package com.example.test01;

import android.content.Intent;
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
import com.example.test01.Model.Request;
import com.example.test01.ViewHolder.ConfirmViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfirmActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    public Button btn;
    FirebaseDatabase database;
    DatabaseReference confirm;

    AppCompatSpinner spinner;
    FirebaseRecyclerAdapter<Request, ConfirmViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);

        FirebaseApp.initializeApp(ConfirmActivity.this);
        database = FirebaseDatabase.getInstance();
        confirm = database.getReference("confirm");

        recyclerView = (RecyclerView)findViewById(R.id.listConfirm);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrder();
    }

    private void loadOrder() {
        adapter = new FirebaseRecyclerAdapter<Request, ConfirmViewHolder>(
                Request.class,
                R.layout.order_layout,
                ConfirmViewHolder.class,
                confirm
        ) {
            @Override
            protected void populateViewHolder(ConfirmViewHolder viewHolder, Request model, int position) {

                viewHolder.textOrderid.setText("訂單編號:" + adapter.getRef(position).getKey());
                viewHolder.textOrderName.setText("顧客:" + model.getName());
                viewHolder.textOrderphone.setText("顧客電話:" + model.getphone());
                viewHolder.textOrderPrice.setText("總金額:" + model.getPrice());
                viewHolder.textOrderStatus.setText("訂單狀態:" + Common.convertCodeToStatus(model.getStatus()));

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        // Toast.makeText(ConfirmActivity.this,"第幾筆訂單"+position,Toast.LENGTH_SHORT).show();

                        String key = adapter.getRef(position).getKey();
                        ClickItem(key);
                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
    public void ClickItem(String key)
    {
        Intent intent = new Intent(this,OrderFoodList.class);
        intent.putExtra("localKey",key);
        startActivity(intent);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals("已完成"))
            OrderFinish(adapter.getRef(item.getOrder()).getKey(),adapter.getItem(item.getOrder()));
        return super.onContextItemSelected(item);
    }

    private void OrderFinish(String key, Request item) {
        confirm.child(key).child("status").setValue("2");
        item.setStatus("2");
    }
}
