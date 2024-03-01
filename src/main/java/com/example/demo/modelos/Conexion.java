package com.example.demo.modelos;

import com.mysql.cj.jdbc.Driver;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    static private String DB = "taqueria";
    static private String USER = "adminTacos";
    static private String PWD = "1234";

    static public Connection connection;

    public static void crearConexion (){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/"+
                    DB
                    ,USER,PWD);
            System.out.println("conexion establecida con exito");

        } catch (Exception e) {
          e.printStackTrace();
        }
    }
}
