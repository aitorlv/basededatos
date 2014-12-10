package com.example.aitor.pruebabasedatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aitor on 08/12/2014.
 */
public class GestorPartido {
    private Ayudante abd;
    private SQLiteDatabase bd;

    public GestorPartido(Context c) {
        abd = new Ayudante(c);
    }
    public void open() {
        bd = abd.getWritableDatabase();
    }
    public void openRead() {
        bd = abd.getReadableDatabase();
    }
    public void close() {
        abd.close();
    }

    public long insert(Partido objeto) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaPartido.IDJUGADOR, objeto.getIdJugador());
        valores.put(Contrato.TablaPartido.VALORACION, objeto.getValoracion());
        valores.put(Contrato.TablaPartido.CONTRINCANTE, objeto.getContrincante());
        long id = bd.insert(Contrato.TablaPartido.TABLA, null, valores);
        //id es el código autonumérico
        return id;
    }

    public int delete(Partido objeto) {
        String condicion = Contrato.TablaPartido._ID + " = ?";
        String[] argumentos = { objeto.getIdPartido() + "" };
        int cuenta = bd.delete(Contrato.TablaPartido.TABLA, condicion,argumentos);
        return cuenta;
    }

    public int delete(int id){
        return delete(new Partido(id, 0, null, 0));
    }

    /*
    public int deleteV2() {
        String condicion = Contrato.TablaJugador.NOMBRE + " = ? or " + Contrato.TablaJugador.VALORACION + " < ? ";
        //delete from jugador where nombre = ? or valoracion < ?
        String nombre = "Pepe";
        int valoracion = 6;
        String[] argumentos = {nombre, valoracion+""};
        int cuenta = bd.delete(Contrato.TablaJugador.TABLA, condicion,argumentos);
        nombre = "Juan";
        valoracion = 3;
        argumentos[0]= nombre;
        argumentos[1]= valoracion+"";
        cuenta = bd.delete(Contrato.TablaJugador.TABLA, condicion,argumentos);
        return cuenta;
    }
    */

    public int update(Partido objeto) {
        ContentValues valores = new ContentValues();
        valores.put(Contrato.TablaPartido.VALORACION, objeto.getValoracion());
        valores.put(Contrato.TablaPartido.CONTRINCANTE, objeto.getContrincante());
        String condicion = Contrato.TablaPartido._ID + " = ?";
        String[] argumentos = { objeto.getIdPartido() + "" };
        int cuenta = bd.update(Contrato.TablaPartido.TABLA, valores, condicion, argumentos);
        return cuenta;
    }

    public List<Partido> select(String condicion, String[] parametros, String orden) {
        List<Partido> lo = new ArrayList<Partido>();
        Cursor cursor = bd.query(Contrato.TablaPartido.TABLA, null, condicion, parametros, null, null, orden);
        /*
        String[] campos = {"nombre", "valoracion"};
        String[] parametros = {"Pepe", "6"};
        bd.query("tabla", campos, "nombre = ? and valoracion = ?", parametros, "groupBy", "having", "orderBy");
        //select nombre, valoracion from tabla
        //where nombre = ? and valoracion = ?
        //...
        */
        cursor.moveToFirst();
        Partido objeto;
        while (!cursor.isAfterLast()) {
            objeto = getRow(cursor);
            lo.add(objeto);
            cursor.moveToNext();
        }
        cursor.close();
        return lo;
    }

    public ArrayList<Partido> select() {
        ArrayList<Partido> lo = new ArrayList<Partido>();
        Cursor cursor = bd.query(Contrato.TablaPartido.TABLA, null, null, null, null, null, null);
        cursor.moveToFirst();
        Partido objeto;
        while (!cursor.isAfterLast()) {
            objeto = getRow(cursor);
            lo.add(objeto);
            cursor.moveToNext();
        }
        cursor.close();
        return lo;
    }

    public static Partido getRow(Cursor c) {
        Partido objeto = new Partido();
        objeto.setIdPartido(c.getLong(0));
        objeto.setIdJugador(c.getLong(1));
        objeto.setValoracion(c.getInt(2));
        objeto.setContrincante(c.getString(3));
        return objeto;
    }

    /*
    public Jugador getRow(long id) {
        String[] proyeccion = { Contrato.TablaJugador._ID,
                Contrato.TablaJugador.NOMBRE,
                Contrato.TablaJugador.TELEFONO,
                Contrato.TablaJugador.VALORACION,
                Contrato.TablaJugador.FNAC};
        String where = Contrato.TablaJugador._ID + " = ?";
        String[] parametros = new String[] { id+"" };
        String groupby = null;
        String having = null;
        String orderby = Contrato.TablaJugador.NOMBRE + " ASC";
        Cursor c = bd.query(Contrato.TablaJugador.TABLA, proyeccion,
                where, parametros, groupby, having, orderby);
        c.moveToFirst();
        Jugador objeto = getRow(c);
        c.close();
        return objeto;
    }
    */

    public Partido getRow(long id){
        List<Partido> partido = select(Contrato.TablaPartido._ID + " = ?", new String[]{id + ""}, null);
        Partido objeto = partido.get(0);
        if (!partido.isEmpty())
            return objeto;
        else
            return null;
    }

    public Cursor getCursor(String condicion, String[] parametros, String orden) {
        Cursor cursor = bd.query(Contrato.TablaPartido.TABLA, null, condicion, parametros, null, null, orden);
        return cursor;
    }

    public Cursor getCursor() {
        Cursor cursor = bd.query(Contrato.TablaPartido.TABLA, null, null, null, null, null, null);
        return cursor;
    }

    public Partido getRow(String nombre) {
        String[] parametros = new String[] { nombre };
        Cursor c = bd.rawQuery("select * from "+
                Contrato.TablaPartido.TABLA
                + " where " + Contrato.TablaPartido.CONTRINCANTE + " = ?", parametros);
        c.moveToFirst();
        Partido objeto = getRow(c);
        c.close();
        return objeto;
    }
    public long media(String id) {
        String[] parametros = new String[] { id };
        Cursor c = bd.rawQuery("select avg(valoracion) from "+
                Contrato.TablaPartido.TABLA
                + " where " + Contrato.TablaPartido.IDJUGADOR + " = ?", parametros);
        c.moveToFirst();
        long media = c.getLong(0);
        c.close();
        return media;
    }
}
