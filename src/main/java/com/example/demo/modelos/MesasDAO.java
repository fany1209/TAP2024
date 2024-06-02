package com.example.demo.modelos;

import com.example.demo.vistas.MesasTaqueria;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;

import java.sql.ResultSet;
import java.sql.Statement;

public class MesasDAO {
    private int idMesa;
    private int numero;
    private String estado; // Nuevo atributo para el estado de la mesa

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void INSERTAR() {
        String query = "INSERT INTO Mesas(numero, estado) VALUES(" + numero + ", '" + estado + "')";

        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ACTUALIZAR() {
        String query = "UPDATE Mesas SET numero=" + numero + ", estado='" + estado + "' WHERE idMesa=" + idMesa;

        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void ELIMINAR() {
        String query = "DELETE FROM Mesas WHERE idMesa=" + idMesa;

        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<MesasDAO> CONSULTAR() {
        ObservableList<MesasDAO> listaMesas = FXCollections.observableArrayList();
        String query = "SELECT * FROM Mesas";

        try {
            MesasDAO objMesa;
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                objMesa = new MesasDAO();
                objMesa.idMesa = res.getInt("idMesa");
                objMesa.numero = res.getInt("numero");
                objMesa.estado = res.getString("estado"); // Obtener el estado de la mesa
                listaMesas.add(objMesa);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaMesas;
    }
}
