package com.example.demo.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class CategoriasDAO {
    private int idCategoria;
    private String nombre;

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void INSERTAR(){
        String query = "INSERT INTO categorias(nombre) VALUES('" + nombre + "')";

        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ACTUALIZAR(){
        String query = "UPDATE categorias SET nombre='" + nombre + "' WHERE idCategoria=" + idCategoria;

        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ELIMINAR(){
        String query = "DELETE FROM categorias WHERE idCategoria=" + idCategoria;

        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<CategoriasDAO> CONSULTAR(){
        ObservableList<CategoriasDAO> listaCategorias = FXCollections.observableArrayList();
        String query = "SELECT * FROM categorias";

        try {
            CategoriasDAO objCategoria;
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()){
                objCategoria = new CategoriasDAO();
                objCategoria.idCategoria = res.getInt("idCategoria");
                objCategoria.nombre = res.getString("nombre");
                listaCategorias.add(objCategoria);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaCategorias;
    }
}
