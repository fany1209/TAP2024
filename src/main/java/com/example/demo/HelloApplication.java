package com.example.demo;

import com.example.demo.modelos.Conexion;
import com.example.demo.vistas.*;
//import com.example.demo.vistas.Memorama;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {
    private MenuBar mnbPrincipal;
    private Menu menParcial1, menuParcial2, menuSalir;
    private MenuItem mitCalculadora,mitMemorama, mitCuadroMagico,mitEmpleadoTaqueria,mitPista, mitSalir;
    private BorderPane bdpPanel;



    @Override
    public void start(Stage stage) throws IOException {
        Crearmenu ();
       // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        bdpPanel = new BorderPane();
        bdpPanel.setTop(mnbPrincipal);
        Scene scene = new Scene(bdpPanel);
        scene.getStylesheets().add(getClass().getResource("/estilos/main.css").toString());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        stage.setMaximized(true);
        //new Calculadora();

        Conexion.crearConexion();

    }

    private void Crearmenu() {

        mitCalculadora = new MenuItem("calculadora");
        mitCalculadora.setOnAction(actionEvent -> new Calculadora());

        mitMemorama = new MenuItem("memorama");
       mitMemorama.setOnAction(actionEvent -> new Memorama());

        mitCuadroMagico = new MenuItem("Cuadro Magico");
        mitCuadroMagico.setOnAction(actionEvent -> new CuadroMagico());

        mitEmpleadoTaqueria = new MenuItem("Empleado taqueria");
        mitEmpleadoTaqueria.setOnAction(actionEvent -> new EmpleadoTaqueria());


        menParcial1 = new Menu("primer parcial") ;
        menParcial1.getItems().addAll(mitCalculadora);
        menParcial1.getItems().addAll(mitMemorama);
        menParcial1.getItems().addAll(mitCuadroMagico);
        menParcial1.getItems().addAll(mitEmpleadoTaqueria);

        menuParcial2= new Menu("segundo parcial");
        mitPista = new MenuItem("hilos");
        mitPista.setOnAction(event -> new Pista());
        menuParcial2.getItems().addAll(mitPista);

        mitSalir= new MenuItem("salir");
        menuSalir = new Menu("salir");
        menuSalir.getItems().add(mitSalir);
        menuSalir.setOnAction(actionEvent -> System.exit(0));

        mnbPrincipal = new MenuBar();
        mnbPrincipal.getMenus().addAll(menParcial1,menuParcial2, menuSalir);
    }

    public static void main(String[] args) {
        launch();
    }
}