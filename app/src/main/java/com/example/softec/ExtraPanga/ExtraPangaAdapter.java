package com.example.softec.ExtraPanga;

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
import com.example.softec.MenuModel.MenuExtraAddings;
import com.example.softec.MenuModel.MenuReplaceOf;
import com.example.softec.R;
import com.example.softec.StaticClasses.StaticClass;
import java.util.List;

public class ExtraPangaAdapter extends RecyclerView.Adapter<ExtraPangaAdapter.ExtraPangaVH> {

    List<MenuExtraAddings> list;
    ExtraAddingsCallBack extraAddingsCallBack;
    Context context;
    StaticClass st;
    int last;

    public ExtraPangaAdapter(List<MenuExtraAddings> list, ExtraAddingsCallBack extraAddingsCallBack, Context context, int last) {
        this.list = list;
        this.extraAddingsCallBack = extraAddingsCallBack;
        this.context = context;
        this.last = last;
        st = new StaticClass(context);
    }

    @NonNull
    @Override
    public ExtraPangaVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ExtraPangaVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.extra_addings_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ExtraPangaVH holder, int position) {

        final MenuExtraAddings extraAddings = list.get(position);
        holder.extra_adding_text.setText(extraAddings.getName());

        holder.rv_extra_adding_parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(last==0){
                    extraAddingsCallBack.addExtraAdding(extraAddings, !extraAddings.isSelected());
                    notifyDataSetChanged();
                }

                else {
                    extraAddings.setSelected(!extraAddings.isSelected());
                    if(extraAddings.getReplaceOf().getReplace_of()==-1) {
                        extraAddingsCallBack.multipleExtraAddings(extraAddings, extraAddings.isSelected(), !extraAddings.isSelected());
                    }
                    else {
                        extraAddingsCallBack.replaceExtraAddings(extraAddings, extraAddings.isSelected(), !extraAddings.isSelected());
                    }
                    notifyDataSetChanged();
                }

            }
        });

        MenuReplaceOf menuReplaceOf = extraAddings.getReplaceOf();

        double price = Double.parseDouble(extraAddings.getPrice().length()==0?"0.0":extraAddings.getPrice());

        if(last==1) {
            holder.iv_extra_adding.setImageDrawable(context.getResources().getDrawable(extraAddings.isSelected() ? R.drawable.black_rect_check_box : R.drawable.ic_baseline_uncheck_box));
        }

        else {
            holder.iv_extra_adding.setVisibility(View.GONE);
        }

        if (last==1 && menuReplaceOf.getReplace_of() != -1) {
            holder.tv_replace_of.setVisibility(View.VISIBLE);
            holder.tv_replace_of.setText(st.htmlText("In Place Of <b><font color='#00E676'>" + menuReplaceOf.getName() + "</font></b>"));
        } else {
            holder.tv_replace_of.setVisibility(View.GONE);
        }

        holder.tv_extra_adding_price.setText(price>0?"$"+price:"Free");
        holder.tv_extra_adding_price.setTextColor(context.getResources().getColor(price>0?R.color.red_color:R.color.green_color));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ExtraPangaVH extends RecyclerView.ViewHolder {

        TextView extra_adding_text, tv_replace_of, tv_extra_adding_price;
        RelativeLayout rv_extra_adding_parent;
        ImageView iv_extra_adding;

        public ExtraPangaVH(@NonNull View itemView) {
            super(itemView);
            rv_extra_adding_parent = itemView.findViewById(R.id.rv_extra_adding_parent);
            tv_replace_of = itemView.findViewById(R.id.tv_replace_of);
            tv_extra_adding_price = itemView.findViewById(R.id.tv_extra_adding_price);
            extra_adding_text = itemView.findViewById(R.id.extra_adding_text);
            iv_extra_adding = itemView.findViewById(R.id.iv_extra_adding);
        }
    }
}