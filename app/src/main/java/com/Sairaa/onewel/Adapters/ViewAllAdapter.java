package com.Sairaa.onewel.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.Sairaa.onewel.Activities.Add.AddSignUpPD;
import com.Sairaa.onewel.R;

import java.util.ArrayList;

public class ViewAllAdapter extends RecyclerView.Adapter<ViewAllAdapter.ViewHolder> {

    private Context context;
    private String[] arrayList;

    public ViewAllAdapter(Context context, String[] arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cus_view_all_activity,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.txt_cus_view_all.setText(arrayList[i]);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add=new Intent(context, AddSignUpPD.class);
                add.putExtra("position",i);
                context.startActivity(add);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txt_cus_view_all;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_cus_view_all=itemView.findViewById(R.id.txt_cus_view_all);
        }
    }
}
