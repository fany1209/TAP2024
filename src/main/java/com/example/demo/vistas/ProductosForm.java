package com.example.demo.vistas;

import com.example.demo.modelos.ProductosDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ProductosForm extends Stage {
    private TableView<ProductosDAO> tbvProductos;
    private ProductosDAO producto;
    private String[] arPrompts = {"Nombre del producto", "Precio", "Costo", "ID de categoría", "ID de promoción"};
    private Scene scene;
    private TextField[] arTxtCampos = new TextField[5];
    private Button btnGuardar;
    private VBox vBoxPrincipal;

    public ProductosForm(TableView<ProductosDAO> tbvProductos, ProductosDAO producto) {
        this.tbvProductos = tbvProductos;
        this.producto = (producto == null) ? new ProductosDAO() : producto;
        CrearUI();
        this.setTitle("Agregar/Editar Producto");
        this.setScene(scene);
        this.show();
    }

    private void CrearUI() {
        vBoxPrincipal = new VBox();
        vBoxPrincipal.setPadding(new Insets(10));
        vBoxPrincipal.setSpacing(10);
        vBoxPrincipal.setAlignment(Pos.CENTER);

        for (int i = 0; i < arTxtCampos.length; i++) {
            arTxtCampos[i] = new TextField();
            arTxtCampos[i].setPromptText(arPrompts[i]);
            vBoxPrincipal.getChildren().add(arTxtCampos[i]);
        }

        LlenarForm();

        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> GuardarProducto());
        vBoxPrincipal.getChildren().add(btnGuardar);

        scene = new Scene(vBoxPrincipal, 350, 250);
    }

    private void LlenarForm() {
        arTxtCampos[0].setText(producto.getNombre());
        arTxtCampos[1].setText(String.valueOf(producto.getPrecio()));
        arTxtCampos[2].setText(String.valueOf(producto.getCosto()));
        arTxtCampos[3].setText(String.valueOf(producto.getIdCategoria()));
        arTxtCampos[4].setText(String.valueOf(producto.getIdPromocion()));
    }

    private void GuardarProducto() {
        producto.setNombre(arTxtCampos[0].getText());
        producto.setPrecio(Double.parseDouble(arTxtCampos[1].getText()));
        producto.setCosto(Double.parseDouble(arTxtCampos[2].getText()));
        producto.setIdCategoria(Integer.parseInt(arTxtCampos[3].getText()));
        producto.setIdPromocion(Integer.parseInt(arTxtCampos[4].getText()));

        if (producto.getIdProducto() > 0)
            producto.ACTUALIZAR();
        else
            producto.INSERTAR();

        tbvProductos.setItems(producto.CONSULTAR());
        tbvProductos.refresh();

        // Limpiar los campos después de guardar
        for (TextField campo : arTxtCampos) {
            campo.clear();
        }
    }
}
