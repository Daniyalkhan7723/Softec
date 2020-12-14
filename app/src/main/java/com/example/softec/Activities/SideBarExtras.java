package com.example.softec.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import com.example.softec.Api.ApiClient;
import com.example.softec.Api.ApiInterface;
import com.example.softec.PojoClasses.NumberVerification;
import com.example.softec.R;
import com.example.softec.adapter.FaqsAdapter;
import com.example.softec.databinding.ActivitySideBarExtrasBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SideBarExtras extends BaseActivity {

    int count = 0;

    ActivitySideBarExtrasBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySideBarExtrasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(getIntent()!=null){

            count = getIntent().getIntExtra("count",0);

        }

        controlLay(count);

        binding.backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        binding.btnSendContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = binding.etEmail.getText().toString();
                String message =  binding.etMessage.getText().toString();

                if(!isEmailValid(email)){ binding.etEmail.requestFocus(); binding.etEmail.setError("Please enter valid email format"); return; }
                if(email.length()==0){ binding.etEmail.requestFocus(); binding.etEmail.setError("Please enter email"); return;}
                if(message.length()==0){ binding.etMessage.requestFocus(); binding.etMessage.setError("Please enter your message");return;}

                contactUs(email,message);
            }
        });

    }

    private void contactUs(String email, String message){

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Sending...Please wait...");
        dialog.setCancelable(false);
        dialog.show();
        Call<NumberVerification> call = ApiClient.getRetrofit().create(ApiInterface.class).contactUS(email,message);
        call.enqueue(new Callback<NumberVerification>() {
            @Override
            public void onResponse(Call<NumberVerification> call, Response<NumberVerification> response) {
                st.toast("Thanks For Contacting Us. We will response You soon");
                binding.etEmail.setText("");binding.etMessage.setText("");
                dialog.dismiss();
                finish();
            }

            @Override
            public void onFailure(Call<NumberVerification> call, Throwable t) {
                st.toast("Try again Later.");
                binding.etEmail.setText("");binding.etMessage.setText("");
                dialog.dismiss();
            }
        });


    }

    private void controlLay(int i){
        String title = i==0?"CONTACT US":i==1?"TERMS AND CONDITIONS":i==2?"FAQ's":i==3?"PRIVACY POLICY":"";
        binding.contactUsLay.setVisibility(i==0? View.VISIBLE:View.GONE);
        binding.termsAndConditions.setVisibility(i==1 || i==3? View.VISIBLE:View.GONE);
        binding.faqLay.setVisibility(i==2?View.VISIBLE:View.GONE);

        binding.termPrivacyText.setText(title);
        binding.title.setText(title);

        binding.rvFaqs.setLayoutManager(new LinearLayoutManager(this));
        binding.rvFaqs.setAdapter(new FaqsAdapter(this));

    }


}
