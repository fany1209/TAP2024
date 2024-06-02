package com.example.demo.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class PromocionesDAO {
    private int idPromocion;
    private String nombre;
    private String descripcion;
    private float costoPromo;
    private String fechaInicio;
    private String fechaFin;

    public int getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(int idPromocion) {
        this.idPromocion = idPromocion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getCostoPromo() {
        return costoPromo;
    }

    public void setCostoPromo(float costoPromo) {
        this.costoPromo = costoPromo;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void INSERTAR(){
        String query = "INSERT INTO promociones(nombre, descripcion, costo_promo, fecha_inicio, fecha_fin) " +
                "VALUES('" + nombre + "', '" + descripcion + "', " + costoPromo + ", '" + fechaInicio + "', '" + fechaFin + "')";

        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ACTUALIZAR(){
        String query = "UPDATE promociones SET nombre='" + nombre + "', descripcion='" + descripcion +
                "', costo_promo=" + costoPromo + ", fecha_inicio='" + fechaInicio + "', fecha_fin='" + fechaFin +
                "' WHERE idPromocion=" + idPromocion;

        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ELIMINAR(){
        String query = "DELETE FROM promociones WHERE idPromocion=" + idPromocion;

        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<PromocionesDAO> CONSULTAR(){
        ObservableList<PromocionesDAO> listaPromociones = FXCollections.observableArrayList();
        String query = "SELECT * FROM promociones";

        try {
            PromocionesDAO objPromocion;
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()){
                objPromocion = new PromocionesDAO();
                objPromocion.idPromocion = res.getInt("idPromocion");
                objPromocion.nombre = res.getString("nombre");
                objPromocion.descripcion = res.getString("descripcion");
                objPromocion.costoPromo = res.getFloat("costo_promo");
                objPromocion.fechaInicio = res.getString("fecha_inicio");
                objPromocion.fechaFin = res.getString("fecha_fin");
                listaPromociones.add(objPromocion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaPromociones;
    }
}
