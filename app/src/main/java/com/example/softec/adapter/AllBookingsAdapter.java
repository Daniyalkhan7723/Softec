package com.example.softec.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softec.PojoClasses.Bookings;
import com.example.softec.R;

import java.util.List;

public class AllBookingsAdapter extends RecyclerView.Adapter<AllBookingsAdapter.PinItemClass> {

    Context context;
    List<Bookings> bookingList;

    public AllBookingsAdapter(Context context,List<Bookings> bookingList) {

        this.context = context;
        this.bookingList = bookingList;

    }

    @NonNull
    @Override
    public AllBookingsAdapter.PinItemClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.booking_layout,parent,false);
        return new PinItemClass(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final AllBookingsAdapter.PinItemClass holder, final int position) {

        int i = (bookingList.size()-1)-position;

        Bookings bookingModel = bookingList.get(i);

        String status = bookingModel.getStatus();

        holder.tv_no_of_guests.setText(bookingModel.getNo_of_guests()+"");
        holder.tv_status.setText(status);
        holder.tv_time.setText(bookingModel.getTime());
        holder.tv_date.setText(bookingModel.getDate());
        holder.tv_special_note.setText(bookingModel.getSpecial_requirements());

        holder.tv_special_note.setVisibility(bookingModel.getSpecial_requirements().length()==0?View.GONE:View.VISIBLE);

        holder.tv_status.setTextColor(context.getResources().getColor(status.toLowerCase().equals("pending")?R.color.red_color:R.color.green_color));

    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public class PinItemClass extends RecyclerView.ViewHolder{

        TextView tv_no_of_guests,tv_status,tv_time,tv_date,tv_special_note;

        public PinItemClass(@NonNull View itemView) {
            super(itemView);
            tv_no_of_guests = itemView.findViewById(R.id.tv_no_of_guests);
            tv_status = itemView.findViewById(R.id.tv_status);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_special_note = itemView.findViewById(R.id.tv_special_note);
            tv_date = itemView.findViewById(R.id.tv_date);
        }
    }
}
