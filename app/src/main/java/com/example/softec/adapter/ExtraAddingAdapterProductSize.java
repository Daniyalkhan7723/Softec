package com.example.softec.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softec.MenuModel.ExtraAddingsCallBack;
import com.example.softec.MenuModel.MenuProductSize;
import com.example.softec.R;
import com.example.softec.StaticClasses.StaticClass;

import java.util.List;

public class ExtraAddingAdapterProductSize extends RecyclerView.Adapter<ExtraAddingAdapterProductSize.ExtraAddingAdapterVH> {

    Context context;
    ExtraAddingsCallBack extraAddingsCallBack;
    List<MenuProductSize> menuProductSizeList;
    StaticClass st;
    int selected = -1;

    public ExtraAddingAdapterProductSize(Context context, List<MenuProductSize> menuProductSizeList, ExtraAddingsCallBack extraAddingsCallBack){
        this.context = context;
        this.menuProductSizeList = menuProductSizeList;
        this.extraAddingsCallBack = extraAddingsCallBack;
        st = new StaticClass(context);
    }

    @NonNull
    @Override
    public ExtraAddingAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExtraAddingAdapterVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.extra_addings_layout_for_product_size,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExtraAddingAdapterVH holder, final int position) {
        final MenuProductSize menuProductSize = menuProductSizeList.get(position);

        holder.tv_extra_adding_price.setText("$"+menuProductSize.getPrice());
        holder.tv_product_size_title.setText(menuProductSize.getName());
        holder.rv_extra_adding_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected = position;
                extraAddingsCallBack.addProductSize(menuProductSize);
                notifyDataSetChanged();
            }
        });
        holder.iv_extra_adding.setImageDrawable(context.getResources().getDrawable(selected==position?R.drawable.black_rect_check_box:R.drawable.ic_baseline_uncheck_box));
    }

    @Override
    public int getItemCount() {
        return menuProductSizeList.size();
    }

    class  ExtraAddingAdapterVH extends RecyclerView.ViewHolder{

        TextView tv_product_size_title,tv_extra_adding_price;
        RelativeLayout rv_extra_adding_parent;
        ImageView iv_extra_adding;

        public ExtraAddingAdapterVH(@NonNull View itemView) {
            super(itemView);
            tv_product_size_title = itemView.findViewById(R.id.tv_product_size_title);
            rv_extra_adding_parent = itemView.findViewById(R.id.rv_extra_adding_parent);
            iv_extra_adding = itemView.findViewById(R.id.iv_extra_adding);
            tv_extra_adding_price = itemView.findViewById(R.id.tv_extra_adding_price);
        }
    }

}
