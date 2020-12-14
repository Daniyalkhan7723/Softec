package com.example.softec.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.softec.Api.ApiClient;
import com.example.softec.Api.ApiInterface;
import com.example.softec.MenuModel.MenuOrderData;
import com.example.softec.PojoClasses.Check_Coupon;
import com.example.softec.PojoClasses.CouponData;
import com.example.softec.PojoClasses.GetAvailableSeats;
import com.example.softec.PojoClasses.OrderPlaceModel;
import com.example.softec.PojoClasses.OtherData;
import com.example.softec.StaticClasses.SharedPreference;
import com.example.softec.StaticClasses.StaticClass;
import com.example.softec.adapter.CollectionOrderAdapter;
import com.example.softec.databinding.ActivityCollectionOrderBinding;
import com.example.softec.databinding.AddVoucherNumberLayoutBinding;
import com.example.softec.databinding.CardInfoBinding;
import com.example.softec.databinding.CouponCodeInputBinding;
import com.example.softec.databinding.NumberPickerBinding;
import com.example.softec.databinding.RecieptBinding;
import com.example.softec.databinding.SpecialMessageLayoutBinding;
import com.example.softec.adapter.ReceiptAdapter;
import com.google.gson.Gson;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.Stripe;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;

import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectionOrder extends AppCompatActivity {

    String other_data = "";
    String order_data = "";
    private String stripe_token_key = "";
    private SharedPreference sp;
    private String final_amount;
    int REQUEST_CODE = 0077;
    ActivityCollectionOrderBinding binding;
    CollectionOrderAdapter collectionOrderAdapter;
    String specialMessage = "";

    ArrayList<MenuOrderData> menuOrderDataList = new ArrayList<>();

    OtherData otherData;
    private int selectedLay = 0;
    boolean processing = false;
    String coupon_apply = "0";
    String coupon_id = "";
    String coupon_discount_percent = "";
    String total_price_after_discount = "";
    String processing_fees = "3.01";
    String payment_type = "cash";
    String longitude = "";
    String latitude = "";
    int loyaltyCheck = 0;
    int loyaltyDiscountAmount = 0;
    CouponData cd;
    StaticClass st;
    private double totolPrice = 0;
    private String collectionTime = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCollectionOrderBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        sp = new SharedPreference(this, "app_local_data");
        st = new StaticClass(this);


        if (getIntent() != null) {

            menuOrderDataList = (ArrayList<MenuOrderData>) getIntent().getSerializableExtra("menu_order");

            totolPrice = getIntent().getDoubleExtra("total_price", 0.0);

            binding.tvTotalPrice.setText("$" + (totolPrice));

            longitude = getIntent().getStringExtra("longitude");
            latitude = getIntent().getStringExtra("latitude");

            loyaltyDiscountAmount = getIntent().getIntExtra("loyaltyDiscountAmount", 0);
            loyaltyCheck = getIntent().getIntExtra("loyaltyCheck", 0);

            binding.loyaltyCheckText.setVisibility(loyaltyCheck != 1 ? View.GONE : View.VISIBLE);
            binding.loyaltyCheckText.setText("This order is free upto $" + loyaltyDiscountAmount);
            binding.addVoucherCv.setVisibility(loyaltyCheck == 1 ? View.GONE : View.VISIBLE);

        }

        binding.specialText.setMovementMethod(new ScrollingMovementMethod());

        binding.toolBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                previousLay();

            }
        });

        setAdapter();

        binding.editSpecialText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addSpecialMessage();
            }
        });

        binding.collectionTimeLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker();
            }
        });

        binding.addVoucherCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (cd == null) {
                    couponCodeInput();
                } else {
                    voucherDescription(1);
                }
            }
        });

        binding.placeorderBottomLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextLay();
            }
        });

        binding.cashPaymentLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextLay();
            }
        });

        binding.creditCardPaymentLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                payment_type = "cc";
                creditCardCredentials();
//                startActivityForResult(new Intent(CollectionOrder.this, AddPayment.class),REQUEST_CODE);
                //nextLay();
            }
        });

        binding.cashPaymentLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                payment_type = "cash";
                new AlertDialog.Builder(CollectionOrder.this).setTitle("Final Confirmation").setMessage("You can't cancel it after you confirm it now").
                        setPositiveButton("Place", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                conversion();
                                dialog.dismiss();
                                nextLay();
                                processing = true;
                                binding.btnDone.setVisibility(View.GONE);
//                                binding.btnViewReceipt.setVisibility(View.GONE);
                                binding.ivThankYou.setVisibility(View.GONE);
                                binding.btnCancelOrder.setVisibility(View.GONE);
                                binding.orderProgress.setVisibility(View.VISIBLE);

                            }
                        }).setNeutralButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

            }
        });

        binding.btnCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conversion();
                processing = true;
                binding.btnDone.setVisibility(View.GONE);
