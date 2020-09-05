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

    String  cantidad0, producto0, subtotal0,
            cantidad1, producto1, subtotal1,
            cantidad2, producto2, subtotal2,
            cantidad3, producto3, subtotal3,
            cantidad4, producto4, subtotal4,
            cantidad5, producto5, subtotal5,
            cantidad6, producto6, subtotal6,
            cantidad7, producto7, subtotal7,
            cantidad8, producto8, subtotal8,
            cantidad9, producto9, subtotal9;

    int total0,total1,total2,total3,total4,total5,total6,total7,total8,total9;


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

            cantidad0 = mDatabase.listReplacement().get(0).getCantidadProd();
            producto0 = mDatabase.listReplacement().get(0).getName();
            subtotal0 = mDatabase.listReplacement().get(0).getPhno();
            total0 = mDatabase.listReplacement().get(0).getTotal();
        }
        else {
            replacementView.setVisibility(View.GONE);
            Toast.makeText(this, "Ningún protocolo registrado en la base de datos", Toast.LENGTH_SHORT).show();
        }

        /*Toast.makeText(this,    "Item: " +allReplacement.size()
                , Toast.LENGTH_SHORT).show();*/

        if (allReplacement.size() <= 1){
            cantidad1 = " ";producto1 = " ";subtotal1 = " ";total1 = 0;
        }else{
            cantidad1 = allReplacement.get(1).getCantidadProd();producto1 = allReplacement.get(1).getName();
            subtotal1 = allReplacement.get(1).getPhno();total1 = allReplacement.get(1).getTotal();
        }

        if (allReplacement.size() <= 2){
            cantidad2 = " ";producto2 = " ";subtotal2 = " ";total2 = 0;
        }else{
            cantidad2 = allReplacement.get(2).getCantidadProd();producto2 = allReplacement.get(2).getName();
            subtotal2 = allReplacement.get(2).getPhno();total2 = allReplacement.get(2).getTotal();
        }

        if (allReplacement.size() <= 3){
            cantidad3 = " ";producto3 = " ";subtotal3 = " ";total3 = 0;
        }else{
            cantidad3 = allReplacement.get(3).getCantidadProd();producto3 = allReplacement.get(3).getName();
            subtotal3 = allReplacement.get(3).getPhno();total3 = allReplacement.get(3).getTotal();
        }

        if (allReplacement.size() <= 4){
            cantidad4 = " ";producto4 = " ";subtotal4 = " ";total4 = 0;
        }else{
            cantidad4 = allReplacement.get(4).getCantidadProd();producto4 = allReplacement.get(4).getName();
            subtotal4 = allReplacement.get(4).getPhno();total4 = allReplacement.get(4).getTotal();
        }

        if (allReplacement.size() <= 5){
            cantidad5 = " ";producto5 = " ";subtotal5 = " ";total5 = 0;
        }else{
            cantidad5 = allReplacement.get(5).getCantidadProd();producto5 = allReplacement.get(5).getName();
            subtotal5 = allReplacement.get(5).getPhno();total5 = allReplacement.get(5).getTotal();
        }

        if (allReplacement.size() <= 6){
            cantidad6 = " ";producto6 = " ";subtotal6 = " ";total6 = 0;
        }else{
            cantidad6 = allReplacement.get(6).getCantidadProd();producto6 = allReplacement.get(6).getName();
            subtotal6 = allReplacement.get(6).getPhno();total6 = allReplacement.get(6).getTotal();
        }

        if (allReplacement.size() <= 7){
            cantidad7 = " ";producto7 = " ";subtotal7 = " ";total7 = 0;
        }else{
            cantidad7 = allReplacement.get(7).getCantidadProd();producto7 = allReplacement.get(7).getName();
            subtotal7 = allReplacement.get(7).getPhno();total7 = allReplacement.get(7).getTotal();
        }

        if (allReplacement.size() <= 8){
            cantidad8 = " ";producto8 = " ";subtotal8 = " ";total8 = 0;
        }else{
            cantidad8 = allReplacement.get(8).getCantidadProd();producto8 = allReplacement.get(8).getName();
            subtotal8 = allReplacement.get(8).getPhno();total8 = allReplacement.get(8).getTotal();
        }

        if (allReplacement.size() <= 9){
            cantidad9 = " ";producto9 = " ";subtotal9 = " ";total9 = 0;
        }else{
            cantidad9 = allReplacement.get(9).getCantidadProd();producto9 = allReplacement.get(9).getName();
            subtotal9 = allReplacement.get(9).getPhno();total9 = allReplacement.get(9).getTotal();
        }




        FloatingActionButton fbtnAdd = findViewById(R.id.fbtnAdd);
        fbtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTaskDialog();
            }
        });

        Toast.makeText(this,    cantidad0 + " " + producto0 + " " +subtotal0 + " " +total0 + "\n"
                        + cantidad1 + " " + producto1 + " " +subtotal1 + " " +total1 + "\n"
                        + cantidad2 + " " + producto2 + " " +subtotal2 + " " +total2 + "\n"
                , Toast.LENGTH_SHORT).show();
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