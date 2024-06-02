package com.example.demo.vistas;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.RandomAccessFile;


public class CuadroMagico extends Stage {
    private Scene Escena_Principal, Escena_CM;
    private GridPane Ventana_datos, Ventana_CM;
    private TextField Cuadro_texto;
    private Button Calcular, Celda;
    private Label Introducir_tamano;
    private int Tamano, i, Numero, Fila, Columna, Valor;
    private RandomAccessFile Archivo;
    private Stage S_CM;

    public CuadroMagico() {
        CrearUI();
        Escena_Principal = new Scene(Ventana_datos, 800, 400);
        Escena_Principal.getStylesheets().add(getClass().getResource("/estilos/cm.css").toString());
        this.setTitle("Cuadro mágico");
        this.setScene(Escena_Principal);
        this.show();
    }

    private void CrearUI() {
        Ventana_datos = new GridPane();
        Ventana_datos.setAlignment(Pos.TOP_CENTER);
        Ventana_datos.setHgap(10);
        Ventana_datos.setVgap(10);
        Introducir_tamano = new Label("Tamaño del cuadro");
        Cuadro_texto = new TextField();
        Calcular = new Button("Calcular");
        Calcular.setOnAction(event -> determina());
        Ventana_datos.add(Introducir_tamano, 0, 0);
        Ventana_datos.add(Cuadro_texto, 1, 0);
        Ventana_datos.add(Calcular, 1, 1);
    }

    private void determina() {
        try {
            Tamano = Integer.parseInt(Cuadro_texto.getText());
            if (Tamano < 3 || Tamano % 2 == 0) {
                mostrarError("El tamaño debe ser impar y mayor o igual a 3.");
                return;
            }
            generar(Tamano);
            Mostrar(Tamano);
        } catch (NumberFormatException e) {
            mostrarError("Por favor, introduzca un número válido para el tamaño.");
        }
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR, mensaje, ButtonType.OK);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.showAndWait();
    }

    private void generar(int tamano) {
        if (tamano % 2 == 0) {
            throw new IllegalArgumentException("El tamaño debe ser un número impar.");
        }

        try {
            // Crear y abrir el archivo para lectura/escritura
            RandomAccessFile archivo = new RandomAccessFile("cuadroMagico.txt", "rw");

            // Inicializar el archivo con ceros
            for (int i = 0; i < tamano * tamano; i++) {
                archivo.writeInt(0);
            }

            int numero = 1;
            int fila = tamano / 2;
            int columna = tamano / 2;

            while (numero <= tamano * tamano) {
                archivo.seek((fila * tamano + columna) * Integer.BYTES);

                // Si el lugar está ocupado, mover hacia abajo
                if (archivo.readInt() != 0) {
                    fila++;
                } else {
                    // Escribir el número en la posición calculada
                    archivo.seek((fila * tamano + columna) * Integer.BYTES);
                    archivo.writeInt(numero++);

                    // Mover hacia la esquina superior derecha
                    fila--;
                    columna++;
                }

                // Ajustes para cuando se sale de los límites
                if (fila < 0) {
                    fila = tamano - 1;
                }
                if (columna == tamano) {
                    columna = 0;
                }
                if (fila >= tamano) {
                    fila = 0;
                }
                if (columna < 0) {
                    columna = tamano - 1;
                }
            }

            // Cerrar el archivo
            archivo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void Mostrar(int Tamano) {
        S_CM = new Stage();
        Ventana_CM = new GridPane();
        Ventana_CM.setAlignment(Pos.CENTER);
        Ventana_CM.setHgap(10);
        Ventana_CM.setVgap(10);
        try {
            Archivo = new RandomAccessFile("cuadroMagico.txt", "r");
            for (Fila = 0; Fila < Tamano; Fila++) {
                for (Columna = 0; Columna < Tamano; Columna++) {
                    Archivo.seek((Fila * Tamano + Columna) * Integer.BYTES);
                    Valor = Archivo.readInt();
                    Celda = new Button(String.valueOf(Valor));
                    Celda.setMinWidth(30);
                    Celda.setMinHeight(30);
                    Ventana_CM.add(Celda, Columna + 2, Fila);
                }
            }
            Archivo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Escena_CM = new Scene(Ventana_CM, 800, 800);
        Escena_CM.getStylesheets().add(getClass().getResource("/estilos/cm.css").toString());
        S_CM.setTitle("Cuadro mágico");
        S_CM.setScene(Escena_CM);
        S_CM.show();
    }
}