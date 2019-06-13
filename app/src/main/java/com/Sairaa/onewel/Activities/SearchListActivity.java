package com.Sairaa.onewel.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import com.Sairaa.onewel.Adapters.SearchListAdapter;
import com.Sairaa.onewel.R;

public class SearchListActivity extends AppCompatActivity {

    private String address,lat,lon,item;
    private AutoCompleteTextView search_act_Location;
    private EditText search_act_enter_items;
    private TextView search_act_buttonGo;
    private RecyclerView searchActivity_recyclerView;

    private SearchListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);

        address=getIntent().getExtras().getString("address");
        lat=getIntent().getExtras().getString("lat");
        lon=getIntent().getExtras().getString("lon");
        item=getIntent().getExtras().getString("item");

        search_act_Location=findViewById(R.id.search_act_Location);
        search_act_enter_items=findViewById(R.id.search_act_enter_items);
        search_act_buttonGo=findViewById(R.id.search_act_buttonGo);
        searchActivity_recyclerView=findViewById(R.id.searchActivity_recyclerView);

        search_act_enter_items.setText(item);
        search_act_Location.setText(address);

        adapter=new SearchListAdapter(this);
        searchActivity_recyclerView.setHasFixedSize(true);
        searchActivity_recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        searchActivity_recyclerView.setAdapter(adapter);



        search_act_buttonGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
