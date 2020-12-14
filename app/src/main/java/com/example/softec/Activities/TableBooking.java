package com.example.softec.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.softec.Api.ApiClient;
import com.example.softec.Api.ApiInterface;
import com.example.softec.PojoClasses.AddBookingModel;
import com.example.softec.PojoClasses.AllBookingsModel;
import com.example.softec.PojoClasses.Bookings;
import com.example.softec.PojoClasses.GetAvailableBookingDays;
import com.example.softec.PojoClasses.GetAvailableSeats;
import com.example.softec.PojoClasses.GetAvailableSeatsModel;
import com.example.softec.PojoClasses.TableBookingPojo;
import com.example.softec.R;
import com.example.softec.StaticClasses.SharedPreference;
import com.example.softec.StaticClasses.StaticClass;
import com.example.softec.adapter.AllBookingsAdapter;
import com.example.softec.databinding.ActivityTableBookingBinding;
import com.example.softec.databinding.NewTableBookingBinding;
import com.google.android.material.tabs.TabLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TableBooking extends BaseActivity {

    ActivityTableBookingBinding tableBind;
    String selectedTime = "";
    String selectedDate = "";
    List<Bookings> bookingList = new ArrayList<>();

    //get available seats
    GetAvailableSeats getAvailableSeats;
    GetAvailableSeatsModel getAvailableSeatsModel;
    List<GetAvailableBookingDays> availableBookingDaysList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tableBind = ActivityTableBookingBinding.inflate(getLayoutInflater());
        setContentView(tableBind.getRoot());
        initials();
    }

    private void initials() {

        tableBind.rvTableBooking.setLayoutManager(new LinearLayoutManager(this));

        tableBind.addNewBookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddBookingLAy();
            }
        });

        tableBind.addNewBookingRetryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAvailability();
            }
        });

        tableBind.retryAllBookingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allBookings();
            }
        });

        tableBind.backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getAvailability();
        allBookings();
    }

    NewTableBookingBinding newTableBookingBinding;
    AlertDialog dialog;

    @SuppressLint("SetTextI18n")
    private void showAddBookingLAy() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        newTableBookingBinding = NewTableBookingBinding.inflate(getLayoutInflater());
        builder.setView(newTableBookingBinding.getRoot());
        dialog = builder.create();
        dialog.show();
        final Calendar c = Calendar.getInstance();
        final String date = getAvailableSeatsModel.getCurrent_date();
        String dateArray[] = date.split("-");
        c.set(Integer.parseInt(dateArray[0]),(Integer.parseInt(dateArray[1])-1),Integer.parseInt(dateArray[2]));

        final String[] minutes = getResources().getStringArray(R.array.minutes);
        newTableBookingBinding.minuteSpinner.setAdapter(new ArrayAdapter(TableBooking.this,android.R.layout.simple_list_item_1,minutes));

        final List<String[]> listOfHours = new ArrayList<>();

        for(int i=0;i<availableBookingDaysList.size();i++){
            int start_h = Integer.parseInt(availableBookingDaysList.get(i).getBooking_from().split(":")[0]);
            int end_h = Integer.parseInt(availableBookingDaysList.get(i).getBooking_to().split(":")[0]);
            final String[] hours = new String[1+(end_h-start_h)];
            int hh = start_h;
            for (int j = 0; j <=(end_h-start_h); j++) {
                hours[j] = hh < 10 ? "0" + hh : hh + "";
                hh = start_h + j + 1;
            }
            listOfHours.add(hours);
        }

//        newTableBookingBinding.hourSpinner.setAdapter(new ArrayAdapter(TableBooking.this,android.R.layout.simple_list_item_1,listOfHours.get(c.get(Calendar.DAY_OF_WEEK))));

        final String days[] = new String[availableBookingDaysList.size()];

        for(int i=0;i<availableBookingDaysList.size();i++){

            days[i] = availableBookingDaysList.get(i).getDay_name() + " , "+date;
        }


        newTableBookingBinding.backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        newTableBookingBinding.tvAvailableSeats.setText("Enter Require Seats Available: " + getAvailableSeatsModel.getAvailable_seats());

//        final Calendar cldr = Calendar.getInstance();
        int day = c.get(Calendar.DAY_OF_MONTH);
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);

        final DatePickerDialog picker = new DatePickerDialog(TableBooking.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectedDate = year+"-"+(month+1)+"-"+dayOfMonth;

                        try {
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(year,month,dayOfMonth);
                            int dayIndex = calendar.get(Calendar.DAY_OF_WEEK);

                            String day = dayIndex==1?"sunday":dayIndex==2?"monday":dayIndex==3?"tuesday":dayIndex==4?"wednesday":dayIndex==5?"thursday":dayIndex==6?"friday":"saturday";

                            for(int i=0;i<availableBookingDaysList.size();i++){
                                if(day.equals(availableBookingDaysList.get(i).getDay_name().toLowerCase().trim())){
                                    newTableBookingBinding.tvDatePicker.setText(day.toUpperCase()+" "+selectedDate);
                                    newTableBookingBinding.hourSpinner.setAdapter(new ArrayAdapter(TableBooking.this,android.R.layout.simple_list_item_1,listOfHours.get(i)));
                                    break;
                                }
                            }

                        }catch (Exception e){}

                    }

                }, year, month, day);
        picker.getDatePicker().setMinDate(c.getTimeInMillis());
        c.add(Calendar.MONTH, +1);
        picker.getDatePicker().setMaxDate(c.getTimeInMillis());

        newTableBookingBinding.tvDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                picker.show();
            }
        });

        newTableBookingBinding.addBookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String no_of_people = newTableBookingBinding.tvNoOfPeople.getText().toString();

                if(no_of_people.length()==0){
                    st.toast("Number of People Can't be 0"); return;
                }

                int no = Integer.parseInt(newTableBookingBinding.tvNoOfPeople.getText().toString());

                if(no==0){st.toast("Please enter number of people"); return;}

                if(no>getAvailableSeatsModel.getAvailable_seats()){
                    st.toast("Number of people can't be greater than available seats"); return;
                }
                if(selectedDate==""){
                    st.toast("Please Select Date"); return;
                }

                selectedTime = newTableBookingBinding.hourSpinner.getSelectedItem()+":"+newTableBookingBinding.minuteSpinner.getSelectedItem();
                addBooking(sp.getStringValue("phone"),selectedDate,selectedTime,no_of_people,newTableBookingBinding.etSpecialRequirements.getText().toString());
            }
        });

