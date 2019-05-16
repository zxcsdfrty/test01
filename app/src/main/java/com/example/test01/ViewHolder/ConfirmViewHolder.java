package com.example.test01.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import com.example.test01.Interface.ItemClickListener;
import com.example.test01.R;

public class ConfirmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener ,View.OnCreateContextMenuListener{
    public TextView textOrderid,textOrderName,textOrderStatus,textOrderphone,textOrderPrice;

    private ItemClickListener itemClickListener;

    public ConfirmViewHolder(@NonNull View itemView) {
        super(itemView);

        textOrderid = (TextView)itemView.findViewById(R.id.order_id);
        textOrderName = (TextView)itemView.findViewById(R.id.order_name);
        textOrderStatus = (TextView)itemView.findViewById(R.id.order_status);
        textOrderphone = (TextView)itemView.findViewById(R.id.order_phone);
        textOrderPrice = (TextView)itemView.findViewById(R.id.order_price);
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
        menu.setHeaderTitle("完成訂單?");

        menu.add(0,0,getAdapterPosition(),"已完成");
    }
}