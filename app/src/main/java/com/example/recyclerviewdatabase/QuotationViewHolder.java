package com.example.recyclerviewdatabase;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


public class QuotationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView tvNameProd, tvValorProd, tvCantProd,tvTotal;
    ImageView deleteReplacement;
    ImageView editReplacement;
    private ItemClickListener itemClickListener;

    QuotationViewHolder(View itemView) {
        super(itemView);
        tvNameProd = itemView.findViewById(R.id.prodName);
        tvValorProd = itemView.findViewById(R.id.prodValor);
        deleteReplacement = itemView.findViewById(R.id.deleteReplacement);
        editReplacement = itemView.findViewById(R.id.editReplacement);
        tvCantProd = itemView.findViewById(R.id.txtCantidad);
        tvTotal = itemView.findViewById(R.id.txtTotal);

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