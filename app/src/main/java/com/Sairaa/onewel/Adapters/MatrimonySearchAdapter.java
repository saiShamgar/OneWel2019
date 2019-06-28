package com.Sairaa.onewel.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.method.CharacterPickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.Sairaa.onewel.Activities.Matrimony.ViewmatrimonyImage;
import com.Sairaa.onewel.Model.MatrimonyInsertionData;
import com.Sairaa.onewel.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MatrimonySearchAdapter extends RecyclerView.Adapter<MatrimonySearchAdapter.ViewHolder> {

    private Context context;
    private ArrayList<MatrimonyInsertionData> list;

    public MatrimonySearchAdapter(Context context, ArrayList<MatrimonyInsertionData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.matrimony_search_items_layout,viewGroup,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {
        holder.mat_search_name.setText(list.get(i).getName());
        holder.mat_search_age.setText(list.get(i).getAge()+","+list.get(i).getStudy()+","+list.get(i).getReligion());
        holder.mat_search_profession.setText(list.get(i).getProfession());
        holder.mat_search_location.setText("#"+list.get(i).getCity()+","+list.get(i).getDistrict()+","+
                list.get(i).getState()+","+list.get(i).getCountry());

        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(context);
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        Glide.with(context)
                .load(list.get(i).getImage())
                .placeholder(circularProgressDrawable)
                .error(R.drawable.ic_gallery)
                .into(holder.mat_search_image);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewImage=new Intent(context, ViewmatrimonyImage.class);
                viewImage.putExtra("details",list.get(i));
                context.startActivity(viewImage);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mat_search_name,mat_search_age,mat_search_profession,mat_search_location;
        private ImageView mat_search_image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mat_search_name=itemView.findViewById(R.id.mat_search_name);
            mat_search_age=itemView.findViewById(R.id.mat_search_age);
            mat_search_profession=itemView.findViewById(R.id.mat_search_profession);
            mat_search_location=itemView.findViewById(R.id.mat_search_location);
            mat_search_image=itemView.findViewById(R.id.mat_search_image);
        }
    }
}
