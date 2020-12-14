package com.example.softec.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.softec.MenuModel.ExtraAddingsCallBack;
import com.example.softec.MenuModel.MenuExtraAddingDeal1;
import com.example.softec.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

public class ExtraAddingsDealLayout1 extends RecyclerView.Adapter<ExtraAddingsDealLayout1.ExtraAddingsDealLayout1VH> {

    Context context;
    ExtraAddingsCallBack extraAddingsCallBack;
    List<MenuExtraAddingDeal1> list_MenuExtraAddingDeal1;
    List<ExtraAddingAdapter> list_ExtraAddingAdapter;

    public ExtraAddingsDealLayout1(Context context, List<MenuExtraAddingDeal1> list_MenuExtraAddingDeal1,ExtraAddingsCallBack extraAddingsCallBack,List<ExtraAddingAdapter> list_ExtraAddingAdapter) {
        this.context = context;
        this.list_ExtraAddingAdapter = list_ExtraAddingAdapter;
        this.list_MenuExtraAddingDeal1 = list_MenuExtraAddingDeal1;
        this.extraAddingsCallBack = extraAddingsCallBack;
    }

    @NonNull
    @Override
    public ExtraAddingsDealLayout1VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new ExtraAddingsDealLayout1VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.extra_addings_deal_layout1, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExtraAddingsDealLayout1VH holder, final int position) {
        holder.shimmer.stopShimmer();
        holder.shimmer.setShimmer(null);

        MenuExtraAddingDeal1 menuExtraAddingDeal1 = list_MenuExtraAddingDeal1.get(position);
        ExtraAddingAdapter extraAddingAdapter = list_ExtraAddingAdapter.get(position);
        holder.itemType.setText(menuExtraAddingDeal1.getName());

        boolean opened = list_MenuExtraAddingDeal1.get(position).isOpen();

        holder.food_item_pin_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean open = list_MenuExtraAddingDeal1.get(position).isOpen();
                list_MenuExtraAddingDeal1.get(position).setOpen(!open);
                notifyDataSetChanged();
            }
        });

        if(opened){
            holder.foodItemsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            holder.foodItemsRecyclerView.setAdapter(extraAddingAdapter);
            holder.foodItemsRecyclerView.setVisibility(View.VISIBLE);
            holder.arrowIcon.setImageResource(R.drawable.arrow_up);
        } else {
            holder.foodItemsRecyclerView.setVisibility(View.GONE);
            holder.arrowIcon.setImageResource(R.drawable.arrow_down);
        }

    }

    @Override
    public int getItemCount() {
        return list_MenuExtraAddingDeal1.size();
    }

    public class ExtraAddingsDealLayout1VH extends RecyclerView.ViewHolder {

        ImageView arrowIcon;
        TextView itemType;
        RecyclerView foodItemsRecyclerView;
        ShimmerFrameLayout shimmer;
        RelativeLayout food_item_pin_layout;


        public ExtraAddingsDealLayout1VH(@NonNull View itemView) {
            super(itemView);
            arrowIcon = itemView.findViewById(R.id.arrow_icon);
            itemType = itemView.findViewById(R.id.item_type);
            foodItemsRecyclerView = itemView.findViewById(R.id.food_item_pin_layout_recycler_view);
            shimmer = itemView.findViewById(R.id.shimmer);
            food_item_pin_layout = itemView.findViewById(R.id.food_item_pin_layout);
        }
    }


}
