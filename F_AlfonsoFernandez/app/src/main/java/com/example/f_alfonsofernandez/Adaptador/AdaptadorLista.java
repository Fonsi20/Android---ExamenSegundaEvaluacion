package com.example.f_alfonsofernandez.Adaptador;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.f_alfonsofernandez.JuegoActivity;
import com.example.f_alfonsofernandez.Objeto.Compuestos;
import com.example.f_alfonsofernandez.R;

import java.util.ArrayList;

public class AdaptadorLista extends ArrayAdapter {

    private Activity context;
    private ArrayList<Compuestos> compuesto;

    public AdaptadorLista(Activity context, ArrayList<Compuestos> compuesto) {
        super(context, R.layout.item_lista, compuesto);
        this.context = context;
        this.compuesto = compuesto;
    }

    static class ViewHolder {
        TextView txNombre;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View fila = convertView;
        final ViewHolder holder;

        if (fila == null) {
            LayoutInflater layoutInflater = context.getLayoutInflater();

            fila = layoutInflater.inflate(R.layout.item_lista, null);

            holder = new ViewHolder();

            holder.txNombre = (TextView) fila.findViewById(R.id.txNombre);

            fila.setTag(holder);
        } else {
            holder = (ViewHolder) fila.getTag();
        }

        fila.setBackgroundColor(context.getResources().getColor(R.color.white));
        holder.txNombre.setText(compuesto.get(position).getNombre());


        holder.txNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, JuegoActivity.class);
                i.putExtra("NombreDelProducto", holder.txNombre.getText().toString());
                context.startActivityForResult(i,1);
            }
        });


        return fila;
    }
}