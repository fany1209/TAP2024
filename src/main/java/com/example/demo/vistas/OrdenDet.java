package com.example.demo.vistas;

import com.example.demo.modelos.OrdenDetDAO;

import com.example.demo.modelos.OrdenesDAO;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OrdenDet extends Stage {
    private TableView<OrdenDetDAO> tablaDetalleOrden;
    private Button btnAgregarDetalle;
    private Button btnEliminarDetalle;
    private VBox vBoxPrincipal;

    public OrdenDet(ObservableList<OrdenDetDAO> detalles) {
        crearUI(detalles);
        this.setTitle("Detalle de Orden");
        this.setScene(new Scene(vBoxPrincipal, 600, 400));
        this.show();
    }

    private void crearUI(ObservableList<OrdenDetDAO> detalles) {
        tablaDetalleOrden = new TableView<>();
        tablaDetalleOrden.setItems(detalles);

        TableColumn<OrdenDetDAO, Integer> colIdOrden = new TableColumn<>("ID Orden");
        colIdOrden.setCellValueFactory(new PropertyValueFactory<>("idOrden"));

        TableColumn<OrdenDetDAO, Integer> colIdProducto = new TableColumn<>("ID Producto");
        colIdProducto.setCellValueFactory(new PropertyValueFactory<>("idProducto"));

        TableColumn<OrdenDetDAO, Integer> colCantidad = new TableColumn<>("Cantidad");
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        TableColumn<OrdenDetDAO, Double> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<OrdenDetDAO, Integer> colIdPromocion = new TableColumn<>("ID Promoción");
        colIdPromocion.setCellValueFactory(new PropertyValueFactory<>("idPromocion"));

        TableColumn<OrdenDetDAO, Integer> colIdMesa = new TableColumn<>("ID Mesa");
        colIdMesa.setCellValueFactory(new PropertyValueFactory<>("idMesa"));

        tablaDetalleOrden.getColumns().addAll(colIdOrden, colIdProducto, colCantidad, colPrecio, colIdPromocion, colIdMesa);

        HBox hBoxBotones = new HBox(10);
        hBoxBotones.getChildren().addAll(btnAgregarDetalle, btnEliminarDetalle);
        hBoxBotones.setAlignment(Pos.CENTER);

        vBoxPrincipal = new VBox(10);
        vBoxPrincipal.setPadding(new Insets(10));
        vBoxPrincipal.getChildren().addAll(tablaDetalleOrden, hBoxBotones);
    }
}
