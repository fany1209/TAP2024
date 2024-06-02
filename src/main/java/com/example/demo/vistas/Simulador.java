package com.example.demo.vistas;

import com.example.demo.componentes.Impresion_tarea;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Simulador extends Stage {

    private TableView<Impresion_tarea> tablaDeTareas;
    private ProgressBar barraDeProgreso;
    private Button agregarTareaBtn, controlarSimuladorBtn;
    private ObservableList<Impresion_tarea> listaDeTareas;
    private Hilo hiloDelSimulador;
    private boolean simuladorActivo;
    private TableColumn<Impresion_tarea, String> numeroDeArchivoCol, nombreCol, horaDeAccesoCol;
    private TableColumn<Impresion_tarea, Integer> hojasCol;
    private Random aleatorio;
    private SimpleDateFormat formatoDeFecha;

    public Simulador() {
        inicializarComponentes();
        configurarUI();
        this.setTitle("Simulador de impresión");
        Scene scene = new Scene(crearLayout());
        scene.getStylesheets().add(getClass().getResource("/estilos/simulador.css").toString()); // Asegúrate de que la ruta sea correcta
        this.setScene(scene);
        this.setWidth(800);
        this.setHeight(600);
        this.show();
    }

    private void inicializarComponentes() {
        tablaDeTareas = new TableView<>();
        listaDeTareas = FXCollections.observableArrayList();
        barraDeProgreso = new ProgressBar(0);
        agregarTareaBtn = new Button("Agregar tarea");
        controlarSimuladorBtn = new Button("Iniciar simulador");
        hiloDelSimulador = new Hilo();
        simuladorActivo = false;
    }

    private void configurarUI() {
        tablaDeTareas.setItems(listaDeTareas);

        numeroDeArchivoCol = new TableColumn<>("Número de archivo");
        numeroDeArchivoCol.setCellValueFactory(new PropertyValueFactory<>("numeroDeArchivo"));

        nombreCol = new TableColumn<>("Nombre de archivo");
        nombreCol.setCellValueFactory(new PropertyValueFactory<>("nombreDeArchivo"));

        hojasCol = new TableColumn<>("Número de hojas");
        hojasCol.setCellValueFactory(new PropertyValueFactory<>("numeroDeHojas"));

        horaDeAccesoCol = new TableColumn<>("Hora de acceso");
        horaDeAccesoCol.setCellValueFactory(new PropertyValueFactory<>("horaDeAcceso"));

        tablaDeTareas.getColumns().addAll(numeroDeArchivoCol, nombreCol, hojasCol, horaDeAccesoCol);

        agregarTareaBtn.setOnAction(event -> agregarTarea());
        controlarSimuladorBtn.setOnAction(event -> control());


    }

    private VBox crearLayout() {
        return new VBox(tablaDeTareas, barraDeProgreso, agregarTareaBtn, controlarSimuladorBtn);
    }

    private void agregarTarea() {
        aleatorio = new Random();
        formatoDeFecha = new SimpleDateFormat("yyyyMMddHHmmss");

        String nombreDeArchivo = "Archivo_" + formatoDeFecha.format(new Date()) + ".txt";
        int numeroDeHojas = aleatorio.nextInt(50) + 1;
        String horaDeAcceso = new SimpleDateFormat("HH:mm:ss").format(new Date());

        Impresion_tarea tarea = new Impresion_tarea(
                String.valueOf(listaDeTareas.size() + 1),
                nombreDeArchivo,
                numeroDeHojas,
                horaDeAcceso
        );

        listaDeTareas.add(tarea);
        tablaDeTareas.refresh();
    }

    private void control() {
        if (simuladorActivo) {
            detener();
        } else {
            iniciar();
        }
    }

    private void detener() {
        simuladorActivo = false;
        controlarSimuladorBtn.setText("Iniciar simulador");
        if (hiloDelSimulador != null) {
            hiloDelSimulador.interrupt();
        }
    }

    private void iniciar() {
        simuladorActivo = true;
        controlarSimuladorBtn.setText("Detener simulador");
        hiloDelSimulador = new Hilo();
        hiloDelSimulador.start();
    }

    private class Hilo extends Thread {
        @Override
        public void run() {
            while (!Thread.interrupted()) {
                if (!listaDeTareas.isEmpty() && simuladorActivo) {
                    procesarTarea(listaDeTareas.get(0));
                }
            }
        }

        private void procesarTarea(Impresion_tarea tareaActual) {
            for (int i = 0; i < tareaActual.getNumeroDeHojas(); i++) {
                try {
                    Thread.sleep(100);
                    actualizarProgreso(tareaActual, i + 1);
                } catch (InterruptedException e) {
                    break;
                }
            }
            Platform.runLater(() -> listaDeTareas.remove(tareaActual));
        }

        private void actualizarProgreso(Impresion_tarea tareaActual, int hojasProcesadas) {
            final double progreso = hojasProcesadas / (double) tareaActual.getNumeroDeHojas();
            Platform.runLater(() -> barraDeProgreso.setProgress(progreso));
        }
    }
}
