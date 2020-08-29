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

import java.text.NumberFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SubTotalListener{

    private SqliteDatabase mDatabase;
    TextView txtSubtotal,txtIva,txtTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtSubtotal = (TextView)findViewById(R.id.txtSubtotal);
        txtIva = (TextView)findViewById(R.id.txtIva);
        txtTotal = (TextView)findViewById(R.id.txtTotal);

        final RecyclerView replacementView = findViewById(R.id.recyclerReplacementList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        replacementView.setLayoutManager(linearLayoutManager);
        replacementView.setHasFixedSize(true);
        mDatabase = new SqliteDatabase(this);
        final ArrayList<Repuestos> allReplacement = mDatabase.listReplacement();
        if (allReplacement.size() > 0) {
            replacementView.setVisibility(View.VISIBLE);
            QuotationAdapter mAdapter = new QuotationAdapter(this, allReplacement,this);
            replacementView.setAdapter(mAdapter);
        }
        else {
            replacementView.setVisibility(View.GONE);
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
        View subView = inflater.inflate(R.layout.dialog_add_edit_replace, null);
        final EditText nameProdField = subView.findViewById(R.id.enterName);
        final EditText valorProdField = subView.findViewById(R.id.enterPhoneNum);
        final EditText cantidadField = subView.findViewById(R.id.enterCantidad);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Agregar producto");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String nameProd = nameProdField.getText().toString();
                final String valorProd = valorProdField.getText().toString();
                final String cantidadProd = cantidadField.getText().toString();

                if (TextUtils.isEmpty(nameProd) || TextUtils.isEmpty(valorProd) || TextUtils.isEmpty(cantidadProd)) {
                    Toast.makeText(MainActivity.this, "Datos incompletos, inténtalo de nuevo!!!", Toast.LENGTH_LONG).show();
                }
                else {
                    Repuestos newReplacement = new Repuestos(nameProd, valorProd, cantidadProd);
                    mDatabase.addQuotaReplacement(newReplacement);
                    //Toast.makeText(MainActivity.this, "Cantidad: "+ cantidad+"\nNombre:  " +name+"\nValor: " + ph_no, Toast.LENGTH_LONG).show();
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
        txtSubtotal.setText(format.format(total));

        double valorIva = total*0.19;
        txtIva.setText(format.format(valorIva));

        double valorTotal = total+valorIva;
        txtTotal.setText(format.format(valorTotal));
    }
}

  /*((TextView)findViewById(R.id.txtVtotal)).setText(format.format(total));
        NumberFormat formatter = new DecimalFormat("###,###,###,###");
        double myNumber = total;
        //String str = formatter.format(myNumber);
        String str = formatter.getCurrencyInstance(new Locale("en","US")).format(myNumber);
        String str = NumberFormat.getCurrencyInstance(Locale.US).format(myNumber);*/