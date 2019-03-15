package com.example.f_alfonsofernandez;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.f_alfonsofernandez.Adaptador.AdaptadorLista;
import com.example.f_alfonsofernandez.BBDD.BDHelper;
import com.example.f_alfonsofernandez.Objeto.Compuestos;

import java.util.ArrayList;

public class ListaCompuestos extends AppCompatActivity {

    ArrayList<Compuestos> CompuestosList;
    private int num = 1;
    private String BDname;
    private int BDversion;
    private SQLiteDatabase DBCompuestos;
    private int valor = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_compuestos);

        BDname = "Compuestos";
        BDversion = 1;
        BDHelper bdhelper = new BDHelper(this, BDname, null, BDversion);
        DBCompuestos = bdhelper.getWritableDatabase();

        ListView lv = (ListView) findViewById(R.id.lv);

        consultarListaCompuestos();

        DBCompuestos.close();
        AdaptadorLista adaptador = new AdaptadorLista(this, CompuestosList);
        lv.setAdapter(adaptador);
    }

    private void consultarListaCompuestos() {
        Compuestos com = null;
        CompuestosList = new ArrayList<Compuestos>();

        //select * from Alimentos, la categoria ya la tengo arriba, que la recibo el putExtra
        Cursor cursor = DBCompuestos.rawQuery("SELECT * FROM COMPUESTOS", null);

        while (cursor.moveToNext()) {
            com = new Compuestos();
            com.setNombre(cursor.getString(1));
            com.setSiglas(cursor.getString(0));

            CompuestosList.add(com);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("FONSI", "almenos entra aqui?");
        Toast.makeText(ListaCompuestos.this, "VAAAAMOSS.", Toast.LENGTH_LONG).show();
        Log.i("FONSI", "RESULT = " + resultCode);


        if (resultCode == RESULT_CANCELED) {
            Log.i("FONSI", "rescodeee = " + requestCode);
            if (requestCode == 1) {
                valor = valor + data.getExtras().getInt("Int");
                Log.i("FONSI", "entró en la vuelta de MANTENERSE y en valor hay estos: " + valor);
            }
        }
        if (resultCode == RESULT_OK) {
            Log.i("FONSI", "rescodeee = " + requestCode);
            if (requestCode == 1) {
                String texto = data.getExtras().getString("Int");
                valor = valor + data.getExtras().getInt("Int");
                Log.i("FONSI", "entró en la vuelta de salida y en valor hay estos: " + valor);
                Intent j = new Intent();
                j.putExtra("Int", valor);
                setResult(RESULT_OK, j);
                finish();
            }
        }
    }
}
