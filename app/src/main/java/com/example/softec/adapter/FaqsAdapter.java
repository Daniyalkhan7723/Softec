package com.example.softec.adapter;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;
        import com.example.softec.R;

public class FaqsAdapter extends RecyclerView.Adapter<FaqsAdapter.FaqsClass> {

    Context context;

    public FaqsAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public FaqsClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.faqs_single_row,parent,false);
        return new FaqsClass(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final FaqsClass holder, int position) {
        if(position==0){
            holder.arrowDirection.setImageResource(R.drawable.arrow_down);
            holder.arrowDirection.setTag("arrowDown");
            holder.answer.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.arrowDirection.getTag().equals("arrowDown")){
                    holder.arrowDirection.setImageResource(R.drawable.arrow_down);
                    holder.arrowDirection.setTag("arrowUp");
                    holder.answer.setVisibility(View.GONE);
                }
                else{
                    holder.arrowDirection.setImageResource(R.drawable.arrow_up);
                    holder.arrowDirection.setTag("arrowDown");
                    holder.answer.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class FaqsClass extends RecyclerView.ViewHolder{
        TextView question,answer;
        ImageView arrowDirection;
        public FaqsClass(@NonNull View itemView) {
            super(itemView);
            question=itemView.findViewById(R.id.tv_question);
            answer=itemView.findViewById(R.id.tv_answer);
            arrowDirection=itemView.findViewById(R.id.arrow_direction);
        }
    }
}
