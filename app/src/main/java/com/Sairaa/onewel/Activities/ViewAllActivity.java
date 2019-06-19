package com.Sairaa.onewel.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.Sairaa.onewel.Adapters.ViewAllAdapter;
import com.Sairaa.onewel.R;

public class ViewAllActivity extends AppCompatActivity {
    private RecyclerView view_all_recyclerView;
    private ViewAllAdapter allAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        view_all_recyclerView=findViewById(R.id.view_all_recyclerView);

        DividerItemDecoration itemDecoration=new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        allAdapter=new ViewAllAdapter(this,getResources().getStringArray(R.array.shop_arrays));
        view_all_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        view_all_recyclerView.setHasFixedSize(true);
        view_all_recyclerView.setAdapter(allAdapter);
        view_all_recyclerView.addItemDecoration(itemDecoration);
    }
}
