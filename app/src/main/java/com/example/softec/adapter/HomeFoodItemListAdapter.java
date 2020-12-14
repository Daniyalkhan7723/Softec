package com.example.softec.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softec.MenuModel.MenuDealList;
import com.example.softec.MenuModel.MenuProductList;
import com.example.softec.MenuModel.MenuSelectionCallBack;
import com.example.softec.NewApis.OrderListListener;
import com.example.softec.R;
import com.example.softec.StaticClasses.StaticClass;
import com.example.softec.StaticClasses.Utils;

import java.util.List;

public class HomeFoodItemListAdapter extends RecyclerView.Adapter<HomeFoodItemListAdapter.RecyclerViewHolder> {
    Context context;
    String type;
    StaticClass st;
    int index;
    OrderListListener orderListListener;
    List<MenuDealList> menuDealLists;
    List<MenuProductList> menuProductLists;

    MenuSelectionCallBack menuSelectionCallBack;

    public HomeFoodItemListAdapter(Context context,
                                   int index,
                                   String type,
                                   List<MenuDealList> menuDealLists,
                                   List<MenuProductList>
                                           menuProductLists, OrderListListener orderListListener,MenuSelectionCallBack menuSelectionCallBack) {
        this.context = context;
        this.index = index;
        this.menuSelectionCallBack = menuSelectionCallBack;
        this.type = type;
        this.menuDealLists = menuDealLists;
        this.menuProductLists = menuProductLists;
        st = new StaticClass(context);
        this.orderListListener = orderListListener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_row, parent, false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {


        String name = "";
        String description = "";
        String price = "";


        if (type.equals(Utils.typeDeal)) {
            final MenuDealList menuDealList = menuDealLists.get(position);
            name = menuDealList.getName();
            price = String.valueOf(menuDealList.getPrice());
            description = menuDealList.getDescription();

            holder.foodItemDescription.setVisibility(View.VISIBLE);
            holder.foodItemDescription.setText(description);

            holder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (menuDealList.getExtraAddings().size() == 0) {
                                menuSelectionCallBack.addDeal(menuDealList);
                            }
                            else {
                                menuSelectionCallBack.openNewActivityForDeal(menuDealList);
                            }
                        }
                    }
            );

            if (menuDealList.getExtraAddings().size() > 0) {
                holder.iv_next_indicator.setImageResource(R.drawable.ic_baseline_navigate_next);
            }
            else {
                holder.iv_next_indicator.setImageBitmap(null);
            }

        } else {
            final MenuProductList menuProductList = menuProductLists.get(position);
            holder.foodItemDescription.setVisibility(View.GONE);
            name = menuProductList.getName();
            price = String.valueOf(menuProductList.getPrice());
            holder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if(menuProductList.getMenuProductSizeList().size()==0){
                                menuSelectionCallBack.addProduct(menuProductList);
                            }

                            else {
                                menuSelectionCallBack.openNewActivityForProduct(menuProductList);
                            }

                        }
                    }
            );


            if (menuProductList.getMenuProductSizeList().size() > 0) {
                holder.foodItemPrice.setVisibility(View.GONE);
                holder.iv_next_indicator.setVisibility(View.VISIBLE);
            }
            else {
                holder.foodItemPrice.setVisibility(View.VISIBLE);
                holder.iv_next_indicator.setVisibility(View.GONE);
            }

        }

        holder.foodItemName.setText(name);
        holder.foodItemPrice.setText("$" + price);

    }

    @Override
    public int getItemCount() {
        return type.equals(Utils.typeDeal) ? menuDealLists.size() : menuProductLists.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView foodItemName, foodItemDescription, foodItemPrice;
        ImageView iv_next_indicator;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            foodItemName = itemView.findViewById(R.id.food_item_name);
            foodItemDescription = itemView.findViewById(R.id.food_item_description);
            foodItemPrice = itemView.findViewById(R.id.food_item_price);
            iv_next_indicator = itemView.findViewById(R.id.iv_next_indicator);
        }
    }

}