//        newTableBookingBinding.tv_special_heading.

        String sr = "";

        newTableBookingBinding.etSpecialRequirements.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                int length = newTableBookingBinding.etSpecialRequirements.getText().toString().length();
                newTableBookingBinding.tvSpecialHeading.setText("Special Requirements ("+length+"/140)");
            }
        });



        newTableBookingBinding.tvNoOfPeople.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(newTableBookingBinding.tvNoOfPeople.getText().toString().length()>0) {
                    int no = Integer.parseInt(newTableBookingBinding.tvNoOfPeople.getText().toString());
                    if (no > getAvailableSeatsModel.getAvailable_seats()) {
                        newTableBookingBinding.tvNoOfPeople.setTextColor(getResources().getColor(R.color.red_color));
                    } else {
                        newTableBookingBinding.tvNoOfPeople.setTextColor(getResources().getColor(R.color.black));
                    }
                }

            }
        });

    }


    //show DatePicker
    private void openDatePicker(String currentDate){

    }

    //api callings
    private void getAvailability() {

        controlAvailAddButtonLay(0);

        Call<GetAvailableSeats> call = ApiClient.getRetrofit().create(ApiInterface.class).getAvailableSeats();

        call.enqueue(new Callback<GetAvailableSeats>() {
            @Override
            public void onResponse(Call<GetAvailableSeats> call, Response<GetAvailableSeats> response) {
                if(response.body()!=null){
                    if (response.body().getStatus().equals("success")) {
                        getAvailableSeats = response.body();
                        getAvailableSeatsModel = getAvailableSeats.getData();
                        availableBookingDaysList = getAvailableSeatsModel.getBookings();
                        controlAvailAddButtonLay(1);
                    } else {
                        controlAllBookingsLay(2);
                    }
                }
                else {
                    st.toast("null body");
                }
            }

            @Override
            public void onFailure(Call<GetAvailableSeats> call, Throwable t) {
                controlAvailAddButtonLay(2);
            }
        });

    }
    private void addBooking(String phone, final String date, final String time, final String no_of_guests,String sr){

        phone = phone.substring(0,1).equals("+")?phone.substring(1,phone.length()):phone;

        sr = sr.trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Booking Seats...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Call<AddBookingModel> call = ApiClient.getRetrofit().create(ApiInterface.class).
                addBooking(phone,
                        date,
                        time,
                        no_of_guests,
                        sr);
        call.enqueue(new Callback<AddBookingModel>() {
            @Override
            public void onResponse(Call<AddBookingModel> call, Response<AddBookingModel> response) {

                progressDialog.dismiss();
                st.toast(response.body().getMessage());

                if(response.body().getStatus().equals("success")){
                    dialog.dismiss();
                    controlAllBookingsLay(1);
                    allBookings();
                }

            }

            @Override
            public void onFailure(Call<AddBookingModel> call, Throwable t) {
                progressDialog.dismiss();
                st.toast("Network Error Please Try Again");
            }
        });

    }
    private void allBookings(){

        String num = sp.getStringValue("phone");
        num = num.substring(0,1).equals("+")?num.substring(1,num.length()):num;

        controlAllBookingsLay(0);
//
        Call<AllBookingsModel> call = ApiClient.getRetrofit().create(ApiInterface.class).allBooking(num);

        call.enqueue(new Callback<AllBookingsModel>() {
            @Override
            public void onResponse(Call<AllBookingsModel> call, Response<AllBookingsModel> response) {

                if(response.body()!=null){
                    if(response.body().getStatus().equals("success")){

                        bookingList = response.body().getBookings();
                        controlAllBookingsLay(1);
                    }
                    else{
                        controlAllBookingsLay(2);
                    }
                }

                else {
                    st.toast("not body");
                }


            }

            @Override
            public void onFailure(Call<AllBookingsModel> call, Throwable t) {

                controlAllBookingsLay(2);

            }
        });

    }

    // layout controllers
    private void controlAvailAddButtonLay(int i) {

        tableBind.availPB.setVisibility(i == 0 ? View.VISIBLE : View.GONE);
        tableBind.addNewBookingBtn.setVisibility(i == 1 ? View.VISIBLE : View.GONE);
        tableBind.addNewBookingRetryBtn.setVisibility(i == 2 ? View.VISIBLE : View.GONE);

    }
    private void controlAllBookingsLay(int i) {
        tableBind.recyclerPB.setVisibility(i == 0 ? View.VISIBLE : View.GONE);
        tableBind.rvTableBooking.setVisibility(i == 1 ? View.VISIBLE : View.GONE);
        tableBind.retryAllBookingsBtn.setVisibility(i == 2 ? View.VISIBLE : View.GONE);

        if(i==1){
            tableBind.rvTableBooking.setAdapter(new AllBookingsAdapter(this,bookingList));
        }

    }

}