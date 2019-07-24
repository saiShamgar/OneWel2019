package com.Sairaa.onewel.Adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.Sairaa.onewel.Activities.ViewItemActivity;
import com.Sairaa.onewel.Model.Advertisement.AdvertisementDetails;
import com.Sairaa.onewel.R;
import com.Sairaa.onewel.Utils.AppUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> {

    private Context context;
    private ArrayList<AdvertisementDetails> details;



    public SearchListAdapter(Context applicationContext, ArrayList<AdvertisementDetails> details) {
        this.context=applicationContext;
        this.details=details;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_search_list_layout,viewGroup,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewItem=new Intent(context, ViewItemActivity.class);
                viewItem.putExtra("viewDetails",details.get(i));
                context.startActivity(viewItem);
            }
        });

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        Glide.with(context)
                .load(details.get(i).getImage_url())
                .placeholder(circularProgressDrawable)
                .error(R.drawable.ic_gallery)
                .into(holder.search_list_shop_image);

        holder.search_list_shop_name.setText(details.get(i).getShop_name());
        holder.search_list_shop_description.setText(details.get(i).getShop_desc());
        holder.search_list_shop_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callIntent=new Intent();
                callIntent.setAction(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:"+details.get(i).getPhone_num()));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                    AppUtils.showToast(context,"permissions not granted");
                    return;
                }
                context.startActivity(callIntent);
            }
        });





    }

    @Override
    public int getItemCount() {
        return details.size();
    }

    public class  ViewHolder extends RecyclerView.ViewHolder{
        private ImageView search_list_shop_image;
        private TextView search_list_shop_name,search_list_shop_description,search_list_shop_call;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            search_list_shop_image=itemView.findViewById(R.id.search_list_shop_image);
            search_list_shop_name=itemView.findViewById(R.id.search_list_shop_name);
            search_list_shop_description=itemView.findViewById(R.id.search_list_shop_description);
            search_list_shop_call=itemView.findViewById(R.id.search_list_shop_call);
        }
    }
}
