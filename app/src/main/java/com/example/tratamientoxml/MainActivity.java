package com.example.tratamientoxml;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.tratamientoxml.Actividad1.Actividad1;
import com.example.tratamientoxml.Actividad2.Actividad2;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void actividad1(View view)
    {
        Intent intent = new Intent(MainActivity.this, Actividad1.class);
        startActivity(intent);

    }

    public void actividad2(View view)
    {
        Intent intent = new Intent(MainActivity.this, Actividad2.class);
        startActivity(intent);
    }
}
