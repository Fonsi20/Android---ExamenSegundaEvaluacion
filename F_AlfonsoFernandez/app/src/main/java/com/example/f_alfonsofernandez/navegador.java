package com.example.f_alfonsofernandez;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class navegador extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navegador);

        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.formulacionquimica.com/"));
        startActivity(i);
    }
}
