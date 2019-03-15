package com.example.f_alfonsofernandez;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    private static final int LLAMADA_TELEFONO = 1;
    private int codigo = 1;
    private int Devuelve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            deployDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextView lbl = (TextView) findViewById(R.id.txtTitulo);
        registerForContextMenu(lbl);

        ImageView img = (ImageView) findViewById(R.id.imgInicio);
        registerForContextMenu(img);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Devuelve = data.getExtras().getInt("Int");
                finish();
            }
        }
    }


    private void deployDatabase() throws IOException {

        //Open your local db as the input stream
        String packageName = getApplicationContext().getPackageName();
        String DB_PATH = "/data/data/" + packageName + "/databases/";
        //Create the directory if it does not exist
        File directory = new File(DB_PATH);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String DB_NAME = "Compuestos"; //The name of the source sqlite file

        InputStream myInput = getAssets().open("Compuestos");

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        menu.setHeaderTitle("Eliga una opcion");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contextual, menu);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.item1:
                Intent i = new Intent(MainActivity.this, ListaCompuestos.class);
                startActivityForResult(i, 1);
                return true;

            case R.id.item2:
                AlertDialog.Builder ventana2 = new AlertDialog.Builder(MainActivity.this);
                ventana2.setTitle("!ATTENTION¡");
                ventana2.setMessage("@string/textoSalir");
                ventana2.setIcon(R.drawable.flask);
                ventana2.setCancelable(false);
                ventana2.setPositiveButton("@string/OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                ventana2.setNegativeButton("@string/cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                ventana2.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflador = getMenuInflater();
        inflador.inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.item1:
                AlertDialog.Builder ventana = new AlertDialog.Builder(MainActivity.this);
                ventana.setTitle("Acerca de...");
                ventana.setMessage("Autor: Alfonso Fernández Álvarez\nMarzo - 2019");
                ventana.setIcon(R.drawable.flask);
                ventana.show();
                return true;

            case R.id.item2:
                if (Build.VERSION.SDK_INT >= 23) {
                    if (checkSelfPermission(android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                        Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:(+34)999112233"));
                        startActivity(i);
                    } else {
                        requestPermissions(new String[]{android.Manifest.permission.CALL_PHONE}, LLAMADA_TELEFONO);
                    }
                } else {
                    Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:(+34)999112233"));
                    startActivity(i);
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Devuelve", Devuelve);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Devuelve = savedInstanceState.getInt("Devuelve");
        Toast.makeText(MainActivity.this, "El numero de aciertos de la ultima actividad es: ." + Devuelve, Toast.LENGTH_LONG).show();
    }

}
