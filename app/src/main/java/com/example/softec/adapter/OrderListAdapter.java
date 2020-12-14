package com.example.softec.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softec.MenuModel.MenuOrderData;
import com.example.softec.MenuModel.MenuSelectionCallBack;
import com.example.softec.NewApis.OrderListListener;
import com.example.softec.OrderCollectionClasses.OrderCollection;
import com.example.softec.PojoClasses.CollectionList;
import com.example.softec.R;
import com.example.softec.StaticClasses.StaticClass;

import java.util.ArrayList;
import java.util.List;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.OrderListAdapterVH> {

    Context context;
    ArrayList<CollectionList> collectionLists;
    OrderListListener orderListListener;
    StaticClass st;
//    List<OrderCollection> orderCollectionList;

    List<MenuOrderData> menuOrderDataList;
    MenuSelectionCallBack menuSelectionCallBack;

    public OrderListAdapter(Context context, List<MenuOrderData> menuOrderDataList, MenuSelectionCallBack menuSelectionCallBack,OrderListListener orderListListener){
        this.context = context;
        st = new StaticClass(context);
        this.orderListListener = orderListListener;
        this.menuOrderDataList = menuOrderDataList;
        this.menuSelectionCallBack = menuSelectionCallBack;
    }

    @NonNull
    @Override
    public OrderListAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderListAdapterVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.overlay_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderListAdapterVH holder, final int position) {

        final MenuOrderData menuOrderData = menuOrderDataList.get(position);
        holder.tv_food_name.setText(menuOrderData.getName());
        holder.tv_food_price.setText("$" + menuOrderData.getTotal_price());
        holder.item_quantity_tv.setText(String.valueOf(menuOrderData.getQuantity()));

        holder.addLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.item_quantity_lay.setAnimation(st.randomAnimations());
                orderListListener.addPrice(position);
            }
        });

        holder.subLay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.item_quantity_lay.setAnimation(st.randomAnimations());
                orderListListener.subPrice(position);
            }
        });
//
        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderListListener.deleteExistingItem(position);
            }
        });

        holder.rv_selected_item_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderListListener.openItem(menuOrderData,position);
            }
        });

    }

    public void updateOderListAdapter(List<MenuOrderData> menuOrderDataList){

        this.menuOrderDataList = menuOrderDataList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount(){ return menuOrderDataList.size(); }

    class OrderListAdapterVH extends RecyclerView.ViewHolder{

        TextView tv_food_name;
        TextView tv_food_price;
        LinearLayout item_quantity_lay;
        TextView item_quantity_tv;
        LinearLayout addLay,subLay;
        ImageView deleteItem;
        RelativeLayout rv_selected_item_parent;

        public OrderListAdapterVH(@NonNull View itemView) {

            super(itemView);

            tv_food_name = itemView.findViewById(R.id.tv_food_name);
            tv_food_price = itemView.findViewById(R.id.tv_food_price);
            item_quantity_lay = itemView.findViewById(R.id.item_quantity_lay);
            item_quantity_tv = itemView.findViewById(R.id.item_quantity_tv);
            addLay = itemView.findViewById(R.id.addLay);
            subLay=itemView.findViewById(R.id.subLay);
            deleteItem = itemView.findViewById(R.id.deleteItem);
            rv_selected_item_parent = itemView.findViewById(R.id.rv_selected_item_parent);


        }
    }

}
