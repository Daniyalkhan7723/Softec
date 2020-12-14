package com.example.softec.Activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.softec.Api.ApiClient;
import com.example.softec.Api.ApiInterface;
import com.example.softec.Listeners.GetClicks;
import com.example.softec.MenuModel.MenuData;
import com.example.softec.MenuModel.MenuDealList;
import com.example.softec.MenuModel.MenuDealProduct;
import com.example.softec.MenuModel.MenuExtraAddings;
import com.example.softec.MenuModel.MenuMenuData;
import com.example.softec.MenuModel.MenuOrderData;
import com.example.softec.MenuModel.MenuProductList;
import com.example.softec.MenuModel.MenuProductSize;
import com.example.softec.MenuModel.MenuSelectionCallBack;
import com.example.softec.NewApis.OrderListListener;
import com.example.softec.Listeners.PreviousOrderListener;
import com.example.softec.PojoClasses.Loyality;
import com.example.softec.PojoClasses.LoyaltyAvailable;
import com.example.softec.PojoClasses.OtherData;
import com.example.softec.PojoClasses.PreviousOrderData;
import com.example.softec.PojoClasses.PreviousOrders;
import com.example.softec.R;
import com.example.softec.StaticClasses.Utils;
import com.example.softec.VoucherTesting;
import com.example.softec.adapter.CollectionOrderAdapter;
import com.example.softec.adapter.OrderListAdapter;
import com.example.softec.adapter.PinItemRecyclerAdapter;
import com.example.softec.adapter.PreviousOrderAdapter;
import com.example.softec.databinding.ExpandSelectedItemBinding;
import com.example.softec.databinding.LoyaltyDisplayBinding;
import com.example.softec.databinding.PreviousOrdersListLayBinding;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends BaseActivity {

    //new required variables
    LinearLayout share_app_layout;
    RelativeLayout networkErrorLayout, success_layout;
    ShimmerFrameLayout min_order_shimmer, restaurant_phone_shimmer, distance_text_shimmer;
    private boolean shimmering = true;
    RelativeLayout networkproblemLay;
    OrderListAdapter orderListAdapter;
    MenuSelectionCallBack menuSelectionCallBack;

    SwipeRefreshLayout swiperefreshmenu;

    //New
    List<MenuMenuData> menuDataList = new ArrayList<>();
    ArrayList<MenuOrderData> menuOrderDataList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home1);
        initMenuSelectionCallBack();
        setPreviousOrderListener();
        initials();

        testV();
    }

    private void testV(){

        st.toast("voucher testing");

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://www.servo.com.ly/").addConverterFactory(GsonConverterFactory.create()).build();
        Call<VoucherTesting> call = retrofit.create(ApiInterface.class).VoucherTEST("2224","66546alcn");
        call.enqueue(new Callback<VoucherTesting>() {
            @Override
            public void onResponse(Call<VoucherTesting> call, Response<VoucherTesting> response) {
                st.toast("Response: "+response.body().getMessage());
            }

            @Override
            public void onFailure(Call<VoucherTesting> call, Throwable t) {
                st.toast("Failed: "+t.getMessage());
            }
        });

    }

    private void initMenuSelectionCallBack() {

        menuSelectionCallBack = new MenuSelectionCallBack() {
            @Override
            public void addDeal(MenuDealList dealItem) {

                int id_Sum = 0;
                for (int i = 0; i < dealItem.getDealProducts().size(); i++) {
                    id_Sum = id_Sum + dealItem.getDealProducts().get(i).getId();
                }
                addInMenuOrder(dealItem, Utils.typeDeal, null, id_Sum);
            }

            @Override
            public void addProduct(MenuProductList productItem) {
                addInMenuOrder(null, Utils.typeProduct, productItem, 0);
            }

            @Override
            public void openNewActivityForDeal(MenuDealList menuDealList) {
                Intent intent = new Intent(HomeActivity.this, JExtraAddings.class);
                intent.putExtra("extra_adding", menuDealList);
                intent.putExtra("type", Utils.typeDeal);
                startActivityForResult(intent, Utils.startExtraAddingActivityForResult);
            }

            @Override
            public void openNewActivityForProduct(MenuProductList productList) {
                Intent intent = new Intent(HomeActivity.this, ExtraAddingsActivity.class);
                intent.putExtra("extra_adding", productList);
                intent.putExtra("type", Utils.typeProduct);
                startActivityForResult(intent, Utils.startExtraAddingActivityForProductResult);
            }
        };
    }

    private void addInMenuOrder(MenuDealList dealItem, String type, MenuProductList productItem, int id_Sum) {

        MenuOrderData menuOrderData =
                new MenuOrderData(
                        type.equals(Utils.typeDeal) ? dealItem.getId() : productItem.getId(),
                        id_Sum,
                        type.equals(Utils.typeDeal) ? dealItem.getName() : productItem.getName(),
                        type,
                        1,
                        type.equals(Utils.typeDeal) ? dealItem.getPrice() : productItem.getPrice(),
                        type.equals(Utils.typeDeal) ? dealItem.getPrice() : productItem.getPrice(),
                        type.equals(Utils.typeDeal) ? dealItem.getDealProducts() : new ArrayList<MenuDealProduct>(),
                        type.equals(Utils.typeDeal) ? dealItem.getExtraAddings() : new ArrayList<MenuExtraAddings>(),
                        type.equals(Utils.typeDeal) ? new ArrayList<MenuProductSize>() : productItem.getMenuProductSizeList());


        // deal duplicate chehcking
        int duplicateDeal = 0;
        if (type.equals(Utils.typeDeal)) {
            for (int i = 0; i < menuOrderDataList.size(); i++) {
                if (dealItem.getId() == menuOrderDataList.get(i).getId()) {
                    if (id_Sum == menuOrderDataList.get(i).getId_sum()) {
                        menuOrderDataList.get(i).setQuantity(menuOrderDataList.get(i).getQuantity() + 1);
                        menuOrderDataList.get(i).setTotal_price(menuOrderDataList.get(i).getActual_price() * menuOrderDataList.get(i).getQuantity());
                        duplicateDeal = 1;
                        collectionListLayoutManager.scrollToPositionWithOffset(i, -5);
                        break;
                    }
                }
            }
        }

        int duplicateProduct = 0;
        if (type.equals(Utils.typeProduct)) {
            for (int i = 0; i < menuOrderDataList.size(); i++) {
                if (productItem.getId() == menuOrderDataList.get(i).getId()) {
                    if (id_Sum == menuOrderDataList.get(i).getId_sum()) {
                        menuOrderDataList.get(i).setQuantity(menuOrderDataList.get(i).getQuantity() + 1);
                        menuOrderDataList.get(i).setTotal_price(menuOrderDataList.get(i).getActual_price() * menuOrderDataList.get(i).getQuantity());
                        duplicateProduct = 1;
                        collectionListLayoutManager.scrollToPositionWithOffset(i, -5);
                        break;
                    }
                }
            }
        }

        if (type.equals(Utils.typeDeal) && duplicateDeal != 1) {
            menuOrderDataList.add(menuOrderData);
            orderListAdapter = new OrderListAdapter(this, menuOrderDataList, menuSelectionCallBack,orderListListener);
            collectionRecycler.setAdapter(orderListAdapter);
        } else if (type.equals(Utils.typeProduct) && duplicateProduct != 1) {
            menuOrderDataList.add(menuOrderData);
            orderListAdapter = new OrderListAdapter(this, menuOrderDataList, menuSelectionCallBack,orderListListener);
            collectionRecycler.setAdapter(orderListAdapter);
        }

        setGrandTotal(menuOrderData.getActual_price());
        orderListAdapter.updateOderListAdapter(menuOrderDataList);
        collection_list_lay.setVisibility(View.VISIBLE);

        //collectionRecycler.setAdapter(orderListAdapter);
        //        orderListAdapter = new OrderListAdapter(this, menuOrderDataList, menuSelectionCallBack,orderListListener);

    }

    private void setGrandTotal(double price) {
        totalPrice += price;
        tv_total_price.setText(String.valueOf("$" + totalPrice));
    }

    private void initorderListListener() {

        orderListListener = new OrderListListener() {

            @Override
            public void openItem(MenuOrderData menuOrderData, int index) {
                openSingleItem(menuOrderData,index);
            }

            @Override
            public void deleteExistingItem(int index) {
                updateMenuData(index, 2);
            }

            @Override
            public void addPrice(int index) {
                updateMenuData(index, 1);
            }

            @Override
            public void subPrice(int index) {
                updateMenuData(index, -1);
            }

        };

    }

    private void openSingleItem(final MenuOrderData myMenuOrderData, final int index){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final ExpandSelectedItemBinding binding = ExpandSelectedItemBinding.inflate(getLayoutInflater());
        builder.setView(binding.getRoot());

        final AlertDialog dialog = builder.create();
        dialog.show();
//        dialog.setCancelable(false);

        binding.type.setText("<<< "+(myMenuOrderData.getType()==Utils.typeDeal?"Deal":"Product")+" >>>");
        binding.name.setText(myMenuOrderData.getName());

        final StringBuffer stringBuffer = new StringBuffer();

        if(myMenuOrderData.getType()==Utils.typeDeal){

            for(int i=0;i<myMenuOrderData.getMenu_deal_product_list().size();i++){
                stringBuffer.append(myMenuOrderData.getMenu_deal_product_list().get(i).getName()+"\n");
            }

            for(int i=0;i<myMenuOrderData.getMenu_extra_addings_list().size();i++){
                stringBuffer.append(myMenuOrderData.getMenu_extra_addings_list().get(i).getName()+"\n");
            }

            binding.description.setText("Deal Products\n\n"+(stringBuffer.toString().length()==0?"No More Products":stringBuffer.toString()));

        }
        else {

            for(int i=0;i<myMenuOrderData.getMenu_product_size_list().size();i++){
                stringBuffer.append(myMenuOrderData.getMenu_product_size_list().get(i).getName()+"\n");
            }

            binding.description.setText("Other Detail\n\n"+(stringBuffer.toString().length()==0?"No Other Detail":stringBuffer.toString()));

        }



        binding.addmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderListListener.addPrice(index);
                binding.type.performClick();
            }
        });

        binding.subone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderListListener.subPrice(index);
                binding.type.performClick();
            }
        });

        binding.type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.quantity.setText("Quantity: "+myMenuOrderData.getQuantity());
                binding.price.setText("Price: $"+myMenuOrderData.getActual_price());
                binding.totalprice.setText("Total Price: $"+myMenuOrderData.getTotal_price());
                binding.ordertotal.setText("Order Total: $"+totalPrice);

                if(myMenuOrderData.getQuantity()==0){dialog.dismiss();}

            }
        });

        binding.type.performClick();

        dialog.show();

    }

    private void updateMenuData(int index, int op) {
        if (op != 2) {
            menuOrderDataList.get(index).setQuantity(op == 1 ? (menuOrderDataList.get(index).getQuantity() + 1) : (menuOrderDataList.get(index).getQuantity() - 1));
            menuOrderDataList.get(index).setTotal_price(menuOrderDataList.get(index).getActual_price() * menuOrderDataList.get(index).getQuantity());
            setGrandTotal(op == 1 ? menuOrderDataList.get(index).getActual_price() : (-1 * menuOrderDataList.get(index).getActual_price()));
        }

        if (op == 2) {
            setGrandTotal(-1 * menuOrderDataList.get(index).getTotal_price());
        }

        if (menuOrderDataList.get(index).getQuantity() == 0 || op == 2) {
            menuOrderDataList.remove(index);
        }

        orderListAdapter.updateOderListAdapter(menuOrderDataList);
        if (menuOrderDataList.size() == 0) {
            collection_list_lay.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        statusCheck();
        if (sp.containKey("user_id")) {
            tv_signName.setText("Profile");
            previous_orders_lay.setVisibility(View.VISIBLE);
            table_booking_lay.setVisibility(View.VISIBLE);
            if (loyaltyCheck == 0) {
                checkLoyalty(sp.getIntValue("user_id"));
            }
        } else {
            tv_signName.setText("Sign In");
            table_booking_lay.setVisibility(View.GONE);
            previous_orders_lay.setVisibility(View.GONE);
        }
    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Utils.location_permission_code);

            } else {
                getCurrentLocation();
            }
            //st.toast("Enabled");
        }

    }

    private void buildAlertMessageNoGps() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, You Have To Enable It...")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                }).show();

    }

    private static String longitude = "";
    private static String latitude = "";
    private static String distance_in_text = "";

    public void getCurrentLocation() {

        final LocationRequest locationRequest = new LocationRequest();

        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.getFusedLocationProviderClient(this)
                .requestLocationUpdates(locationRequest, new LocationCallback() {

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);


                        LocationServices.getFusedLocationProviderClient(getApplicationContext()).removeLocationUpdates(this);

                        if (locationResult != null && locationResult.getLocations().size() > 0) {

                            int latestlocationIndex = locationResult.getLocations().size() - 1;

                            double lat = locationResult.getLocations().get(latestlocationIndex).getLatitude();
                            double lng = locationResult.getLocations().get(latestlocationIndex).getLongitude();

                            longitude = String.valueOf(lng);
                            latitude = String.valueOf(lat);

                            String distance = findDistance(lat, lng);
                            distanceText.setText(distance);
                        }

                    }
                }, Looper.myLooper());

    }

    private void checkLoyalty(final int user_id){

        Call<LoyaltyAvailable> call = ApiClient.getRetrofit().create(ApiInterface.class).loyaltyAvailable(user_id+"");

        call.enqueue(new Callback<LoyaltyAvailable>() {
            @Override
            public void onResponse(Call<LoyaltyAvailable> call, Response<LoyaltyAvailable> response) {
                try{

                    String status = response.body().getStatus();
                    Loyality loyality = response.body().getData();

                    if(status.equals("success")){
                        displayLoyalty(loyality);
                    }
                    else {
                        loyaltyCheck = 0;
                        loyalty_check_text.setVisibility(View.GONE);
                    }
                }catch (Exception e){

                    loyalty_check_text.setVisibility(View.GONE);

                    new AlertDialog.Builder(HomeActivity.this).setCancelable(false)
                            .setMessage("Sorry! could not check your loyalty!!!")
                            .setPositiveButton("Try again!!!", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    checkLoyalty(user_id);
                                    dialog.dismiss();;
                                }
                            }).show();

                }
            }

            @Override
            public void onFailure(Call<LoyaltyAvailable> call, Throwable t) {
                loyalty_check_text.setVisibility(View.GONE);
            }
        });

    }

    private void displayLoyalty(Loyality loyality) {

        loyaltyCheck = 1;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LoyaltyDisplayBinding binding = LoyaltyDisplayBinding.inflate(getLayoutInflater());
        builder.setView(binding.getRoot());

        final AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setCancelable(false);


        binding.gotIT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        loyaltyDiscountAmount = loyality.getDiscount_amount();

        binding.amount.setText("Amount $" + loyaltyDiscountAmount);
        loyalty_check_text.setVisibility(View.VISIBLE);
        loyalty_check_text.setText("This order is free upto $" + loyaltyDiscountAmount);

        dialog.show();

    }

    public String inputStreamToString(InputStream inputStream) {
        try {
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes, 0, bytes.length);
            String json = new String(bytes);
            return json;
        } catch (IOException e) {
            return null;
        }
    }

    private void initials() {

        initializeVariables();
        onClicks();
        collapsingBehaviour();
//        setPreviousOrderListener();

        getAllData();

    }

    private void getAllData() {

        controlLay(1);
        doShimmer();

        swiperefreshmenu.setRefreshing(true);

        Call<MenuData> menuDataCall = ApiClient.getRetrofit().create(ApiInterface.class).getMenuData();

        menuDataCall.enqueue(new Callback<MenuData>() {
            @Override
            public void onResponse(Call<MenuData> call, Response<MenuData> response) {

                assert response.body() != null;
                if (response.body().getStatus().equals("success")) {
                    controlLay(1);
                    setUpAfterSuccess(response.body());
                    sp.saveStringValue("latitude",response.body().getLatitude());
                    sp.saveStringValue("longitude",response.body().getLongitude());
                    statusCheck();
                } else {
                    controlLay(2);
                    swiperefreshmenu.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<MenuData> call, Throwable t)
            {
                swiperefreshmenu.setRefreshing(false);
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
                controlLay(2);
            }
        });

    }



    private void setUpAfterSuccess(MenuData menuData) {

        menuDataList = menuData.getData();
        addTabs(menuDataList);
        stopShimmer();

        min_order_amount = menuData.getMinimumAmount();
        tv_min_order_amount.setText("$" + min_order_amount);
        tv_restaurant_phone_number.setText(menuData.getRestaurant_phone());

        pinItemRecyclerAdapter.updateAdapter(menuData);

        pinItemRecyclerAdapter.setGetClicks(new GetClicks() {
            @Override
            public void onItemSelect(int position) {
                tabSelection(position);
                linearLayoutManager.scrollToPositionWithOffset(position, -5);
            }
        });

        recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int f = linearLayoutManager.findFirstVisibleItemPosition();
                tabSelection(f);
            }

        });


    }

    //other methods

    private void controlLay(int i) {

        success_layout.setVisibility(i == 1 ? View.VISIBLE : View.GONE);
        networkErrorLayout.setVisibility(i == 2 ? View.VISIBLE : View.GONE);
        home_drawer_nav.setVisibility(i == 1 ? View.VISIBLE : View.GONE);

        if(i==1){swiperefreshmenu.setRefreshing(false);}

    }

    private void doShimmer() {
        min_order_shimmer.startShimmer();
        restaurant_phone_shimmer.startShimmer();
        distance_text_shimmer.startShimmer();
    }

    private void stopShimmer() {
        min_order_shimmer.stopShimmer();
        min_order_shimmer.setShimmer(null);
        restaurant_phone_shimmer.stopShimmer();
        restaurant_phone_shimmer.setShimmer(null);
        distance_text_shimmer.stopShimmer();
        distance_text_shimmer.setShimmer(null);
        tv_min_order_amount.setBackground(null);
        tv_restaurant_phone_number.setBackground(null);
        distanceText.setBackground(null);
    }

    private void controlShimmer(int i) {

        if (i == 1) {
            min_order_shimmer.stopShimmer();
            min_order_shimmer.setShimmer(null);
            tv_min_order_amount.setBackground(null);
        } else {
            min_order_shimmer.startShimmer();
        }

    }

    private void initializeVariables() {

        linearLayoutManager = new LinearLayoutManager(this);
        gson = new Gson();
        initorderListListener();
        pinItemRecyclerAdapter = new PinItemRecyclerAdapter(this, menuDataList, orderListListener, menuSelectionCallBack);
        orderListAdapter = new OrderListAdapter(this, menuOrderDataList, menuSelectionCallBack,orderListListener);

        //layouts
        loyalty_check_text = findViewById(R.id.loyalty_check_text);
        sign_in_name_lay = findViewById(R.id.sign_in_name_lay);
        tv_signName = findViewById(R.id.tv_signName);
        previous_orders_lay = findViewById(R.id.previous_orders_lay);
        table_booking_lay = findViewById(R.id.table_booking_lay);
        success_layout = findViewById(R.id.success_layout);
        networkErrorLayout = findViewById(R.id.networkErrorLayout);
        parentLay = findViewById(R.id.parentLay);
        home_drawer_nav = findViewById(R.id.home_drawer_nav);
        distanceText = findViewById(R.id.distanceText);
        networkproblemLay = findViewById(R.id.networkproblemLay);
        tabLayout = findViewById(R.id.sliding_tabs);
        toot_bar_back = findViewById(R.id.toot_bar_back);
        drawerLayout = findViewById(R.id.home_drawer);
        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setAdapter(pinItemRecyclerAdapter);
        collection_list_lay = findViewById(R.id.collection_list_lay);
        collection_list_lay.setVisibility(View.GONE);
        share_app_layout = findViewById(R.id.share_app_layout);
        tv_min_order_amount = findViewById(R.id.tv_min_order_amount);
        tv_restaurant_phone_number = findViewById(R.id.tv_restaurant_phone_number);
        distanceText = findViewById(R.id.distanceText);
        collectionRecycler = findViewById(R.id.collectionRecycler);
        collectionListLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true);
        collectionListLayoutManager.setStackFromEnd(true);

        collectionRecycler.setLayoutManager(collectionListLayoutManager);
        collectionRecycler.setAdapter(orderListAdapter);
        place_order = findViewById(R.id.place_order);
        tv_total_price = findViewById(R.id.tv_total_price);

        //shimmers
        min_order_shimmer = findViewById(R.id.min_order_shimmer);
        restaurant_phone_shimmer = findViewById(R.id.restaurant_phone_shimmer);
        distance_text_shimmer = findViewById(R.id.distance_text_shimmer);

    }

    public void test(View view) {
    }

    LinearLayout callLay;

    private void onClicks() {

        callLay = findViewById(R.id.callLay);
        callLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = tv_restaurant_phone_number.getText().toString();
                if(number.length()==0){return;}
                callNumber(number);
            }
        });

        swiperefreshmenu = findViewById(R.id.swiperefreshmenu);

        swiperefreshmenu.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getAllData();
            }
        });

        previous_orders_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPreviousOrderLay();
                drawerLayout.closeDrawers();
            }
        });

        sign_in_name_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, SignIn.class));
            }
        });

        findViewById(R.id.toot_bar_drawer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(home_drawer_nav);
            }
        });

        networkproblemLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hoverEffect(v);
                if (st.isConnected()) {
                    getAllData();
                } else {
                    controlLay(2);
                }
            }
        });

        findViewById(R.id.openMap).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                st.toast("Getting Current Location");
                statusCheck();
