package com.Sairaa.onewel.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.Sairaa.onewel.R;
import com.Sairaa.onewel.Utils.AppUtils;
import com.Sairaa.onewel.Utils.Contants;
import com.google.firebase.database.*;

public class ReferenceList extends AppCompatActivity {
    private String number,status;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reference_list);

        context=ReferenceList.this;
        number=getIntent().getExtras().getString("number");
        status=getIntent().getExtras().getString("status");
        if (status.contains("refPromoter")){
            getPromoterData();
        }
    }

    private void getPromoterData() {
        Query query= FirebaseDatabase.getInstance().getReference().child(Contants.REFERENCES);

        ValueEventListener valueEventListener=new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    if (dataSnapshot.hasChild(number)){
                        for (DataSnapshot list:dataSnapshot.child(number).getChildren()){
                            // Log.e("keys",list.getKey());
                            String key=list.getKey();
//                            for (DataSnapshot dataSnapshot1:list.getChildren()){
                            String child=list.child("REFERRED").getValue().toString();
                            Log.e("child",child);
//                            }
                        }
                    }
                    else {
                        AppUtils.showToast(context,"No References found");
                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        query.addValueEventListener(valueEventListener);
    }
}
