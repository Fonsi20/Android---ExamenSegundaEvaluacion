package com.example.f_alfonsofernandez;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.f_alfonsofernandez.BBDD.BDHelper;
import com.example.f_alfonsofernandez.Objeto.Compuestos;

public class JuegoActivity extends AppCompatActivity {

    int num = 1;
    private String BDname;
    private int BDversion;
    private SQLiteDatabase DBCompuestos;
    private String nombreC;
    private int valor = 0;
    private int fallos = 0;
    private static final int NOTIF_ALERTA_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego);

        final LinearLayout llJuego = (LinearLayout) findViewById(R.id.llEdit);
        final LinearLayout llEscoge = (LinearLayout) findViewById(R.id.llEscoger);
        Button btnComprobar = (Button) findViewById(R.id.btnComprobar);
        Button btnContinuar = (Button) findViewById(R.id.btnContinuar);
        final EditText edEntrada = (EditText) findViewById(R.id.edEntrada);
        final RadioButton rbYes = (RadioButton) findViewById(R.id.rbYes);
        final RadioButton rbNo = (RadioButton) findViewById(R.id.rbNo);

        llJuego.setVisibility(View.VISIBLE);
        llEscoge.setVisibility(View.GONE);
        BDname = "Compuestos";
        BDversion = 1;
        BDHelper bdhelper = new BDHelper(this, BDname, null, BDversion);
        DBCompuestos = bdhelper.getWritableDatabase();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        nombreC = bundle.getString("NombreDelProducto");

        edEntrada.setHint(nombreC);

        btnComprobar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edEntrada.getText().toString().equals("")) {
                    Toast.makeText(JuegoActivity.this, "You must enter a formula", Toast.LENGTH_LONG).show();
                } else {
                    Cursor cursor = DBCompuestos.rawQuery("SELECT * FROM COMPUESTOS", null);
                    while (cursor.moveToNext()) {
                        Compuestos com = new Compuestos();
                        com.setNombre(cursor.getString(1));
                        com.setSiglas(cursor.getString(0));
                        if (com.getNombre().equals(nombreC)) {
                            if (com.getSiglas().equals(edEntrada.getText().toString().toUpperCase())) {
                                llJuego.setVisibility(View.GONE);
                                llEscoge.setVisibility(View.VISIBLE);
                                valor ++;
                                Toast.makeText(JuegoActivity.this, com.getSiglas() + "@string/correcto", Toast.LENGTH_LONG).show();
                            } else {
                                fallos ++;
                                if(fallos >= 3){
                                    NotificationCompat.Builder ncBuilder = new NotificationCompat.Builder(JuegoActivity.this);

                                    ncBuilder.setSmallIcon(android.R.drawable.stat_sys_warning);
                                    ncBuilder.setTicker("Alerta!");

                                    ncBuilder.setContentTitle("Tiene más de 2 errores");
                                    ncBuilder.setContentText("Consulte la web para aprender más.");

                                    Bitmap icono= BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
                                    ncBuilder.setLargeIcon(icono);

                                    Intent i = new Intent(JuegoActivity.this, navegador.class);
                                    PendingIntent pi = PendingIntent.getActivity(JuegoActivity.this, 1, i, 0);
                                    ncBuilder.setContentIntent(pi);

                                    ncBuilder.setAutoCancel(true);

                                    NotificationManager nm = (NotificationManager)getSystemService(JuegoActivity.this.NOTIFICATION_SERVICE);
                                    Notification notificacion=ncBuilder.build();
                                    nm.notify(NOTIF_ALERTA_ID, notificacion);
                                }
                                Toast.makeText(JuegoActivity.this, "@string/fallo", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            }
        });

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (rbYes.isChecked()) {
                    Log.i("FONSI", "entro en el SI, el valor de valor es = " + valor);

                    Intent x = new Intent();
                    x.putExtra("Int", valor);
                    setResult(RESULT_CANCELED, x);
                    finish();
                }
                if (rbNo.isChecked()) {
                    Log.i("FONSI", "entro en el NO");
                    Intent y = new Intent();
                    y.putExtra("Int", valor);
                    setResult(RESULT_OK, y);
                    finish();
                }
            }
        });
    }
}