//                openActivity(DeliverMap.class);
            }
        });

        table_booking_lay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, TableBooking.class));
                drawerLayout.closeDrawers();
            }
        });

        place_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(min_order_amount>totalPrice){
                    new AlertDialog.Builder(HomeActivity.this).setCancelable(false)
                            .setTitle("Minimum Order is of $"+min_order_amount)
                            .setMessage("Please select order at least upto $"+min_order_amount+" amount.\nThank You!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();;
                                }
                            }).show();
                }

                else if(sp.containKey("user_id")){
                    startCollectionActivity();
                }
                else {

                    new AlertDialog.Builder(HomeActivity.this).setCancelable(false).setMessage("User must be sign-in before manage the order placement.")
                            .setPositiveButton("Sign In", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startActivity(new Intent(HomeActivity.this, SignIn.class));
                                    dialog.dismiss();;
                                }
                            }).setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();

                }
            }
        });

        findViewById(R.id.contact_us_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openSideBar(0);
                drawerLayout.closeDrawers();

            }
        });

        findViewById(R.id.terms_and_conditions_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSideBar(1);
                drawerLayout.closeDrawers();
            }
        });

        findViewById(R.id.faq_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSideBar(2);
                drawerLayout.closeDrawers();
            }
        });

        findViewById(R.id.privacy_policy_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSideBar(3);
                drawerLayout.closeDrawers();
            }
        });

        share_app_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareApp();
                drawerLayout.closeDrawers();
            }
        });

    }

    // previous orders

    List<PreviousOrderData> previousOrderDataArrayList = new ArrayList<>();
    PreviousOrdersListLayBinding previousBind;
    AlertDialog dialog;
    private void openPreviousOrderLay() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        previousBind = PreviousOrdersListLayBinding.inflate(getLayoutInflater());
        builder.setView(previousBind.getRoot());

        dialog = builder.create();
        dialog.show();
        dialog.setCancelable(false);

        previousBind.historyFirstLay.setVisibility(View.VISIBLE);
        previousBind.historySecondLay.setVisibility(View.GONE);

        previousBind.previousOrdersRecycler.setLayoutManager(new LinearLayoutManager(this));

        previousBind.previousPb.setVisibility(View.VISIBLE);
        previousBind.previousOrdersRecycler.setVisibility(View.GONE);

        previousBind.toolBarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousBind.historyFirstLay.setVisibility(View.VISIBLE);
                previousBind.historySecondLay.setVisibility(View.GONE);
            }
        });

        previousBind.tvNoOrderYet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        previousBind.toolBarBack0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Call<PreviousOrders> call =  ApiClient.getRetrofit().create(ApiInterface.class).getPreviousOrder(""+sp.getIntValue("user_id"));
        call.enqueue(new Callback<PreviousOrders>() {
            @Override
            public void onResponse(Call<PreviousOrders> call, Response<PreviousOrders> response) {
                try {
                    previousOrderDataArrayList = response.body().getData();
                    previousBind.previousPb.setVisibility(View.GONE);
                    previousBind.previousOrdersRecycler.
                            setAdapter(new PreviousOrderAdapter(HomeActivity.this, previousOrderDataArrayList, previousOrderListener));
                    previousBind.previousOrdersRecycler.setVisibility(View.VISIBLE);
                }catch (Exception e){
                    st.toast("Error: "+e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<PreviousOrders> call, Throwable t) {
                dialog.dismiss();
                st.toast("Error Please Try Again...");
            }
        });


    }

    PreviousOrderListener previousOrderListener;

    private void setPreviousOrderListener(){

        previousOrderListener = new PreviousOrderListener() {
            @Override
            public void getPreviousData(final PreviousOrderData previous) {

                previousBind.historyFirstLay.setVisibility(View.GONE);
                previousBind.historySecondLay.setVisibility(View.VISIBLE);

                Type listType = new TypeToken<ArrayList<MenuOrderData>>(){}.getType();
                OtherData otherData = gson.fromJson(previous.getOther_data().toString(),OtherData.class);
                final ArrayList<MenuOrderData> collectionLists = gson.fromJson(previous.getOrder_data().toString(),listType);

                previousBind.tvDate.setText(previous.getCreated_at());
                previousBind.tvTotalAmount.setText("$"+otherData.getTotal_price());
                previousBind.tvTotalPaid.setText("$"+otherData.getFinal_amount());
                previousBind.tvPaymentType.setText((otherData.getPayment_type().equals("cc")?"Credit Card":"Cash"));

                previousBind.collectionListRecycler.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
                previousBind.collectionListRecycler.setAdapter(new CollectionOrderAdapter(HomeActivity.this,collectionLists));

                previousBind.discountLay.setVisibility(View.VISIBLE);

                if(otherData.getLoyalty_check().equals("1")){
                    previousBind.discountType.setText("Loyalty Discount");
                    previousBind.tvDiscount.setText("$"+otherData.getLoyalty_amount());
                }
                else if(otherData.getCoupon().equals("1")){
                    previousBind.discountType.setText("Coupon Discount");
                    previousBind.tvDiscount.setText("%"+otherData.getCoupon_discount_percent());
                }
                else {
                    previousBind.discountLay.setVisibility(View.GONE);
                }


                previousBind.reuseBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setPreviousOrderSelection(previous);
                    }
                });

            }
        };

    }
    private void setPreviousOrderSelection(PreviousOrderData previous){

        Type listType = new TypeToken<ArrayList<MenuOrderData>>(){}.getType();
        OtherData otherData = gson.fromJson(previous.getOther_data().toString(),OtherData.class);
        final ArrayList<MenuOrderData> colList = gson.fromJson(previous.getOrder_data().toString(),listType);

//        ArrayList<MenuOrderData> menuOrderDataList = new ArrayList<>();

        if(menuOrderDataList.size()>0){
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Are you sure");
            builder.setMessage("This order selection will replace the current order selection with current prices.")
                    .setCancelable(false)
                    .setNeutralButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setPositiveButton("Replace", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            menuOrderDataList.clear();
                            collection_list_lay.setVisibility(View.VISIBLE);
                            menuOrderDataList = colList;
                            setTotalPrice();
                            collectionRecycler = findViewById(R.id.collectionRecycler);
                            LinearLayoutManager Layout = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, true);
                            Layout.setStackFromEnd(true);
                            collectionRecycler.setLayoutManager(Layout);
                            collectionRecycler.setAdapter(new OrderListAdapter(HomeActivity.this, menuOrderDataList,menuSelectionCallBack,orderListListener));
                            HomeActivity.this.dialog.dismiss();
                        }
                    });
            builder.show();
        }

        else {
            menuOrderDataList.clear();
            collection_list_lay.setVisibility(View.VISIBLE);
            menuOrderDataList = colList;
            setTotalPrice();
            collectionRecycler = findViewById(R.id.collectionRecycler);
            LinearLayoutManager Layout = new LinearLayoutManager(HomeActivity.this, LinearLayoutManager.HORIZONTAL, true);
            Layout.setStackFromEnd(true);
            collectionRecycler.setLayoutManager(Layout);
            collectionRecycler.setAdapter(new OrderListAdapter(HomeActivity.this, menuOrderDataList,menuSelectionCallBack,orderListListener));
            this.dialog.dismiss();
        }

    }

    private void setTotalPrice() {

        totalPrice = 0.0;

        for (int i = 0; i < menuOrderDataList.size(); i++) {

            totalPrice += menuOrderDataList.get(i).getTotal_price();
        }

        tv_total_price.setText("$" + totalPrice);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Utils.location_permission_code && grantResults.length > 0) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            } else {
                Toast.makeText(HomeActivity.this, "Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startCollectionActivity(){


        Log.i("finalData",new Gson().toJson(menuOrderDataList));

        Intent intent = new Intent(HomeActivity.this, CollectionOrder.class);
        intent.putExtra("menu_order",menuOrderDataList);
        intent.putExtra("total_price",totalPrice);
        intent.putExtra("longitude",longitude);
        intent.putExtra("latitude",latitude);
        intent.putExtra("loyaltyCheck",loyaltyCheck);
        intent.putExtra("loyaltyDiscountAmount",loyaltyDiscountAmount);
        startActivityForResult(intent,Utils.COLLECTION_REQUEST);

    }

    private void addTabs(List<MenuMenuData> productData) {

        tabLayout.removeAllTabs();

        for (int i = 0; i < productData.size(); i++) {

            tabLayout.addTab(tabLayout.newTab().setText("" + productData.get(i).getName()));
        }

        tabLayout.getTabAt(0).select();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
//                linearLayoutManager.scrollToPositionWithOffset(tab.getPosition(),-5);
                if (!tabAutoSelect) {
//                    Toast.makeText(Home1.this,"Push Selection",Toast.LENGTH_SHORT).show();
                    pinItemRecyclerAdapter.expandItem(tab.getPosition());
                    linearLayoutManager.scrollToPositionWithOffset(tab.getPosition(), -5);
                }
                tabAutoSelect = false;
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });

    }

    private void tabSelection(int s) {
//        Toast.makeText(Home1.this,"Bef: "+tabAutoSelect,Toast.LENGTH_SHORT).show();
        if (!tabLayout.getTabAt(s).isSelected()) {
            tabAutoSelect = true;
            tabLayout.getTabAt(s).select();
        }
//        if(tabLayout.getTabAt(s).isSelected()){}
//        Toast.makeText(Home1.this,"Aft: "+tabAutoSelect,Toast.LENGTH_SHORT).show();
    }

    private void collapsingBehaviour() {

        appBarLayout = findViewById(R.id.appbar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                    //collapsed
                    if (collapsed) {
                        toot_bar_back.setVisibility(View.VISIBLE);
                        collapsed = false;
                        expanded = true;
                    }
                } else if (verticalOffset == 0) {
                    //Expanded
                    if (expanded) {
                        toot_bar_back.setVisibility(View.GONE);
                        collapsed = true;
                        expanded = false;
                    }

                } else {}

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Utils.startExtraAddingActivityForResult && resultCode == RESULT_OK) {
            MenuDealList dealItem = (MenuDealList) data.getSerializableExtra(Utils.menu_order_item);
            int idSum = data.getIntExtra("idSum", 0);
            addInMenuOrder(dealItem, Utils.typeDeal, null, idSum);

        } else if (requestCode == Utils.startExtraAddingActivityForProductResult && resultCode == RESULT_OK) {
            MenuProductList productItem = (MenuProductList) data.getSerializableExtra(Utils.menu_order_item);
            int idSum = data.getIntExtra("idSum", 0);
            addInMenuOrder(null, Utils.typeProduct, productItem, idSum);
        }

        else if(requestCode==Utils.COLLECTION_REQUEST){

            if(resultCode==RESULT_OK){
                menuOrderDataList.clear();
                collection_list_lay.setVisibility(View.GONE);
                totalPrice = 0.0;
                setGrandTotal(totalPrice);
                loyalty_check_text.setVisibility(View.GONE);
                loyaltyCheck = 0;
            }
        }

    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(home_drawer_nav)) {
            drawerLayout.closeDrawers();
        }

        else if(collection_list_lay.getVisibility()==View.VISIBLE){
            new AlertDialog.Builder(this).setMessage("Are you sure to exit ?...").
                    setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            HomeActivity.super.onBackPressed();
                            dialog.dismiss();
                        }
                    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }
        else {

            super.onBackPressed();
        }
    }

    private double totalPrice = 0;
    ImageView toot_bar_back;
    DrawerLayout drawerLayout;
    TextView tv_signName;
    LinearLayout sign_in_name_lay;
    LinearLayout previous_orders_lay, table_booking_lay;
    RecyclerView recycler;
    TabLayout tabLayout;
    boolean collapsed = true, expanded = true;
    PinItemRecyclerAdapter pinItemRecyclerAdapter;
    private CoordinatorLayout parentLay;
    private boolean tabAutoSelect = false;
    RelativeLayout home_drawer_nav;
    LinearLayoutManager linearLayoutManager;
    LinearLayoutManager collectionListLayoutManager;
    private TextView searchLay;
    private AppBarLayout appBarLayout;
    RecyclerView collectionRecycler;
    RelativeLayout collection_list_lay;
    OrderListListener orderListListener;
    MaterialCardView place_order;
    TextView tv_total_price;
    TextView loyalty_check_text;
    private static double min_order_amount = 0.0;
    private static TextView tv_min_order_amount, tv_restaurant_phone_number, distanceText;
    private static int loyaltyCheck = 0;
    private static int loyaltyDiscountAmount = 0;

}