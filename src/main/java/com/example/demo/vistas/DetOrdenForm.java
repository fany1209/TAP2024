package com.example.demo.vistas;

import com.example.demo.modelos.OrdenDetDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DetOrdenForm extends Stage {
    private TextField txtIdOrden;
    private TextField txtIdProducto;
    private TextField txtCantidad;
    private TextField txtPrecio;
    private TextField txtIdPromocion;
    private TextField txtIdMesa;
    private Button btnGuardar;

    public DetOrdenForm() {
        initComponents();
        createLayout();
        this.setTitle("Detalle de Orden");
    }

    private void initComponents() {
        txtIdOrden = new TextField();
        txtIdOrden.setPromptText("ID Orden");
        txtIdProducto = new TextField();
        txtIdProducto.setPromptText("ID Producto");
        txtCantidad = new TextField();
        txtCantidad.setPromptText("Cantidad");
        txtPrecio = new TextField();
        txtPrecio.setPromptText("Precio");
        txtIdPromocion = new TextField();
        txtIdPromocion.setPromptText("ID Promoción");
        txtIdMesa = new TextField();
        txtIdMesa.setPromptText("ID Mesa");
        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> guardarDetalleOrden());
    }

    private void createLayout() {
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10));
        vBox.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(
                new Label("ID Orden:"), txtIdOrden,
                new Label("ID Producto:"), txtIdProducto,
                new Label("Cantidad:"), txtCantidad,
                new Label("Precio:"), txtPrecio,
                new Label("ID Promoción:"), txtIdPromocion,
                new Label("ID Mesa:"), txtIdMesa,
                btnGuardar
        );

        Scene scene = new Scene(vBox, 300, 400);
        this.setScene(scene);
    }

    private void guardarDetalleOrden() {
        OrdenDetDAO detalleOrden = new OrdenDetDAO();
        detalleOrden.setIdOrden(Integer.parseInt(txtIdOrden.getText()));
        detalleOrden.setIdProducto(Integer.parseInt(txtIdProducto.getText()));
        detalleOrden.setCantidad(Integer.parseInt(txtCantidad.getText()));
        detalleOrden.setPrecio(Double.parseDouble(txtPrecio.getText()));
        detalleOrden.setIdPromocion(Integer.parseInt(txtIdPromocion.getText()));
        detalleOrden.setIdMesa(Integer.parseInt(txtIdMesa.getText()));

        // Lógica para guardar el detalle de la orden en la base de datos o en una estructura de datos

        // Después de guardar, limpiamos los campos
        txtIdOrden.clear();
        txtIdProducto.clear();
        txtCantidad.clear();
        txtPrecio.clear();
        txtIdPromocion.clear();
        txtIdMesa.clear();
    }
}
