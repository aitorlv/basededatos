package com.example.aitor.pruebabasedatos;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by aitor on 08/12/2014.
 */
public class AdpatadorPartido extends CursorAdapter {


    public AdpatadorPartido(Context context, Cursor c) {
        super(context, c,true);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater i = LayoutInflater.from(parent.getContext());
        View v = i.inflate(R.layout.detalle, parent, false);
        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tv1, tv2, tv3, tv4;
        tv1 = (TextView)view.findViewById(R.id.tvNombre);
        tv3 = (TextView)view.findViewById(R.id.tvFecha);
        tv4 = (TextView)view.findViewById(R.id.tvTelefono);
        Partido j = GestorPartido.getRow(cursor);
        tv1.setText(j.getContrincante());
        tv4.setText(j.getValoracion()+"");
        tv3.setText(j.getIdJugador()+"");
    }
}
