package com.example.demo.componentes;

public class Impresion_tarea {
    private String numeroDeArchivo;
    private String nombreDeArchivo;
    private int numeroDeHojas;
    private String horaDeAcceso;

    public Impresion_tarea(String numeroDeArchivo, String nombreDeArchivo, int numeroDeHojas, String horaDeAcceso) {
        this.numeroDeArchivo = numeroDeArchivo;
        this.nombreDeArchivo = nombreDeArchivo;
        this.numeroDeHojas = numeroDeHojas;
        this.horaDeAcceso = horaDeAcceso;
    }

    public String getNumeroDeArchivo() {
        return numeroDeArchivo;
    }

    public void setNumeroDeArchivo(String numeroDeArchivo) {
        this.numeroDeArchivo = numeroDeArchivo;
    }

    public String getNombreDeArchivo() {
        return nombreDeArchivo;
    }

    public void setNombreDeArchivo(String nombreDeArchivo) {
        this.nombreDeArchivo = nombreDeArchivo;
    }

    public int getNumeroDeHojas() {
        return numeroDeHojas;
    }

    public void setNumeroDeHojas(int numeroDeHojas) {
        this.numeroDeHojas = numeroDeHojas;
    }

    public String getHoraDeAcceso() {
        return horaDeAcceso;
    }

    public void setHoraDeAcceso(String horaDeAcceso) {
        this.horaDeAcceso = horaDeAcceso;
    }
}
