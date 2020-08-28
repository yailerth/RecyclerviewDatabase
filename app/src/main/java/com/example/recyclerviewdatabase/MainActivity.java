package com.example.recyclerviewdatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SubTotalListener{

    private SqliteDatabase mDatabase;
    TextView txtVtotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtVtotal = (TextView)findViewById(R.id.txtVtotal);

        final RecyclerView contactView = findViewById(R.id.myContactList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        contactView.setLayoutManager(linearLayoutManager);
        contactView.setHasFixedSize(true);
        mDatabase = new SqliteDatabase(this);
        final ArrayList<Contacts> allContacts = mDatabase.listContacts();
        if (allContacts.size() > 0) {
            contactView.setVisibility(View.VISIBLE);
            ContactAdapter mAdapter = new ContactAdapter(this, allContacts,this);
            contactView.setAdapter(mAdapter);
        }
        else {
            contactView.setVisibility(View.GONE);
            Toast.makeText(this, "Ningún protocolo registrado en la base de datos", Toast.LENGTH_SHORT).show();
        }
        FloatingActionButton fbtnAdd = findViewById(R.id.fbtnAdd);
        fbtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTaskDialog();
            }
        });

    }


    private void addTaskDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.add_contacts, null);
        final EditText nameField = subView.findViewById(R.id.enterName);
        final EditText noField = subView.findViewById(R.id.enterPhoneNum);
        final EditText cantidadField = subView.findViewById(R.id.enterCantidad);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Agregar producto");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String name = nameField.getText().toString();
                final String ph_no = noField.getText().toString();
                final String cantidad = cantidadField.getText().toString();

                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(ph_no) || TextUtils.isEmpty(cantidad)) {
                    Toast.makeText(MainActivity.this, "Datos incompletos, inténtalo de nuevo!!!", Toast.LENGTH_LONG).show();
                }
                else {
                    Contacts newContact = new Contacts(name, ph_no, cantidad);
                    mDatabase.addContacts(newContact);
                    Toast.makeText(MainActivity.this, "Cantidad: "+ cantidad+"\nNombre:  " +name+"\nValor: " + ph_no, Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(getIntent());
                }
            }
        });
        builder.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Cancelado!!!", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }
    
    


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    @Override
    public void onSubTotalUpdate(int total) {

        NumberFormat format = NumberFormat.getCurrencyInstance();
        //((TextView)findViewById(R.id.txtVtotal)).setText(format.format(total));
        /*NumberFormat formatter = new DecimalFormat("###,###,###,###");
        double myNumber = total;
        //String str = formatter.format(myNumber);
        String str = formatter.getCurrencyInstance(new Locale("en","US")).format(myNumber);
        //String str = NumberFormat.getCurrencyInstance(Locale.US).format(myNumber);*/
        txtVtotal.setText(format.format(total));
    }

    //probando desde casa
    //prueba2 desde casa
    //prueba 3 desde casa
    //prueba 4 desde casa

    //prueba 5 desde trabajo
}