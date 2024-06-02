package com.example.demo.vistas;

import com.example.demo.componentes.ButtonCell3;
import com.example.demo.componentes.ButtonCell4;
import com.example.demo.modelos.ServicioDomicilioDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class ServicioDomicilio extends Stage {
    private Panel pnlPrincipal;
    private BorderPane bpnPrincipal;
    private ToolBar tlbMenu;
    private Scene escena;
    private Button btnAgregarServicio;
    private TableView<ServicioDomicilioDAO> tbvServicios;

    public ServicioDomicilio() {
        CrearUI();
        this.setTitle("Servicio de Domicilio de Taquería Los Inges");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        ImageView imvEmp = new ImageView(
                getClass().getResource("/imagenes/servicio.jpg").toString()
        );
        tbvServicios = new TableView<>();

        imvEmp.setFitHeight(50);
        imvEmp.setFitWidth(50);
        btnAgregarServicio = new Button();
        btnAgregarServicio.setOnAction(event -> new ServicioDomicilioForm(tbvServicios, null));
        btnAgregarServicio.setPrefSize(30, 30);
        btnAgregarServicio.setGraphic(imvEmp);
        tlbMenu = new ToolBar(btnAgregarServicio);

        CrearTabla();

        bpnPrincipal = new BorderPane();
        bpnPrincipal.setTop(tlbMenu);
        bpnPrincipal.setCenter(tbvServicios);
        pnlPrincipal = new Panel("Servicio de Domicilio de Taquería");
        pnlPrincipal.getStyleClass().add("panel-info");
        pnlPrincipal.setBody(bpnPrincipal);
        escena = new Scene(pnlPrincipal, 600, 400);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    }

    private void CrearTabla() {
        ServicioDomicilioDAO objServicio = new ServicioDomicilioDAO();
        tbvServicios = new TableView<>();

        TableColumn<ServicioDomicilioDAO, Integer> tbcIdServicio = new TableColumn<>("ID");
        tbcIdServicio.setCellValueFactory(new PropertyValueFactory<>("idServicio"));

        TableColumn<ServicioDomicilioDAO, String> tbcFecha = new TableColumn<>("Fecha");
        tbcFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));

        TableColumn<ServicioDomicilioDAO, String> tbcDireccionEntrega = new TableColumn<>("Dirección de Entrega");
        tbcDireccionEntrega.setCellValueFactory(new PropertyValueFactory<>("direccionEntrega"));

        TableColumn<ServicioDomicilioDAO, Double> tbcCosto = new TableColumn<>("Costo");
        tbcCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));

        TableColumn<ServicioDomicilioDAO, String> tbcObservaciones = new TableColumn<>("Observaciones");
        tbcObservaciones.setCellValueFactory(new PropertyValueFactory<>("observaciones"));

        TableColumn<ServicioDomicilioDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(
                new Callback<TableColumn<ServicioDomicilioDAO, String>, TableCell<ServicioDomicilioDAO, String>>() {
                    @Override
                    public TableCell<ServicioDomicilioDAO, String> call(TableColumn<ServicioDomicilioDAO, String> param) {
                        return new ButtonCell4(1);
                    }
                }
        );

        TableColumn<ServicioDomicilioDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(
                new Callback<TableColumn<ServicioDomicilioDAO, String>, TableCell<ServicioDomicilioDAO, String>>() {
                    @Override
                    public TableCell<ServicioDomicilioDAO, String> call(TableColumn<ServicioDomicilioDAO, String> param) {
                        return new ButtonCell4(2);
                    }
                }
        );

        tbvServicios.getColumns().addAll(tbcIdServicio, tbcFecha, tbcDireccionEntrega,
                tbcCosto, tbcObservaciones, tbcEditar, tbcEliminar);
        tbvServicios.setItems(objServicio.CONSULTAR());
    }
}

