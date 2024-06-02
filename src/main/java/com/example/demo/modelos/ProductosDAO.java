package com.example.demo.modelos;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.Statement;

public class ProductosDAO {
    private int idProducto;
    private String nombre;
    private double precio;
    private double costo;
    private int idCategoria;
    private int idPromocion;

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public int getIdPromocion() {
        return idPromocion;
    }

    public void setIdPromocion(int idPromocion) {
        this.idPromocion = idPromocion;
    }

    public void INSERTAR() {
        String query = "INSERT INTO productos(nombre, precio, costo, id_categoria, idPromocion) VALUES('" + nombre + "', " + precio + ", " + costo + ", " + idCategoria + ", " + idPromocion + ")";
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ACTUALIZAR() {
        String query = "UPDATE productos SET nombre='" + nombre + "', precio=" + precio + ", costo=" + costo + ", id_categoria=" + idCategoria + ", idPromocion=" + idPromocion + " WHERE idProducto=" + idProducto;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ELIMINAR() {
        String query = "DELETE FROM productos WHERE idProducto=" + idProducto;
        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<ProductosDAO> CONSULTAR() {
        ObservableList<ProductosDAO> listaProductos = FXCollections.observableArrayList();
        String query = "SELECT * FROM productos";
        try {
            ProductosDAO objProducto;
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                objProducto = new ProductosDAO();
                objProducto.idProducto = res.getInt("idProducto");
                objProducto.nombre = res.getString("nombre");
                objProducto.precio = res.getDouble("precio");
                objProducto.costo = res.getDouble("costo");
                objProducto.idCategoria = res.getInt("id_categoria");
                objProducto.idPromocion = res.getInt("idPromocion");
                listaProductos.add(objProducto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaProductos;
    }
    public ObservableList<ProductosDAO> obtenerProductosPorCategoria(String categoria) {
        ObservableList<ProductosDAO> productosPorCategoria = FXCollections.observableArrayList();
        String query = "SELECT * FROM productos WHERE id_categoria = (SELECT idCategoria FROM categorias WHERE nombre = '" + categoria + "')";

        try {
            Statement stmt = Conexion.connection.createStatement();
            ResultSet res = stmt.executeQuery(query);
            while (res.next()) {
                ProductosDAO producto = new ProductosDAO();
                producto.setIdProducto(res.getInt("idProducto"));
                producto.setNombre(res.getString("nombre"));
                producto.setPrecio(res.getDouble("precio"));
                producto.setCosto(res.getDouble("costo"));
                producto.setIdCategoria(res.getInt("id_categoria"));
                producto.setIdPromocion(res.getInt("idPromocion"));
                productosPorCategoria.add(producto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productosPorCategoria;
    }
}
