package com.example.test01;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.test01.Common.Common;
import com.example.test01.Interface.ItemClickListener;
import com.example.test01.Model.OrderFood;
import com.example.test01.Model.Request;
import com.example.test01.ViewHolder.OrderViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Unconfirm extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference requests;

    AppCompatSpinner spinner;
    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unconfirm);

        FirebaseApp.initializeApp(Unconfirm.this);
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Request");

        recyclerView = (RecyclerView)findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrder();
    }

    private void loadOrder() {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,
                requests
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {

                viewHolder.textOrderid.setText("訂單編號:" + adapter.getRef(position).getKey());
                viewHolder.textOrderName.setText("顧客:" + model.getName());
                viewHolder.textOrderphone.setText("顧客電話:" + model.getphone());
                viewHolder.textOrderPrice.setText("總金額:" + model.getPrice());
                viewHolder.textOrderStatus.setText("訂單狀態:" + Common.convertCodeToStatus(model.getStatus()));

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Toast.makeText(MainActivity.this,"有近來2",Toast.LENGTH_SHORT).show();
                        // ClickItem(position);
                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
    /* public void ClickItem(int position)
     {
         final AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
         alert.setTitle("更新訂單");
         alert.setMessage("是否接受訂單");
         alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 //ShowUpdateDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
             }
         });
        alert.setItems("yes", new Onclic() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
         alert.show();
     }*/
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals("接受"))
            ShowUpdateDialog(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
        else if(item.getTitle().equals("拒絕"))
            deleteOrder(adapter.getRef(item.getOrder()).getKey());
        return super.onContextItemSelected(item);
    }

    private void ShowUpdateDialog(String key, Request item) {

        final String localkey = key;

        requests.child(localkey).child("status").setValue("1");
        item.setStatus("1");
        final DatabaseReference request;
        request = database.getReference("confirm");
        request.child(localkey).setValue(item);
        requests.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds: dataSnapshot.child(localkey).child("food").getChildren())
                {
                    request.child(localkey).child("food").push().setValue(ds.getValue(OrderFood.class));
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        requests.child(localkey).removeValue();
    }

    private void deleteOrder(String key) {
        final String localkey = key;
        final AlertDialog.Builder alert = new AlertDialog.Builder(Unconfirm.this);
        alert.setTitle("拒絕訂單");
        alert.setMessage("無法再次回復訂單!");
        alert.setPositiveButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.setNegativeButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requests.child(localkey).removeValue();
            }
        });
        alert.show();
    }
}
