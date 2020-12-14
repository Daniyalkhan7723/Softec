package com.example.softec.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softec.MenuModel.MenuOrderData;
import com.example.softec.PojoClasses.CollectionList;
import com.example.softec.R;
import com.example.softec.StaticClasses.StaticClass;

import java.util.ArrayList;

public class CollectionOrderAdapter extends RecyclerView.Adapter<CollectionOrderAdapter.CollectionVH> {

    Context context;
    ArrayList<MenuOrderData> menuOrderDataList;
    StaticClass st;

    public CollectionOrderAdapter(Context context, ArrayList<MenuOrderData> menuOrderDataList) {
        this.context = context;
        this.menuOrderDataList = menuOrderDataList;
        st = new StaticClass(context);
    }

    @NonNull
    @Override
    public CollectionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CollectionVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.j_collection_list_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionVH holder, int position) {

        final MenuOrderData menuOrderData = menuOrderDataList.get(position);
        holder.food_item_name.setText(menuOrderData.getQuantity()+" x "+menuOrderData.getName());
        holder.food_item_description.setText("Description Processing");
        holder.food_item_price.setText("$"+menuOrderData.getTotal_price());

    }

    @Override
    public int getItemCount() {
        return menuOrderDataList.size();
    }

    class CollectionVH extends RecyclerView.ViewHolder{

        TextView food_item_name,food_item_price,food_item_description;

        public CollectionVH(@NonNull View itemView) {

            super(itemView);

            food_item_name = itemView.findViewById(R.id.food_item_name);
            food_item_price = itemView.findViewById(R.id.food_item_price);
            food_item_description = itemView.findViewById(R.id.food_item_description);

        }
    }

}
