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
import com.example.softec.MenuModel.MenuExtraAddingDeal1;
import com.example.softec.MenuModel.MenuExtraAddings;
import com.example.softec.MenuModel.MenuReplaceOf;
import com.example.softec.R;
import com.example.softec.StaticClasses.SharedPreference;
import com.example.softec.StaticClasses.StaticClass;

import java.util.List;

public class ExtraAddingAdapter extends RecyclerView.Adapter<ExtraAddingAdapter.ExtraAddingAdapterVH> {

    Context context;
    ExtraAddingsCallBack extraAddingsCallBack;
    List<MenuExtraAddings> menuExtraAddingsList;
    StaticClass st;
    int serverCounter;

    SharedPreference category_id_and_counters;
    SharedPreference category_id_and_counters_selection;

    int myCounter;

    public ExtraAddingAdapter(Context context, List<MenuExtraAddings> extraAddingList, ExtraAddingsCallBack extraAddingsCallBack, int serverCounter,int myCounter) {
        this.context = context;
        this.menuExtraAddingsList = extraAddingList;
        this.extraAddingsCallBack = extraAddingsCallBack;
        this.serverCounter = serverCounter;
        st = new StaticClass(context);
        this.myCounter = myCounter;
        category_id_and_counters = new SharedPreference(context,"category_id_and_counters");
        category_id_and_counters_selection = new SharedPreference(context,"category_id_and_counters_selection");
    }

    @NonNull
    @Override
    public ExtraAddingAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExtraAddingAdapterVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.extra_addings_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExtraAddingAdapterVH holder, final int position) {


        final MenuExtraAddings menuExtraAddings = menuExtraAddingsList.get(position);
        MenuReplaceOf menuReplaceOf = menuExtraAddings.getReplaceOf();
        holder.extra_adding_text.setText(menuExtraAddings.getName());

        double price = Double.parseDouble(menuExtraAddings.getPrice().length()==0?"0.0":menuExtraAddings.getPrice());

        holder.iv_extra_adding.setImageDrawable(context.getResources().getDrawable(menuExtraAddings.isSelected() ? R.drawable.black_rect_check_box : R.drawable.ic_baseline_uncheck_box));

        if (menuReplaceOf.getReplace_of() != -1) {
            holder.tv_replace_of.setVisibility(View.VISIBLE);
            holder.tv_replace_of.setText(st.htmlText("In Place Of <b><font color='#00E676'>" + menuReplaceOf.getName() + "</font></b>"));
        } else {
            holder.tv_replace_of.setVisibility(View.GONE);
        }

        holder.tv_extra_adding_price.setText(String.valueOf(price));
//        holder.tv_extra_adding_price.setTextColor(price.equals("Free") ? context.getResources().getColor(R.color.green_color) : context.getResources().getColor(R.color.black));

        holder.rv_extra_adding_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(menuExtraAddings.getCategory_id()!=-1) {

                    int counterr1 = category_id_and_counters.getIntValue(menuExtraAddings.getCategory_id() + "");
                    int counterr2 = category_id_and_counters_selection.getIntValue(menuExtraAddings.getCategory_id() + "");

                    st.toast("menu extra adding cat id: " + menuExtraAddings.getCategory_id() + ". c1: " + counterr1 + ", c2: " + counterr2);

                    if (counterr1 == counterr2) {
                        st.toast("You cannot select more than " + serverCounter);
                        return;
                    }
                    counterr2 = counterr2+1;
                    category_id_and_counters_selection.saveIntValue(menuExtraAddings.getCategory_id()+"",counterr2);
                }

                menuExtraAddings.setSelected(!menuExtraAddings.isSelected());
                extraAddingsCallBack.addExtraAdding(menuExtraAddings, !menuExtraAddings.isSelected());
                notifyDataSetChanged();

            }
        });

    }

    @Override
    public int getItemCount() {
        return menuExtraAddingsList.size();
    }

    class ExtraAddingAdapterVH extends RecyclerView.ViewHolder {

        TextView extra_adding_text, tv_replace_of, tv_extra_adding_price;
        RelativeLayout rv_extra_adding_parent;
        ImageView iv_extra_adding;

        public ExtraAddingAdapterVH(@NonNull View itemView) {
            super(itemView);
            rv_extra_adding_parent = itemView.findViewById(R.id.rv_extra_adding_parent);
            tv_replace_of = itemView.findViewById(R.id.tv_replace_of);
            tv_extra_adding_price = itemView.findViewById(R.id.tv_extra_adding_price);
            extra_adding_text = itemView.findViewById(R.id.extra_adding_text);
            iv_extra_adding = itemView.findViewById(R.id.iv_extra_adding);
        }
    }

}
