package com.example.softec.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softec.Listeners.GetClicks;
import com.example.softec.MenuModel.MenuData;
import com.example.softec.MenuModel.MenuMenuData;
import com.example.softec.MenuModel.MenuSelectionCallBack;
import com.example.softec.NewApis.OrderListListener;
import com.example.softec.R;
import com.example.softec.StaticClasses.StaticClass;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

public class PinItemRecyclerAdapter extends RecyclerView.Adapter<PinItemRecyclerAdapter.PinItemClass> {

    Context context;
    GetClicks getClicks;
    StaticClass st;
    boolean shimmering = true;
    OrderListListener orderListListener;
    List<MenuMenuData> menuDataList;
    MenuSelectionCallBack menuSelectionCallBack;

    public PinItemRecyclerAdapter(Context context, List<MenuMenuData> menuDataList, OrderListListener orderListListener,MenuSelectionCallBack menuSelectionCallBack) {
        this.context = context;
        this.orderListListener = orderListListener;
        this.menuDataList = menuDataList;
        st = new StaticClass(context);
        this.menuSelectionCallBack = menuSelectionCallBack;
    }

    public void setGetClicks(GetClicks getClicks) {
        this.getClicks = getClicks;
    }

    @NonNull
    @Override
    public PinItemRecyclerAdapter.PinItemClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.food_item_pin_layout, parent, false);
        return new PinItemClass(v);
    }

    public void expandItem(int i) {
        menuDataList.get(i).setOpened(true);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull final PinItemRecyclerAdapter.PinItemClass holder, final int position) {


        if (shimmering) {
            holder.shimmer.startShimmer();
        } else {
            final MenuMenuData menuMenuData = menuDataList.get(position);

            holder.itemType.setText(menuMenuData.getName());
            holder.shimmer.stopShimmer();
            holder.shimmer.setShimmer(null);

            if (menuMenuData.isOpened()) {
                holder.foodItemsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                holder.foodItemsRecyclerView.setAdapter(
                        new HomeFoodItemListAdapter(context, position,
                                menuMenuData.getType(),
                                menuMenuData.getDealList(),
                                menuMenuData.getProductList(),
                                orderListListener,
                                menuSelectionCallBack
                        ));
                holder.foodItemsRecyclerView.setVisibility(View.VISIBLE);
                holder.arrowIcon.setImageResource(R.drawable.arrow_up);
            } else {
                holder.foodItemsRecyclerView.setVisibility(View.GONE);
                holder.arrowIcon.setImageResource(R.drawable.arrow_down);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menuMenuData.setOpened(!menuMenuData.isOpened());
                    getClicks.onItemSelect(position);
                    notifyDataSetChanged();
                }
            });

        }
    }

    public void updateAdapter(MenuData menuData) {
        this.menuDataList = menuData.getData();
        shimmering = false;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return shimmering ? 10 : menuDataList.size();
    }

    public class PinItemClass extends RecyclerView.ViewHolder {

        ImageView arrowIcon;
        TextView itemType;
        RecyclerView foodItemsRecyclerView;
        ShimmerFrameLayout shimmer;

        public PinItemClass(@NonNull View itemView) {
            super(itemView);
            arrowIcon = itemView.findViewById(R.id.arrow_icon);
            itemType = itemView.findViewById(R.id.item_type);
            foodItemsRecyclerView = itemView.findViewById(R.id.food_item_pin_layout_recycler_view);
            shimmer = itemView.findViewById(R.id.shimmer);

        }
    }

}
