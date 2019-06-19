package com.Sairaa.onewel.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.Sairaa.onewel.Model.Customer.CUSTOMER_PAYING_DETAILS;
import com.Sairaa.onewel.R;

import java.util.ArrayList;

public class CustomerDetails_adapter extends RecyclerView.Adapter<CustomerDetails_adapter.ViewHOlder> {

    private Context context;
    private ArrayList<CUSTOMER_PAYING_DETAILS> details;

    public CustomerDetails_adapter(Context context, ArrayList<CUSTOMER_PAYING_DETAILS> details) {
        this.context = context;
        this.details = details;
    }

    @NonNull
    @Override
    public ViewHOlder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cus_reference_layout,viewGroup,false);

        return  new ViewHOlder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHOlder holder, int i) {
        holder.ref_description.setText(details.get(i).getDescription());
        holder.ref_number.setText("PH NO\n"+details.get(i).getName());
        holder.ref_discountPrice.setText("Discount price\n"+details.get(i).getDiscountPrice());
        holder.ref_totalPrice.setText("Total Price\n"+details.get(i).getTotalPrice());
    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public class ViewHOlder extends RecyclerView.ViewHolder{

        private TextView ref_description,ref_number,ref_totalPrice,ref_discountPrice;

        public ViewHOlder(@NonNull View itemView) {
            super(itemView);

            ref_description=itemView.findViewById(R.id.ref_description);
            ref_number=itemView.findViewById(R.id.ref_number);
            ref_totalPrice=itemView.findViewById(R.id.ref_totalPrice);
            ref_discountPrice=itemView.findViewById(R.id.ref_discountPrice);
        }
    }
}
