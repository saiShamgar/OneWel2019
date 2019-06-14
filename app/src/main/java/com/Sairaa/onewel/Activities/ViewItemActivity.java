package com.Sairaa.onewel.Activities;

import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.Sairaa.onewel.Model.Advertisement.AdvertisementDetails;
import com.Sairaa.onewel.R;
import com.bumptech.glide.Glide;

public class ViewItemActivity extends AppCompatActivity {
    private AdvertisementDetails details;

    private ImageView view_item_shop_image;
    private TextView view_item_shop_name,view_item_shop_desc,view_item_shop_timings,view_item_shop_distance_in_meters,
            view_item_shop_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        details=(AdvertisementDetails) getIntent().getSerializableExtra("viewDetails");

        view_item_shop_image=findViewById(R.id.view_item_shop_image);
        view_item_shop_name=findViewById(R.id.view_item_shop_name);
        view_item_shop_desc=findViewById(R.id.view_item_shop_desc);
        view_item_shop_timings=findViewById(R.id.view_item_shop_timings);
        view_item_shop_distance_in_meters=findViewById(R.id.view_item_shop_distance_in_meters);
        view_item_shop_address=findViewById(R.id.view_item_shop_address);

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

        }


    }
}
