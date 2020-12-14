package com.example.softec.Api;

import com.example.softec.MenuModel.MenuData;
import com.example.softec.PojoClasses.AddBookingModel;
import com.example.softec.PojoClasses.AllBookingsModel;
import com.example.softec.PojoClasses.Check_Coupon;
import com.example.softec.PojoClasses.GetAvailableSeats;
import com.example.softec.PojoClasses.GetUser;
import com.example.softec.PojoClasses.LoyaltyAvailable;
import com.example.softec.PojoClasses.NumberVerification;
import com.example.softec.PojoClasses.OrderPlaceModel;
import com.example.softec.PojoClasses.PreviousOrders;
import com.example.softec.VoucherTesting;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiInterface {

    //OK
    @GET("get_home_data")
    Call<MenuData> getMenuData();

    @PUT("check_coupon/{user_id}/{coupon}")
    Call<Check_Coupon> check_coupon(@Path("user_id") String user_id, @Path("coupon") String coupon);

    @FormUrlEncoded
    @POST("api/voucher/update_voucher.php")
    Call<VoucherTesting> VoucherTEST(@Field("user_id") String user_id, @Field("voucher_id") String voucher_id);

    @FormUrlEncoded
    @POST("get_order")
    Call<OrderPlaceModel> place_order(@Field("other_data") String other_data, @Field("order_data") String order_data);

    @PUT("check-loyality/{user_id}")//ok
    Call<LoyaltyAvailable> loyaltyAvailable(@Path("user_id") String user_id);

    @PUT("order-history/{user_id}") //ok
    Call<PreviousOrders> getPreviousOrder(@Path("user_id") String user_id);

    @POST("get_available_seats") //ok
    Call<GetAvailableSeats> getAvailableSeats();

    @FormUrlEncoded
    @POST("add_booking") //ok
    Call<AddBookingModel> addBooking(
            @Field("phone") String phone,
            @Field("date") String date,
            @Field("time") String time,
            @Field("no_of_guests") String no_of_guests,
            @Field("special_requirements") String special_requirements
    );

    @FormUrlEncoded
    @POST("all_booking") //ok
    Call<AllBookingsModel> allBooking(
            @Field("phone") String phone
    );

    @FormUrlEncoded
    @POST("contact_us")
    Call<NumberVerification> contactUS(
            @Field("email") String email,
            @Field("message") String message
    );

    @FormUrlEncoded
    @POST("user_login") //ok
    Call<NumberVerification> verificationRequest(
            @Field("phone") String phone,
            @Field("code") String code,
            @Field("email") String email
    );

    @PUT("profile-details/{phone}") //ok
    Call<GetUser> getUser(@Path("phone") String phone);

    @FormUrlEncoded
    @POST("update-profile") //ok
    Call<NumberVerification> updateProfile(
            @Field("phone") String phone,
            @Field("user_name") String user_name,
            @Field("tip") String tip,
            @Field("promotion") String promotion,
            @Field("tip_status") Integer tip_status,
            @Field("email") String email
    );

}