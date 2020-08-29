package com.example.recyclerviewdatabase;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class QuotationAdapter extends RecyclerView.Adapter<QuotationViewHolder> implements Filterable {


    private Context context;
    private ArrayList<Repuestos> listReplacement;
    private ArrayList<Repuestos> mArrayList;
    private SqliteDatabase mDatabase;
    float sum=0;
    private SubTotalListener subTotalListener;
    //private View.OnClickListener listener;

    public QuotationAdapter(Context context, ArrayList<Repuestos> listReplacement, SubTotalListener subTotalListener) {
        this.context = context;
        this.listReplacement = listReplacement;
        this.mArrayList = listReplacement;
        mDatabase = new SqliteDatabase(context);
        this.subTotalListener = subTotalListener;
    }


    @NonNull
    @Override
    public QuotationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_replac_layout, parent, false);
        //view.setOnClickListener(this);
        return new QuotationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuotationViewHolder holder, int position) {
        final Repuestos repuestos = listReplacement.get(position);
        holder.tvNameProd.setText(repuestos.getName());
        holder.tvValorProd.setText(" $ " + repuestos.getPhno());
        holder.tvCantProd.setText(repuestos.getCantidadProd());
        holder.tvTotal.setText("$ " + repuestos.getTotal());

        int total = 0;
        for(int i = 0; i < listReplacement.size(); i++){
            //total = total + Integer.parseInt(listContacts.get(i).getPhno());
            total = total + listReplacement.get(i).getTotal();
        }
        countTotal(total);

        holder.editReplacement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTaskDialog(repuestos);
            }
        });

        holder.deleteReplacement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.deleteReplacement(repuestos.getId());
                ((Activity) context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(context,"Click en "+ listReplacement.get(position).getName(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ReporteSysmed.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("name", listReplacement.get(position).getPhno());
                context.startActivity(intent);
            }
        });
    }

    public void countTotal(int total){
        // Calculate your total amount here from your arraylist and pass in below call.
        subTotalListener.onSubTotalUpdate(total);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    listReplacement = mArrayList;
                }
                else {
                    ArrayList<Repuestos> filteredList = new ArrayList<>();
                    for (Repuestos repuestos : mArrayList) {
                        if (repuestos.getName().toLowerCase().contains(charString)) {
                            filteredList.add(repuestos);
                        }
                    }
                    listReplacement = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listReplacement;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listReplacement = (ArrayList<Repuestos>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    @Override
    public int getItemCount() {
        int count = listReplacement.size();
        return count;

    }

    private void editTaskDialog(final Repuestos repuestos) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.dialog_add_edit_replace, null);
        final EditText nameField = subView.findViewById(R.id.enterName);
        final EditText contactField = subView.findViewById(R.id.enterPhoneNum);
        final EditText cantProdField = subView.findViewById(R.id.enterCantidad);
        if (repuestos != null) {
            nameField.setText(repuestos.getName());
            contactField.setText(String.valueOf(repuestos.getPhno()));
            cantProdField.setText(String.valueOf(repuestos.getCantidadProd()));
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Editar protocolo");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                String ph_no = contactField.getText().toString();
                String cantidad = cantProdField.getText().toString();

                if (cantidad.equals("")) cantidad = "0";
                if (ph_no.equals("")) ph_no = "0";

                final int a=Integer.parseInt(ph_no);
                final int b=Integer.parseInt(cantidad);
                final int totalProd = a*b;


                if (name.isEmpty() || ph_no.equals("0")|| cantidad.equals("0")) {
                    Toast.makeText(context, "  Espacios sin llenar \n\nVuelve a intentarlo!!!", Toast.LENGTH_LONG).show();
                } else {
                    mDatabase.updateQuotaReplacement(new
                            Repuestos(Objects.requireNonNull(repuestos).getId(), name, ph_no, cantidad,totalProd));
                    ((Activity) context).finish();
                    context.startActivity(((Activity)
                            context).getIntent());
                }
            }
        });
        builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Cancelado!!!",Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

    /*    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener!=null){
            listener.onClick(view);
        }
    }*/
}
