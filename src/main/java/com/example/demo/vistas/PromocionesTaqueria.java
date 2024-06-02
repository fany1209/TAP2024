package com.example.demo.vistas;

import com.example.demo.componentes.ButtonCell3;
import com.example.demo.modelos.PromocionesDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.kordamp.bootstrapfx.BootstrapFX;
import org.kordamp.bootstrapfx.scene.layout.Panel;

public class PromocionesTaqueria extends Stage {
    private Panel pnlPrincipal;
    private BorderPane bpnPrincipal;
    private ToolBar tlbMenu;
    private Scene escena;
    private Button btnAgregarPromocion;
    private TableView<PromocionesDAO> tbvPromociones;

    public PromocionesTaqueria() {
        CrearUI();
        this.setTitle("Promociones de Taqueria Los Inges");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        ImageView imvEmp = new ImageView(
                getClass().getResource("/imagenes/promo.jpg").toString()
        );
        tbvPromociones = new TableView<>();

        imvEmp.setFitHeight(50);
        imvEmp.setFitWidth(50);
        btnAgregarPromocion = new Button();
        btnAgregarPromocion.setOnAction(event -> new PromocionesForm(tbvPromociones, null));
        btnAgregarPromocion.setPrefSize(30, 30);
        btnAgregarPromocion.setGraphic(imvEmp);
        tlbMenu = new ToolBar(btnAgregarPromocion);

        CrearTabla();

        bpnPrincipal = new BorderPane();
        bpnPrincipal.setTop(tlbMenu);
        bpnPrincipal.setCenter(tbvPromociones);
        pnlPrincipal = new Panel("Promociones de Taquería");
        pnlPrincipal.getStyleClass().add("panel-info");
        pnlPrincipal.setBody(bpnPrincipal);
        escena = new Scene(pnlPrincipal, 600, 400);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    }

    private void CrearTabla() {
        PromocionesDAO objPromocion = new PromocionesDAO();
        tbvPromociones = new TableView<>();

        TableColumn<PromocionesDAO, Integer> tbcIdPromocion = new TableColumn<>("ID");
        tbcIdPromocion.setCellValueFactory(new PropertyValueFactory<>("idPromocion"));

        TableColumn<PromocionesDAO, String> tbcNombrePromocion = new TableColumn<>("Nombre");
        tbcNombrePromocion.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<PromocionesDAO, String> tbcDescripcionPromocion = new TableColumn<>("Descripción");
        tbcDescripcionPromocion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        TableColumn<PromocionesDAO, Double> tbcCostoPromocion = new TableColumn<>("Costo");
        tbcCostoPromocion.setCellValueFactory(new PropertyValueFactory<>("costoPromo"));

        TableColumn<PromocionesDAO, String> tbcFechaInicio = new TableColumn<>("Fecha Inicio");
        tbcFechaInicio.setCellValueFactory(new PropertyValueFactory<>("fechaInicio"));

        TableColumn<PromocionesDAO, String> tbcFechaFin = new TableColumn<>("Fecha Fin");
        tbcFechaFin.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));

        TableColumn<PromocionesDAO, String> tbcEditar = new TableColumn<>("Editar");
        tbcEditar.setCellFactory(
                new Callback<TableColumn<PromocionesDAO, String>, TableCell<PromocionesDAO, String>>() {
                    @Override
                    public TableCell<PromocionesDAO, String> call(TableColumn<PromocionesDAO, String> param) {
                        return new ButtonCell3(1);
                    }
                }
        );

        TableColumn<PromocionesDAO, String> tbcEliminar = new TableColumn<>("Eliminar");
        tbcEliminar.setCellFactory(
                new Callback<TableColumn<PromocionesDAO, String>, TableCell<PromocionesDAO, String>>() {
                    @Override
                    public TableCell<PromocionesDAO, String> call(TableColumn<PromocionesDAO, String> param) {
                        return new ButtonCell3(2);
                    }
                }
        );

        tbvPromociones.getColumns().addAll(tbcIdPromocion, tbcNombrePromocion, tbcDescripcionPromocion,
                tbcCostoPromocion, tbcFechaInicio, tbcFechaFin, tbcEditar, tbcEliminar);
        tbvPromociones.setItems(objPromocion.CONSULTAR());
    }
}
