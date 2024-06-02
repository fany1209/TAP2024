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
    private MenuItem mitCalculadora,mitMemorama, mitCuadroMagico,mitEmpleadoTaqueria,mitPista,mitcat,mitpromo,
            mitSD,mitEncuentraLaPareja,mitSimulador,mitMenutaq,mitSalir;
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

        mitEncuentraLaPareja = new MenuItem("Encuentra La Pareja");
        mitEncuentraLaPareja.setOnAction(actionEvent -> new EncuentraLaPareja());




        menParcial1 = new Menu("primer parcial") ;
        menParcial1.getItems().addAll(mitCalculadora);
        menParcial1.getItems().addAll(mitMemorama);
        menParcial1.getItems().addAll(mitCuadroMagico);
        menParcial1.getItems().addAll(mitEmpleadoTaqueria);
        menParcial1.getItems().addAll(mitEncuentraLaPareja);

        menuParcial2= new Menu("segundo parcial");
        mitPista = new MenuItem("hilos");
        mitcat = new MenuItem("CategoriaEmpleado");
        mitpromo = new MenuItem("PromocionesTaqueria");
        mitSD = new MenuItem("Servicio a domomcilio");
        mitMenutaq = new MenuItem("menu taqueria");
        mitSimulador = new MenuItem("Simulador");


        mitPista.setOnAction(event -> new Pista());
        menuParcial2.getItems().addAll(mitPista);

        mitcat.setOnAction(event -> new CategoriasTaqueria());
        menuParcial2.getItems().addAll(mitcat);

        mitpromo.setOnAction(event -> new PromocionesTaqueria());
        menuParcial2.getItems().addAll(mitpromo);

        mitSD.setOnAction(event -> new ServicioDomicilio());
        menuParcial2.getItems().addAll(mitSD);

        mitMenutaq.setOnAction(event -> new MenuTaqueria());
        menuParcial2.getItems().addAll(mitMenutaq);

        mitSimulador.setOnAction(event -> new Simulador());
        menuParcial2.getItems().addAll(mitSimulador);


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