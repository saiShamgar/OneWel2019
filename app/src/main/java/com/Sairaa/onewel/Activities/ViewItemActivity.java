package com.Sairaa.onewel.Activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.Sairaa.onewel.Model.Advertisement.AdvertisementDetails;
import com.Sairaa.onewel.R;
import com.Sairaa.onewel.Utils.AppUtils;
import com.bumptech.glide.Glide;

public class ViewItemActivity extends AppCompatActivity {
    private AdvertisementDetails details;

    private ImageView view_item_shop_image;
    private TextView view_item_shop_name,view_item_shop_desc,view_item_shop_timings,view_item_shop_distance_in_meters,
            view_item_shop_address;
    private TextView item_report;
    private TextView item_call,view_item_discount;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        context=ViewItemActivity.this;
        details=(AdvertisementDetails) getIntent().getSerializableExtra("viewDetails");

        view_item_shop_image=findViewById(R.id.view_item_shop_image);
        view_item_shop_name=findViewById(R.id.view_item_shop_name);
        view_item_shop_desc=findViewById(R.id.view_item_shop_desc);
        view_item_shop_timings=findViewById(R.id.view_item_shop_timings);
        view_item_shop_address=findViewById(R.id.view_item_shop_address);
        item_report=findViewById(R.id.item_report);
        item_call=findViewById(R.id.item_call);
        view_item_discount=findViewById(R.id.view_item_discount);

        if (details!=null){
            CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(this);
            circularProgressDrawable.setStrokeWidth(5f);
            circularProgressDrawable.setCenterRadius(30f);
            circularProgressDrawable.start();
            Glide.with(this)
                    .load(details.getImage_url())
                    .placeholder(circularProgressDrawable)
                    .error(R.drawable.ic_gallery)
                    .into(view_item_shop_image);

            view_item_shop_name.setText(details.getShop_name());
            view_item_shop_desc.setText(details.getShop_desc());
            view_item_shop_timings.setText("Opens\n"+details.getOpens_from()+" to "+details.getCloses_to());
            view_item_shop_address.setText(details.getAddress());
            view_item_discount.setText("  UP TO "+details.getDiscount());

        }

        item_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (details!=null){
                    Intent otpActivity=new Intent(ViewItemActivity.this,OtpActivity.class);
                    otpActivity.putExtra("status","BlockUser");
                    otpActivity.putExtra("number",details.getPhone_num());
                    startActivity(otpActivity);
                }
            }
        });

        item_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (details!=null){
                    Intent callIntent=new Intent();
                    callIntent.setAction(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+details.getPhone_num()));
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
                        AppUtils.showToast(context,"permissions not granted");
                        return;
                    }
                    startActivity(callIntent);
                }
            }
        });




    }
}