//                binding.btnViewReceipt.setVisibility(View.GONE);
                binding.ivThankYou.setVisibility(View.GONE);
                binding.btnCancelOrder.setVisibility(View.GONE);
                binding.orderProgress.setVisibility(View.VISIBLE);
            }
        });

        binding.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnData(1);
            }
        });

//        binding.btnViewReceipt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showReciept();
//            }
//        });

        nextLay();

    }

    private void timePicker() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        final NumberPickerBinding numberPickerBinding = NumberPickerBinding.inflate(getLayoutInflater());
        View view = numberPickerBinding.getRoot();

        final String[] values = {"16:00", "16:10", "16:30", "17:00", "18:00"};
        numberPickerBinding.numberPicker.setMinValue(0);
        numberPickerBinding.numberPicker.setMaxValue(values.length - 1);
        numberPickerBinding.numberPicker.setDisplayedValues(values);
        numberPickerBinding.numberPicker.setWrapSelectorWheel(true);

        alertDialog.setView(view);

        final AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.setCancelable(false);
        alertDialog1.show();

        numberPickerBinding.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectionTime = values[numberPickerBinding.numberPicker.getValue()];
                binding.collectionTimeText.setText(collectionTime);
                alertDialog1.dismiss();
            }
        });


    }

    private void conversion() {

        Gson gson = new Gson();
        order_data = gson.toJson(menuOrderDataList);

        String collection_time_text = binding.collectionTimeText.getText().toString();
        otherData = new OtherData(
                sp.getIntValue("user_id") + ""
                , longitude
                , latitude
                , collection_time_text
                , processing_fees
                , coupon_apply
                , coupon_id
                , coupon_discount_percent
                , total_price_after_discount
                , String.valueOf(totolPrice)
                , binding.specialText.getText().toString()
                , payment_type
                , stripe_token_key
                , loyaltyCheck + ""
                , loyaltyDiscountAmount + ""
                , coupon_apply.equals("1") ? total_price_after_discount
                : loyaltyCheck == 1 ? "" + ((totolPrice > loyaltyDiscountAmount) ? totolPrice - loyaltyDiscountAmount : 0) : String.valueOf(totolPrice));


        other_data = gson.toJson(otherData);
        Log.i("other_data",other_data+"\n"+order_data);
        placeOrder(order_data, other_data);

    }

    AlertDialog cc_dialog;
    CardInfoBinding cardInfoBinding;

    private void creditCardCredentials() {

        AlertDialog.Builder alert;

        alert = new AlertDialog.Builder(this);
        cardInfoBinding = CardInfoBinding.inflate(getLayoutInflater());
        View view = cardInfoBinding.getRoot();
        cardInfoBinding.checkCardProgress.setVisibility(View.GONE);


        alert.setView(view);

        cc_dialog = alert.create();
        cc_dialog.setCancelable(true);
        cc_dialog.show();

        cardInfoBinding.savePayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCard(cardInfoBinding.cardInputWidget.getCard());
            }
        });
    }

    private void saveCard(Card card) {

        if (card == null) {
            Toast.makeText(getApplicationContext(), "Invalid card", Toast.LENGTH_SHORT).show();
        } else {
            if (!card.validateCard()) {
                // Do not continue token creation.
                Toast.makeText(getApplicationContext(), "Invalid card", Toast.LENGTH_SHORT).show();
            } else {
                CreateToken(card);
            }
        }
    }

    private void CreateToken(Card card) {

        cc_dialog.setCancelable(false);
        cardInfoBinding.checkCardProgress.setVisibility(View.VISIBLE);
        cardInfoBinding.savePayment.setVisibility(View.GONE);

//        Stripe stripe = new Stripe(getApplicationContext(), "pk_test_51Gs39VEPcW1No1FNw5GTUuakqc23ryV8Xg8C6BlartnvC0USsTnproBUsdYxPxnAI91UwgyIbX6fkB0U4Es8m4cZ00mxY9pY3h");

        String old1 = "pk_test_b23PdP9dziK1JWCLAjMOwR0100SpkzdXa2";
        String updated_8_august_by_adnan = "pk_test_51GsCq6JMWsqakPcSzM2DaxyAp4FEpyk8SjRjctTRqRx7wXKeiIMgsLDTMS1zhHMhPEK2kn4RgRCRmlckajMbVCY100fTBIFtgK";

        Stripe stripe = new Stripe(getApplicationContext(), updated_8_august_by_adnan);
        stripe.createCardToken(card, new ApiResultCallback<Token>() {
            @Override
            public void onSuccess(Token token) {
//                Toast.makeText(getApplicationContext(),"Tokken: "+token.getId(),Toast.LENGTH_LONG).show();
                cc_dialog.dismiss();
                cardInfoBinding.checkCardProgress.setVisibility(View.GONE);
                cardInfoBinding.savePayment.setVisibility(View.VISIBLE);
                stripe_token_key = token.getId();

                new AlertDialog.Builder(CollectionOrder.this).setTitle("Final Confirmation")
                        .setMessage("Card is valid.. Now...You can't cancel it after you confirm it now").
                        setPositiveButton("Place", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                conversion();
                                dialog.dismiss();
                                nextLay();
                                processing = true;
                                binding.btnDone.setVisibility(View.GONE);
//                                binding.btnViewReceipt.setVisibility(View.GONE);
                                binding.ivThankYou.setVisibility(View.GONE);
                                binding.btnCancelOrder.setVisibility(View.GONE);
                                binding.orderProgress.setVisibility(View.VISIBLE);

                            }
                        }).setNeutralButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

            }

            @Override
            public void onError(@NotNull Exception e) {

                cc_dialog.setCancelable(true);
                cardInfoBinding.checkCardProgress.setVisibility(View.GONE);
                cardInfoBinding.savePayment.setVisibility(View.VISIBLE);
                // Show localized error message
                Toast.makeText(getApplicationContext(),
                        e.getLocalizedMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    private void setAdapter() {

        collectionOrderAdapter = new CollectionOrderAdapter(this, menuOrderDataList);
        binding.collectionListRecycler.setLayoutManager(new LinearLayoutManager(this));
        binding.collectionListRecycler.setAdapter(collectionOrderAdapter);

    }

    private void addSpecialMessage() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        final SpecialMessageLayoutBinding special = SpecialMessageLayoutBinding.inflate(getLayoutInflater());
        View view = special.getRoot();

        alertDialog.setView(view);

        final AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.show();

        special.et.setText(specialMessage);

        special.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(special.et.getText().toString().length()>0){
                    specialMessage = special.et.getText().toString();
                    binding.specialText.setText(specialMessage);
                }

                alertDialog1.dismiss();
            }
        });

    }

    public void contorlLay(int i) {

        if (i == 0) {
            super.onBackPressed();
        }

        binding.firstLay.setVisibility(i == 1 ? View.VISIBLE : View.GONE);
        binding.placeorderBottomLay.setVisibility(i == 1 ? View.VISIBLE : View.GONE);
        binding.secondLay.setVisibility(i == 2 ? View.VISIBLE : View.GONE);
        binding.topTitle.setText(i == 2 ? "Payment Method" : "Collection Order Place");
        binding.finalLay.setVisibility(i == 3 ? View.VISIBLE : View.GONE);
        binding.toolbarLayout.setVisibility(i == 3 ? View.GONE : View.VISIBLE);

    }

    private void nextLay() {
        if (processing) {
            return;
        }
        selectedLay++;
        contorlLay(selectedLay);
    }

    private void previousLay() {
        if (processing) {
            return;
        }
        selectedLay--;
        contorlLay(selectedLay);
    }

    @Override
    public void onBackPressed() {
        previousLay();
    }

    private void placeOrder(String order, String other) {

        Log.i("junaid", order+"\n"+other);

        Call<OrderPlaceModel> call = ApiClient.getRetrofit().create(ApiInterface.class).place_order(other, order);

        call.enqueue(new Callback<OrderPlaceModel>() {
            @Override
            public void onResponse(Call<OrderPlaceModel> call, Response<OrderPlaceModel> response) {
                try {
                    if (response.body().getStatus().equals("success")) {
                        binding.btnDone.setVisibility(View.VISIBLE);
                        binding.ivThankYou.setVisibility(View.VISIBLE);
                        binding.btnCancelOrder.setVisibility(View.GONE);
                        binding.orderProgress.setVisibility(View.GONE);
                        binding.orderSendingStatus.setText("Your order for collection has been sent to the Chief 'Softec'");
//                        binding.btnViewReceipt.setVisibility(View.VISIBLE);
//                        showReciept();
                    } else {
                        binding.btnDone.setVisibility(View.GONE);
//                        binding.btnViewReceipt.setVisibility(View.GONE);
                        binding.ivThankYou.setVisibility(View.GONE);
                        binding.btnCancelOrder.setVisibility(View.VISIBLE);
                        binding.orderProgress.setVisibility(View.GONE);
                        binding.orderSendingStatus.setText("Network Error or Server Error Occur, Please tap RETRY to Try Again..");
                        processing = false;
                    }

                } catch (Exception e) {
                    binding.btnDone.setVisibility(View.GONE);
//                    binding.btnViewReceipt.setVisibility(View.GONE);
                    binding.ivThankYou.setVisibility(View.GONE);
                    binding.btnCancelOrder.setVisibility(View.VISIBLE);
                    binding.orderProgress.setVisibility(View.GONE);
                    binding.orderSendingStatus.setText("Network Error or Server Error Occur, Please tap RETRY to Try Again..");
                    processing = false;
                    st.toast(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<OrderPlaceModel> call, Throwable t) {
                binding.btnDone.setVisibility(View.GONE);
//                binding.btnViewReceipt.setVisibility(View.GONE);
                binding.ivThankYou.setVisibility(View.GONE);
                binding.btnCancelOrder.setVisibility(View.VISIBLE);
                binding.orderProgress.setVisibility(View.GONE);
                binding.orderSendingStatus.setText("Network Error or Server Error Occur, Please tap RETRY to Try Again..");
                processing = false;
            }
        });
    }

    private void returnData(int order_place) {

        Intent data = new Intent();
        data.putExtra("order_place", order_place);
        setResult(RESULT_OK, data);
        finish();

    }

    private void showReciept() {

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        final RecieptBinding recieptBinding = RecieptBinding.inflate(getLayoutInflater());
        View view = recieptBinding.getRoot();
        alertDialog.setView(view);


        final AlertDialog alertDialog1 = alertDialog.create();
        alertDialog1.show();
        recieptBinding.itemsRecycler.setLayoutManager(new LinearLayoutManager(this));
        recieptBinding.itemsRecycler.setAdapter(new ReceiptAdapter(this, menuOrderDataList));


        recieptBinding.totalPrice.setText("Total Bill\t\t$" + (coupon_apply.equals("1") ? total_price_after_discount : totolPrice + ""));
        recieptBinding.paymentBy.setText("Payment By:\t\t" + (payment_type.equals("cash") ? "Cash" : "Credit Card"));


        recieptBinding.gotit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog1.dismiss();
            }
        });

        recieptBinding.btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnData(1);
                Intent i = new Intent(CollectionOrder.this, MainActivity.class);
                i.putExtra("collection_list", menuOrderDataList);
                i.putExtra("other_detail", otherData);
                startActivity(i);
            }
        });

    }


