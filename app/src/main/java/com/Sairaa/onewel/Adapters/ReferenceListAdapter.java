package com.Sairaa.onewel.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.Sairaa.onewel.Model.Advertisement.ADDVERTISER_PAYING_DETAILS;
import com.Sairaa.onewel.Model.Customer.CUSTOMER_PAYING_DETAILS;
import com.Sairaa.onewel.R;

import java.util.ArrayList;

public class ReferenceListAdapter extends RecyclerView.Adapter<ReferenceListAdapter.viewHolder> {

    private Context context;
    private ArrayList<ADDVERTISER_PAYING_DETAILS> details;
    private int i;


    public ReferenceListAdapter(Context context, ArrayList<ADDVERTISER_PAYING_DETAILS> details) {
        this.context = context;
        this.details = details;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cus_reference_layout,viewGroup,false);

       return  new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int i) {

        holder.ref_description.setText(details.get(i).getDescription());
        holder.ref_number.setText("PH NO\n"+details.get(i).getCuster_number());
        holder.ref_discountPrice.setText("Discount price\n"+details.get(i).getDiscount_price());
        holder.ref_totalPrice.setText("Total Price\n"+details.get(i).getTotal_price());

    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        private TextView ref_description,ref_number,ref_totalPrice,ref_discountPrice;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            ref_description=itemView.findViewById(R.id.ref_description);
            ref_number=itemView.findViewById(R.id.ref_number);
            ref_totalPrice=itemView.findViewById(R.id.ref_totalPrice);
            ref_discountPrice=itemView.findViewById(R.id.ref_discountPrice);
        }
    }
}
