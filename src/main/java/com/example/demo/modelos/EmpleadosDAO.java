package com.example.demo.modelos;

import java.sql.Statement;

public class EmpleadosDAO {
    int  idEmpleado;
    String nomEmpleado;
    String RCFEmplado;
    float salario;
    String telefono;
    String direccion;

    public void INSERTAR(){
        String query ="INSERT INTO Empleado(nomEmpleado," +
                "RFCEmpleado,salario,telefono,direccion) " +
                "VALUES('"+nomEmpleado+"','"+RCFEmplado+"'" +
                ","+salario+",'"+telefono+"','"+direccion+"')";

        try {
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public void ACTUALIZAR(){
        String query = "UPDATE Empleado SET nomEmpleado='"+nomEmpleado+"'," +
                "RCFEmpleado='"+RCFEmplado+"',salario="+salario+"," +
                "telefono='"+telefono+"',direccion='"+direccion+"' " +
                "WHERE idEmpledo ="+idEmpleado;
        try{
            Statement stmt = Conexion.connection.createStatement();
            stmt.executeUpdate(query);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void ELIMINAR(){}
    public void CONSULTAR(){}
}
