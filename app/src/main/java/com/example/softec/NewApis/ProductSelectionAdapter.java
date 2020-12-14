package com.example.softec.NewApis;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.softec.NewApis.ModelClasses.SubProducts;
import com.example.softec.R;
import com.example.softec.StaticClasses.StaticClass;

import java.util.List;

public class ProductSelectionAdapter extends RecyclerView.Adapter<ProductSelectionAdapter.SingleProductAdapterVH> {

    Context context;
    StaticClass st;
    List<SubProducts> subProductsList;
    SubProListener subProListener;
    public int selected = -1, quantity = 1, multiple_selection = 0;
    SubProducts subProducts;

//    public ProductSelectionAdapter(Context context, List<SubProducts> subProductsList,SubProListener subProListener, int quantity, int multiple_selection) {
//        this.context = context;
//        this.subProductsList = subProductsList;
//        this.subProListener = subProListener;
//        st = new StaticClass(context);
//        this.quantity = quantity;
//        this.multiple_selection = multiple_selection;
//    }

    int forwardBackward;

    public ProductSelectionAdapter(Context context, SubProducts subProducts,SubProListener subProListener, int forwardBackward) {
        this.context = context;
        this.subProducts = subProducts;
        this.subProductsList = subProducts.getSub_products();
        this.subProListener = subProListener;
        st = new StaticClass(context);
        this.quantity = subProducts.getQuantity();
        this.multiple_selection = subProducts.getIs_multiple();
        this.forwardBackward = forwardBackward;
    }

    @NonNull
    @Override
    public SingleProductAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.dynamic_product_selection,parent,false);
        return new SingleProductAdapterVH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleProductAdapterVH holder, final int position) {

        final SubProducts child_subProducts = subProductsList.get(position);

        if(child_subProducts.getIs_multiple()==0){
            holder.tv_product_name.setText(child_subProducts.getName());
        }
        else {holder.tv_product_name.setText(child_subProducts.getName() + " Optional");}


        if(child_subProducts.getSub_products().size()>0){
            holder.iv_product_up_down.setVisibility(View.VISIBLE);
        }
        else {
            holder.iv_product_up_down.setVisibility(View.GONE);
        }

        holder.iv_product_select.setVisibility(View.VISIBLE);

        holder.dynamic_product_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selected = position;

                if(child_subProducts.getSub_products().size()>0){
                    subProListener.normalSelection(subProducts,position);
                    child_subProducts.setSelected(true);
                }

                else if(child_subProducts.getSub_products().size()==0){
                    subProListener.finalSelect(subProducts,position);
                    child_subProducts.setSelected(true);
                }

            }
        });

//        if(subProducts.getQuantity()>subProducts.getQuantity_check() && forwardBackward==1){
//
//            holder.iv_product_select.setImageResource(R.drawable.radio_unselected);
//
//        }
//
//        else if(subProducts.getQuantity()>subProducts.getQuantity_check() && forwardBackward==1){
//            holder.iv_product_select.setImageResource(R.drawable.radio_unselected);
//        }
//
//        else if(subProducts.getQuantity()==subProducts.getQuantity_check()){
//            holder.iv_product_select.setImageResource(R.drawable.radio_selected);
//        }

        if (subProducts.getIs_multiple() == 0) {
            if (child_subProducts.isSelected() && forwardBackward == 0) {
                holder.iv_product_select.setImageResource(R.drawable.radio_selected);
            } else {
                holder.iv_product_select.setImageResource(R.drawable.radio_unselected);
            }
        } else {
            if (child_subProducts.isSelected() && forwardBackward == 0) {
                holder.iv_product_select.setImageResource(R.drawable.red_tick);
            } else {
                holder.iv_product_select.setImageResource(R.drawable.gray_tick);
            }
        }

    }


    public SubProducts getSubProducts(int i){ try{return subProductsList.get(selected);} catch (IndexOutOfBoundsException e){ return null;}}

    @Override
    public int getItemCount() {
        return subProductsList.size();
    }

    class  SingleProductAdapterVH extends RecyclerView.ViewHolder{

        TextView tv_product_name;
        ImageView iv_product_up_down,iv_product_select;
        LinearLayout dynamic_product_parent;

        public SingleProductAdapterVH(@NonNull View itemView) {
            super(itemView);
            tv_product_name = itemView.findViewById(R.id.tv_product_name);
            iv_product_up_down = itemView.findViewById(R.id.iv_product_up_down);
            dynamic_product_parent = itemView.findViewById(R.id.dynamic_product_parent);
            iv_product_select = itemView.findViewById(R.id.iv_product_select);
        }
    }

}