package com.example.softec.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softec.MenuModel.MenuOrderData;
import com.example.softec.R;
import com.example.softec.StaticClasses.StaticClass;

import java.util.ArrayList;

public class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.SingleProductAdapterVH> {

    Context context;
    StaticClass st;
    ArrayList<MenuOrderData> menuOrderDataList;

    public ReceiptAdapter(Context context,  ArrayList<MenuOrderData> menuOrderDataList) {
        this.context = context;
        st = new StaticClass(context);
        this.menuOrderDataList = menuOrderDataList;
    }

    @NonNull
    @Override
    public SingleProductAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.single_reciept_item,parent,false);
        return new SingleProductAdapterVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleProductAdapterVH holder, final int position) {
        MenuOrderData menuOrderData =  menuOrderDataList.get(position);
        String name = menuOrderData.getName();
        String qty = menuOrderData.getQuantity()+"";
        String total = menuOrderData.getTotal_price()+"";
        String desc = menuOrderData.getDescription();

        holder.item.setText(name);
        holder.qty.setText(qty);
        holder.total.setText("$"+total);
        holder.description.setText(desc);

    }

    @Override
    public int getItemCount() {
        return menuOrderDataList.size();
    }

    class  SingleProductAdapterVH extends RecyclerView.ViewHolder{

        TextView item,qty,total,description;

        public SingleProductAdapterVH(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            qty = itemView.findViewById(R.id.qty);
            total = itemView.findViewById(R.id.total);
            description = itemView.findViewById(R.id.description);
        }
    }

}