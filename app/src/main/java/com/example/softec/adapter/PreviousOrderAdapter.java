package com.example.softec.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softec.Listeners.PreviousOrderListener;
import com.example.softec.MenuModel.MenuOrderData;
import com.example.softec.PojoClasses.CollectionList;
import com.example.softec.PojoClasses.OtherData;
import com.example.softec.PojoClasses.PreviousOrderData;
import com.example.softec.R;
import com.example.softec.StaticClasses.StaticClass;
import com.google.android.material.card.MaterialCardView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PreviousOrderAdapter extends RecyclerView.Adapter<PreviousOrderAdapter.CollectionVH> {

    Context context;
    StaticClass st;
    PreviousOrderListener previousOrderListener;
    List<PreviousOrderData> previousOrderDataList;
    Gson gson;

    public PreviousOrderAdapter(Context context,List<PreviousOrderData> previousOrderDataList,PreviousOrderListener previousOrderListener) {
        this.context = context;
        this.previousOrderListener = previousOrderListener;
        this.previousOrderDataList = previousOrderDataList;
        st = new StaticClass(context);
        gson = new Gson();
    }

    @NonNull
    @Override
    public CollectionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CollectionVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.previous_order,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionVH holder, int j) {

        final int position = (previousOrderDataList.size()-1)-j;

        Type listType = new TypeToken<ArrayList<MenuOrderData>>(){}.getType();
        String itemNames = "";

        final PreviousOrderData previous = previousOrderDataList.get(position);
        OtherData otherData = gson.fromJson(previous.getOther_data().toString(),OtherData.class);

        final ArrayList<MenuOrderData> collectionLists = gson.fromJson(previous.getOrder_data().toString(),listType);

        holder.totalAmount.setText("$"+otherData.getFinal_amount());
        for(int i=0;i<collectionLists.size();i++) {
            itemNames += collectionLists.get(i).getName() + ",";
        }

        holder.items.setText(itemNames.substring(0,itemNames.length()-1));

        holder.previous_parent_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousOrderListener.getPreviousData(previous);
            }
        });


    }

    @Override
    public int getItemCount() {
        return previousOrderDataList.size();
    }

    class CollectionVH extends RecyclerView.ViewHolder{

        MaterialCardView previous_parent_cv;
        TextView totalAmount,items;

        public CollectionVH(@NonNull View itemView) {

            super(itemView);

            previous_parent_cv = itemView.findViewById(R.id.previous_parent_cv);
            totalAmount = itemView.findViewById(R.id.totalAmount);
            items = itemView.findViewById(R.id.items);
        }
    }

}
