package com.example.recyclerviewdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class ReporteSysmed extends AppCompatActivity {

    EditText edtObservaciones;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte_sysmed);

        edtObservaciones = (EditText)findViewById(R.id.edt_observaciones);

        name = getIntent().getStringExtra("name");
        edtObservaciones.setText(name);

    }
}