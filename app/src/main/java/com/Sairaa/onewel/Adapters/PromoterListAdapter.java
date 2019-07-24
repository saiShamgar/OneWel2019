package com.Sairaa.onewel.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.Sairaa.onewel.R;

import java.util.ArrayList;

public class PromoterListAdapter extends RecyclerView.Adapter<PromoterListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<String> list=new ArrayList<>();

    public PromoterListAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.promoter_cus_layout,viewGroup,false);

        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        String sl_no=String.valueOf(i+1);

        holder.cus_ref_id.setText(sl_no+". "+list.get(i));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView cus_ref_id;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cus_ref_id=itemView.findViewById(R.id.cus_ref_id);
        }
    }
}
