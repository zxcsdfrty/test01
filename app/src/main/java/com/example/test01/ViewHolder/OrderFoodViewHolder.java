package com.example.test01.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.example.test01.Interface.ItemClickListener;
import com.example.test01.R;

public class OrderFoodViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener ,View.OnCreateContextMenuListener{
    public TextView textFoodName,textFoodquantity,textFoodPrice;

    private ItemClickListener itemClickListener;

    public  OrderFoodViewHolder(@NonNull View itemView) {
        super(itemView);

        textFoodName = (TextView)itemView.findViewById(R.id.food_name);
        textFoodquantity = (TextView)itemView.findViewById(R.id.food_quantity);
        textFoodPrice = (TextView)itemView.findViewById(R.id.food_price);

        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
    }
    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("是否完成?");

        menu.add(0,0,getAdapterPosition(),"完成");
    }
}
