package com.example.test01;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.test01.Interface.ItemClickListener;
import com.example.test01.Model.OrderFood;
import com.example.test01.ViewHolder.OrderFoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OrderFoodList extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    public Button btn;
    FirebaseDatabase database;
    DatabaseReference confirm;
    String key;
    AppCompatSpinner spinner;
    FirebaseRecyclerAdapter<OrderFood, OrderFoodViewHolder> adapter;
    TextView Name,Phone,description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_food_list);
        Intent intent = getIntent();
        key = intent.getStringExtra("localKey");
        // Toast.makeText(this,key,Toast.LENGTH_SHORT).show();
        FirebaseApp.initializeApp(OrderFoodList.this);
        database = FirebaseDatabase.getInstance();
        confirm = database.getReference("confirm");

        recyclerView = (RecyclerView)findViewById(R.id.listFood);
        Name = (TextView)findViewById(R.id.customerName);
        Phone = (TextView)findViewById(R.id.customerPhone);
        description = (TextView)findViewById(R.id.customerdescription);
        confirm.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Name.setText("顧客名 : "+dataSnapshot.child(key).child("name").getValue().toString());
                Phone.setText("顧客電話 : "+dataSnapshot.child(key).child("phone").getValue().toString());
                description.setText("備註 : "+dataSnapshot.child(key).child("description").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrder();
    }

    private void loadOrder() {
        adapter = new FirebaseRecyclerAdapter<OrderFood, OrderFoodViewHolder>(
                OrderFood.class,
                R.layout.foodorder_layout,
                OrderFoodViewHolder.class,
                confirm.child(key).child("food")
        ) {
            @Override
            protected void populateViewHolder(OrderFoodViewHolder viewHolder, OrderFood model, int position) {

                viewHolder.textFoodName.setText("食物:"+model.getFoodName());
                viewHolder.textFoodquantity.setText("數量:"+model.getQuantity());
                viewHolder.textFoodPrice.setText("價格:"+model.getPrice());

                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        // Toast.makeText(ConfirmActivity.this,"第幾筆訂單"+position,Toast.LENGTH_SHORT).show();

                        String key = adapter.getRef(position).getKey();
                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
}
