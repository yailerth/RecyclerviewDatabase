package com.example.recyclerviewdatabase;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
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

public class ContactAdapter extends RecyclerView.Adapter<ContactViewHolder> implements Filterable {


    private Context context;
    private ArrayList<Contacts> listContacts;
    private ArrayList<Contacts> mArrayList;
    private SqliteDatabase mDatabase;
    float sum=0;
    private SubTotalListener subTotalListener;
    //private View.OnClickListener listener;

    public ContactAdapter(Context context, ArrayList<Contacts> listContacts,SubTotalListener subTotalListener) {
        this.context = context;
        this.listContacts = listContacts;
        this.mArrayList = listContacts;
        mDatabase = new SqliteDatabase(context);
        this.subTotalListener = subTotalListener;
    }


    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_layout, parent, false);
        //view.setOnClickListener(this);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        final Contacts contacts = listContacts.get(position);
        holder.tvName.setText(contacts.getName());
        holder.tvPhoneNum.setText("$ " + contacts.getPhno());
        holder.tvCantProd.setText(contacts.getCantidadProd());


        int total = 0;
        for(int i = 0; i < listContacts.size(); i++){
            total = total + Integer.parseInt(listContacts.get(i).getPhno());

        }
        countTotal(total);

        /*Intent intent = new Intent("message_subject_intent");
        intent.putExtra("vTotal",total);*/
        //Toast.makeText(context, "Total: "+total,Toast.LENGTH_LONG).show();

        holder.editContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTaskDialog(contacts);
            }
        });

        holder.deleteContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.deleteContact(contacts.getId());
                ((Activity) context).finish();
                context.startActivity(((Activity) context).getIntent());
            }
        });

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Toast.makeText(context,"Click en "+listContacts.get(position).getName(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, ReporteSysmed.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("name",listContacts.get(position).getPhno());
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
                    listContacts = mArrayList;
                }
                else {
                    ArrayList<Contacts> filteredList = new ArrayList<>();
                    for (Contacts contacts : mArrayList) {
                        if (contacts.getName().toLowerCase().contains(charString)) {
                            filteredList.add(contacts);
                        }
                    }
                    listContacts = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listContacts;
                return filterResults;
            }
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listContacts = (ArrayList<Contacts>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    @Override
    public int getItemCount() {
        int count = listContacts.size();
        return count;
        /*if (count>=4){
            Toast.makeText(context, "Items: "+count,Toast.LENGTH_SHORT).show();
            return count;
        }else
            return 5;*/
    }

    private void editTaskDialog(final Contacts contacts) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.add_contacts, null);
        final EditText nameField = subView.findViewById(R.id.enterName);
        final EditText contactField = subView.findViewById(R.id.enterPhoneNum);
        final EditText cantProdField = subView.findViewById(R.id.enterCantidad);
        if (contacts != null) {
            nameField.setText(contacts.getName());
            contactField.setText(String.valueOf(contacts.getPhno()));
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Editar protocolo");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final String ph_no = contactField.getText().toString();
                final String cantidad = cantProdField.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(context, "Por favor llene la casilla equipo!!!", Toast.LENGTH_LONG).show();
                } else {
                    mDatabase.updateContacts(new
                            Contacts(Objects.requireNonNull(contacts).getId(), name, ph_no,cantidad));
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