//    private void getAvailability() {
//
//        String TIME_SERVER = "url";
//        NTPUDPClient timeClient = new NTPUDPClient();
//        try {
//            InetAddress inetAddres = InetAddress.getByName(TIME_SERVER);
//            TimeInfo timeInfo = timeClient.getTime(inetAddress);
//            long returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();
//            Date time = new Date(returnTime);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        controlAvailAddButtonLay(0);
//
//        Call<GetAvailableSeats> call = ApiClient.getRetrofit().create(ApiInterface.class).getAvailableSeats();
//
//        call.enqueue(new Callback<GetAvailableSeats>() {
//            @Override
//            public void onResponse(Call<GetAvailableSeats> call, Response<GetAvailableSeats> response) {
//                if(response.body()!=null){
//                    if (response.body().getStatus().equals("success")) {
//                        getAvailableSeats = response.body();
//                        getAvailableSeatsModel = getAvailableSeats.getData();
//                        availableBookingDaysList = getAvailableSeatsModel.getBookings();
//                        controlAvailAddButtonLay(1);
//                    } else {
//                        controlAllBookingsLay(2);
//                    }
//                }
//                else {
//                    st.toast("null body");
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GetAvailableSeats> call, Throwable t) {
//                controlAvailAddButtonLay(2);
//            }
//        });
//
//    }

    //coupon discount
    private void couponCodeInput() {

        final CouponCodeInputBinding codeInputBinding = CouponCodeInputBinding.inflate(getLayoutInflater());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(codeInputBinding.getRoot());

        final AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();

        codeInputBinding.couponCodeErrorMessage.setVisibility(View.GONE);

        codeInputBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        codeInputBinding.btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (codeInputBinding.et.getText().toString().length() == 0) {
                    st.toast("enter Coupon code");
                    return;
                }

                //

                codeInputBinding.voucherProgress.setVisibility(View.VISIBLE);
                codeInputBinding.btnLayFirst.setVisibility(View.GONE);
                codeInputBinding.couponCodeErrorMessage.setVisibility(View.GONE);
                Call<Check_Coupon> call = ApiClient.getRetrofit().create(ApiInterface.class).check_coupon(
                        sp.getIntValue("user_id")+"", codeInputBinding.et.getText().toString());

                call.enqueue(new Callback<Check_Coupon>() {
                    @Override
                    public void onResponse(Call<Check_Coupon> call, Response<Check_Coupon> response) {

                        try {

                            String status = response.body().getStatus();
                            String message = response.body().getMessage();

                            if (status.equals("success")) {
                                cd = response.body().getCoupon();
                                dialog.dismiss();
                                voucherDescription(0);
                            } else {
                                codeInputBinding.voucherProgress.setVisibility(View.GONE);
                                codeInputBinding.btnLayFirst.setVisibility(View.VISIBLE);
                                codeInputBinding.couponCodeErrorMessage.setVisibility(View.VISIBLE);
                                codeInputBinding.couponCodeErrorMessage.setText(message + " Try Another");
                            }
                        } catch (Exception e) {
                            codeInputBinding.voucherProgress.setVisibility(View.GONE);
                            codeInputBinding.btnLayFirst.setVisibility(View.VISIBLE);
                            codeInputBinding.couponCodeErrorMessage.setVisibility(View.VISIBLE);
                            codeInputBinding.couponCodeErrorMessage.setText("Try Another");
                        }
                    }

                    @Override
                    public void onFailure(Call<Check_Coupon> call, Throwable t) {
                        codeInputBinding.voucherProgress.setVisibility(View.GONE);
                        codeInputBinding.btnLayFirst.setVisibility(View.VISIBLE);
                        codeInputBinding.couponCodeErrorMessage.setVisibility(View.VISIBLE);
                        codeInputBinding.couponCodeErrorMessage.setText("Network Error! Try Another");
                    }
                });


                codeInputBinding.et.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        codeInputBinding.couponCodeErrorMessage.setVisibility(View.GONE);
                    }
                });
            }
        });


    }
    private void voucherDescription(final int i) {

        final AddVoucherNumberLayoutBinding addVoucherBinding = AddVoucherNumberLayoutBinding.inflate(getLayoutInflater());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(addVoucherBinding.getRoot());

        final AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();

        addVoucherBinding.tvVoucher.setText(cd.getTitle());
        addVoucherBinding.tvCode.setText(cd.getCoupon());
        addVoucherBinding.tvDiscount.setText(cd.getPercentage() + " %");
        coupon_discount_percent = cd.getPercentage();
        coupon_id = cd.getId() + "";
        addVoucherBinding.tvStatus.setText(cd.getStatus() == 1 ? "Enabled" : "Disabled");

        addVoucherBinding.btnCancel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i==0){cd=null;}
                dialog.dismiss();
            }
        });

        addVoucherBinding.btnApply.setVisibility((i == 0 && cd.getStatus()==1) ? View.VISIBLE : View.GONE);
        addVoucherBinding.btnRemoveTryAgain.setText(i == 0 ? "Try Another" : "Remove & Try Another");

        addVoucherBinding.btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coupon_apply = "1";
                double perc = Double.parseDouble(cd.getPercentage()) / 100;
                double total = totolPrice * perc;
                binding.addVoucherTextView.setText("Coupon : " + cd.getPercentage() + "%, Total: $" + (totolPrice - total));
                total_price_after_discount = String.valueOf(totolPrice - total);
                dialog.dismiss();
                st.toast("Coupon Discount Applied");
            }
        });

        addVoucherBinding.btnRemoveTryAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cd = null;
                dialog.dismiss();
                coupon_apply = "0";
                binding.addVoucherTextView.setText("Apply Coupon");
                couponCodeInput();

            }
        });

    }

}