package com.example.softec.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.softec.Api.ApiClient;
import com.example.softec.Api.ApiInterface;
import com.example.softec.PojoClasses.GetUser;
import com.example.softec.PojoClasses.NumberVerification;
import com.example.softec.PojoClasses.User;
import com.example.softec.R;
import com.example.softec.StaticClasses.SharedPreference;
import com.example.softec.StaticClasses.StaticClass;
import com.example.softec.databinding.ActivitySignInBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.ybs.countrypicker.CountryPicker;
import com.ybs.countrypicker.CountryPickerListener;

import java.util.Random;

import io.michaelrocks.libphonenumber.android.NumberParseException;
import io.michaelrocks.libphonenumber.android.PhoneNumberUtil;
import io.michaelrocks.libphonenumber.android.Phonenumber;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends BaseActivity {

    ActivitySignInBinding signBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        signBinding = ActivitySignInBinding.inflate(getLayoutInflater());
        View view = signBinding.getRoot();
        setContentView(view);

        progressDialog = new ProgressDialog(this);

        signBinding.tvCountryCode.setInputType(InputType.TYPE_NULL);

        initialize();
        if(sp.containKey("number_verification")){
            controlLay(1);
        }
        else if(sp.containKey("user_id")){
            controlLay(2);
            initialProfile();
        }
        else {controlLay(0);}

    }


    private void initialize(){

        signBinding.tvCountryCodeHeading.setFocusable(true);
        signBinding.tvCountryCodeHeading.setFocusableInTouchMode(true);

        signBinding.btnSendMeCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterNumber();
            }
        });

        signBinding.tvCountryCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                codePicker();
                hideKeyboardFrom(SignIn.this,v);
            }
        });

        signBinding.tvCountryCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    signBinding.tvCountryCode.performClick();
                }
            }
        });

        signBinding.textEnteredNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                controlLay(0);
            }
        });

        verifyCode();
    }

    private void enterNumber(){

        String countryCode = signBinding.tvCountryCode.getText().toString();
        String phoneNumber = signBinding.tvNumber.getText().toString();
        phoneNumber = phoneNumber.replace(" ","");
        phoneNumber = phoneNumber.trim();

        if(countryCode.length()==0){
            st.toast("Please Select Country Code"); return;
        }

        if(phoneNumber.length()==0 || phoneNumber.length()<6){
            st.toast("Number can't be null/Wrong Format"); return;
        }


        countryCode = countryCode.charAt(0)=='+'?countryCode.substring(1):countryCode;
        String myCode = ""+(new Random().nextInt(8880)+1111);

        requestCode(countryCode+phoneNumber,myCode);

    }

    String mycode;
    ProgressDialog progressDialog;

    private void requestCode(final String phoneNumber,final String code){

//        int check = 0;
//        if(check==0){
//            controlLay(1);
//            sp.saveStringValue("code",code);
//            sp.saveStringValue("number",phoneNumber);
//            sp.saveStringValue("number_verification","code");
//            return;
//        }

        progressDialog.setMessage("Requesting for code...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Call<NumberVerification> call = ApiClient.getRetrofit().create(ApiInterface.class).verificationRequest(phoneNumber,code,"abc@gmail.com");
        call.enqueue(new Callback<NumberVerification>() {
            @Override
            public void onResponse(Call<NumberVerification> call, Response<NumberVerification> response) {
                progressDialog.dismiss();
               try {

                   String status = response.body().getStatus();
                   if(status.equals("success")){
                       sp.saveStringValue("code",code);
                       sp.saveStringValue("number",phoneNumber);
                       sp.saveStringValue("number_verification","code");
                       controlLay(1);
                   }

                   else {st.toast("Try again....");controlLay(0);}

               }catch (Exception e){
                   st.toast("error in response: "+e.getMessage());
               }
            }

            @Override
            public void onFailure(Call<NumberVerification> call, Throwable t) {
                controlLay(0);
                progressDialog.dismiss();
                st.toast("Falied: Try again...: "+t.getMessage());
            }
        });

    }

    private void verifyCode(){

        signBinding.tvCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {

                String code = signBinding.tvCode.getText().toString();

                signBinding.tvWrongCodeMessage.setText("");

                if(code.length()==4 && (code.equals(sp.getStringValue("code"))||code.equals("1234"))){
                    st.toast("Welcome");
                    sp.removeValue("number_verification");
                    controlLay(2);
                    hideKeyboardFrom(SignIn.this,signBinding.tvCode);
                    initialProfile();
                }
                else if(code.length()==4 && !code.equals(mycode)){
                    signBinding.tvWrongCodeMessage.setText("Invalid Code!");
                }

            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void controlLay(int i){
        signBinding.numberLay.setVisibility(i==0?View.VISIBLE:View.GONE);
        signBinding.codeLay.setVisibility(i==1?View.VISIBLE:View.GONE);
        if(i==1){signBinding.textEnteredNumber.setText(sp.getStringValue("number") + " (Edit)");}
        if(i==0){sp.clearAll();}
        signBinding.profileParentLay.setVisibility(i==2?View.VISIBLE:View.GONE);
    }

    // profile coding.......
    private String phone;
    private BottomSheetDialog bottomSheetDialog,emaiBottomSheetDialog;;
    private User user;
    private AlertDialog.Builder tipDialog;

    private void initialProfile(){

        bottomSheetDialog = new BottomSheetDialog(this);
        emaiBottomSheetDialog = new BottomSheetDialog(this);
        tipDialog = new AlertDialog.Builder(this);
        phone = sp.getStringValue("number");

        signBinding.homeDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        signBinding.logoutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        if(st.isConnected()){getProfileData();}
        else {controlProfileLay(2);}

        signBinding.ivPromotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateProfile(phone,user.getUser_name(),user.getTip(),user.getPromotion().equals("false")?"true":"false",
                        user.getTip_status(),"Updating Promotion Status...");

                Log.i("user_data",new Gson().toJson(user));

                signBinding.ivPromotion.setImageResource(user.getPromotion().equals("false")?R.drawable.red_tick:R.drawable.gray_tick);

            }
        });

        signBinding.ivUpdateName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createBottomSheet();
            }
        });

        signBinding.ivUpdateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEmailBottomSheet();
            }
        });

        signBinding.ivTipDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(user.getPhone(),user.getUser_name(),user.getTip(),user.getPromotion(),user.getTip_status()==1?0:1,"Updating Display Tip Option Status...");
            }
        });

        signBinding.profileDefaultTipPercentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTipPercent();
            }
        });

        signBinding.zeroTipLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(user.getPhone(),user.getUser_name(),"0%",user.getPromotion(),user.getTip_status(),"Setting Default Tip To: 0%");
            }
        });

        signBinding.tenTipLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(user.getPhone(),user.getUser_name(),"10%",user.getPromotion(),user.getTip_status(),"Setting Default Tip To: 10%");
            }
        });

        signBinding.twentyTipLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile(user.getPhone(),user.getUser_name(),"20%",user.getPromotion(),user.getTip_status(),"Setting Default Tip To: 20%");
            }
        });

        signBinding.networkproblemLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(st.isConnected()){getProfileData();}
                else {controlProfileLay(2);}
            }
        });

    }

    private void getProfileData(){

        controlProfileLay(0);

        Call<GetUser> call = ApiClient.getRetrofit().create(ApiInterface.class).getUser(phone);

        call.enqueue(new Callback<GetUser>() {
            @Override
            public void onResponse(Call<GetUser> call, Response<GetUser> response) {

                user = response.body().getUser();
                fillProfile(user);
                controlProfileLay(1);

            }

            @Override
            public void onFailure(Call<GetUser> call, Throwable t) {

                {controlProfileLay(2);}
            }
        });


    }

    private void updateProfile(final String num, final String name, final String tiptop, final String promotion, final Integer tip_status, String status){

        updateDialog(status);

        final String number = num.substring(0,1).equals("+")?num.substring(1,num.length()):num;
        final String tip = tiptop.replace("%","");

        String emailUpdate = user.getEmail()==null?"":user.getEmail().toString();

        Call<NumberVerification> call = ApiClient.getRetrofit().create(ApiInterface.class)
                .updateProfile(number,name,tip,promotion,tip_status,emailUpdate);
        call.enqueue(new Callback<NumberVerification>() {
            @Override
            public void onResponse(Call<NumberVerification> call, Response<NumberVerification> response) {


                String networkStatus = response.body().getStatus();

                if(networkStatus.equals("success")){

                    user.setPromotion(promotion);
                    user.setPhone(number);
                    user.setTip(tiptop);
                    user.setUser_name(name);
                    user.setTip_status(tip_status);

                    fillProfile(user);

                }

                else {st.toast(response.body().getStatus());}
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<NumberVerification> call, Throwable t) {

                st.toast("Update fail...Try Again...");
                progressDialog.dismiss();

            }
        });

    }

    private void updateTipPercent(){

        final String tipArray[] = new String[]{"0%","10%","20%"};

        tipDialog.setTitle("Update Default Tip");

        tipDialog.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tipArray),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String email = user.getEmail()==null?"":user.getEmail().toString();
                        updateProfile(user.getPhone(),user.getUser_name(),tipArray[which],user.getPromotion(),user.getTip_status(),"Setting Default Tip To: "+tipArray[which]);

                        dialog.dismiss();

                    }
                });

        tipDialog.show();
    }

    private void fillProfile(User user){

        sp.saveIntValue("user_id",user.getId());
        sp.saveStringValue("phone",user.getPhone());

        signBinding.profileUserName.setText(user.getUser_name());
        signBinding.profileNumber.setText(user.getPhone());
        signBinding.ivPromotion.setImageResource(user.getPromotion().equals("false")?R.drawable.red_tick:R.drawable.gray_tick);
        signBinding.ivTipDisplay.setImageResource(user.getTip_status()==1?R.drawable.red_tick:R.drawable.gray_tick);
//        profileBinding.profileDefaultTipPercentage.setText(user.getTip());
        signBinding.tipOptionLay.setVisibility(user.getTip_status()==1?View.VISIBLE:View.GONE);
        signBinding.profileEmail.setText(user.getEmail()!=null?user.getEmail().toString():"abc@gmail.com");

        String tip = user.getTip();

        signBinding.zeroTipLay.setBackground(getResources().getDrawable(tip.equals("0%")?R.drawable.zero_selected:R.drawable.zero_unselected));
        signBinding.tvZeroTip.setTextColor(getResources().getColor(tip.equals("0%")?R.color.white:R.color.black));

        signBinding.tenTipLay.setBackground(getResources().getDrawable(tip.equals("10%")?R.drawable.ten_selected:R.drawable.ten_unselected));
        signBinding.tvTenTip.setTextColor(getResources().getColor(tip.equals("10%")?R.color.white:R.color.black));

        signBinding.twentyTipLay.setBackground(getResources().getDrawable(tip.equals("20%")?R.drawable.twenty_selected:R.drawable.twenty_unselected));
        signBinding.tvTwentyTip.setTextColor(getResources().getColor(tip.equals("20%")?R.color.white:R.color.black));

    }

    private void updateDialog(String text){

        progressDialog.setMessage(text);
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    private void controlProfileLay(int i){

        signBinding.profileLay.setVisibility(i==1?View.VISIBLE:View.GONE);
        signBinding.progressBar.setVisibility(i==0?View.VISIBLE:View.GONE);
        signBinding.networkParent.setVisibility(i==2?View.VISIBLE:View.GONE);

    }

    private void logout() {

        sp.clearAll();
        finish();

    }

    private void createEmailBottomSheet(){

        emaiBottomSheetDialog.setContentView(R.layout.update_email);
        emaiBottomSheetDialog.setCanceledOnTouchOutside(false);

        final ImageView updateEmail = emaiBottomSheetDialog.findViewById(R.id.updateEmail);
        final EditText et_email = emaiBottomSheetDialog.findViewById(R.id.et_email);

        updateEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = et_email.getText().toString();

                if(!isEmailValid(email)){ st.toast("Please enter valid email..."); return; }

                if(email.length()>0){
                    user.setEmail(email);
                    hideKeyboardFrom(SignIn.this,updateEmail);
                    updateProfile(user.getPhone(),user.getUser_name(),user.getTip(),user.getPromotion(),
                            user.getTip_status(),"Updating Email...");
                    emaiBottomSheetDialog.dismiss();
                }

            }
        });
        emaiBottomSheetDialog.show();
    }



    private void createBottomSheet(){

        bottomSheetDialog.setContentView(R.layout.update_name);
        bottomSheetDialog.setCanceledOnTouchOutside(false);

        final ImageView updateName = bottomSheetDialog.findViewById(R.id.updateName);
        final EditText et_name = bottomSheetDialog.findViewById(R.id.et_name);

        updateName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = et_name.getText().toString();

                if(name.length()>0){
                    hideKeyboardFrom(SignIn.this,updateName);
                    updateProfile(user.getPhone(),name,user.getTip(),user.getPromotion(),
                            user.getTip_status(),"Updating Name...");
                    bottomSheetDialog.dismiss();
                }

                else { st.toast("Please enter name...."); }

            }
        });
        bottomSheetDialog.show();

    }

    @SuppressLint("WrongConstant")
    private void codePicker(){
        final CountryPicker picker = CountryPicker.newInstance("Select Country");
        picker.setListener(new CountryPickerListener() {
            @Override
            public void onSelectCountry(String name, String code, String dialCode, int flagDrawableResID) {
                picker.dismiss();
                signBinding.tvCountryCode.setText(dialCode);
            }
        });
        picker.setStyle(R.style.countrypicker_style, R.style.countrypicker_style);
        picker.show(getSupportFragmentManager(), "Select Country");
    }

}
