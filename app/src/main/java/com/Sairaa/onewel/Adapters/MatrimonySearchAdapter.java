package com.Sairaa.onewel.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.Sairaa.onewel.Model.MatrimonyInsertionData;
import com.Sairaa.onewel.R;

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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
