package com.example.recyclerviewdatabase;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView tvName, tvPhoneNum;
    ImageView deleteContact;
    ImageView editContact;
    private ItemClickListener itemClickListener;

    ContactViewHolder(View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.contactName);
        tvPhoneNum = itemView.findViewById(R.id.phoneNum);
        deleteContact = itemView.findViewById(R.id.deleteContact);
        editContact = itemView.findViewById(R.id.editContact);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        itemClickListener.onClick(view,getAdapterPosition());
    }
}