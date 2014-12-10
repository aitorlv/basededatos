package com.example.aitor.pruebabasedatos;

import java.io.Serializable;

/**
 * Created by aitor on 08/12/2014.
 */
public class Partido implements Serializable,Comparable<Partido> {

    private long idPartido,idJugador;
    private String contrincante;
    private int valoracion;

    public Partido(){
        this(0,0,null,0);
    }

    public Partido(long idPartido, long idJugador, String contrincante, int valoracion) {
        this.idPartido = idPartido;
        this.idJugador = idJugador;
        this.contrincante = contrincante;
        this.valoracion = valoracion;
    }
    public Partido( long idJugador, String contrincante, int valoracion) {
        this.idJugador = idJugador;
        this.contrincante = contrincante;
        this.valoracion = valoracion;
    }

    public long getIdPartido() {
        return idPartido;
    }

    public void setIdPartido(long idPartido) {
        this.idPartido = idPartido;
    }

    public long getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(long idJugador) {
        this.idJugador = idJugador;
    }

    public String getContrincante() {
        return contrincante;
    }

    public void setContrincante(String contrincante) {
        this.contrincante = contrincante;
    }

    public int getValoracion() {
        return valoracion;
    }

    public void setValoracion(int valoracion) {
        this.valoracion = valoracion;
    }

    @Override
    public String toString() {
        return "Partido{" +
                "idPartido=" + idPartido +
                ", idJugador=" + idJugador +
                ", contrincante='" + contrincante + '\'' +
                ", valoracion=" + valoracion +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Partido)) return false;

        Partido partido = (Partido) o;

        if (idJugador != partido.idJugador) return false;
        if (idPartido != partido.idPartido) return false;
        if (valoracion != partido.valoracion) return false;
        if (contrincante != null ? !contrincante.equals(partido.contrincante) : partido.contrincante != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (idPartido ^ (idPartido >>> 32));
        result = 31 * result + (int) (idJugador ^ (idJugador >>> 32));
        result = 31 * result + (contrincante != null ? contrincante.hashCode() : 0);
        result = 31 * result + valoracion;
        return result;
    }

    @Override
    public int compareTo(Partido partido) {
        return 0;
    }
}
