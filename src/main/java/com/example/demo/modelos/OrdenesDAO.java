package com.example.demo.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;
import java.sql.Statement;

public class OrdenesDAO {
    private int idOrden;
    private int idEmpleado;
    private String fecha;
    private String observaciones;
    private int idMesa;



    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public void INSERTAR() {
        String query = String.format("INSERT INTO Ordenes(idEmpleado, fecha, observaciones, idMesa) " +
                "VALUES(%d, '%s', '%s', %d)", idEmpleado, fecha, observaciones, idMesa);
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ACTUALIZAR() {
        String query = String.format("UPDATE Ordenes SET idEmpleado=%d, fecha='%s', observaciones='%s', idMesa=%d WHERE idOrden=%d",
                idEmpleado, fecha, observaciones, idMesa, idOrden);
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ELIMINAR() {
        String query = "DELETE FROM Ordenes WHERE idOrden=" + idOrden;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public ObservableList<OrdenesDAO> CONSULTAR() {
        ObservableList<OrdenesDAO> listaOrdenes = FXCollections.observableArrayList();
        String query = "SELECT * FROM Ordenes";
        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                OrdenesDAO orden = new OrdenesDAO();
                orden.setIdOrden(res.getInt("idOrden"));
                orden.setIdEmpleado(res.getInt("idEmpleado"));
                orden.setFecha(res.getString("fecha"));
                orden.setIdMesa(res.getInt("idMesa"));
                listaOrdenes.add(orden);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaOrdenes;
    }
}
