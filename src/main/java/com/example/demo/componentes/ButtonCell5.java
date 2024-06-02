package com.example.demo.componentes;

import com.example.demo.modelos.ProductosDAO;
import com.example.demo.vistas.ProductosForm;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import java.util.Optional;

public class ButtonCell5 extends TableCell<ProductosDAO, String> {
    private Button button;
    private int opc;
    private ProductosDAO objProducto;

    public ButtonCell5(int opc) {
        this.opc = opc;
        String buttonText = (opc == 1) ? "Editar" : "Eliminar";
        button = new Button(buttonText);
        button.setOnAction(event -> handleButtonAction(opc));
    }

    private void handleButtonAction(int opc) {
        TableView<ProductosDAO> tbvProductos = ButtonCell5.this.getTableView();
        objProducto = tbvProductos.getItems().get(ButtonCell5.this.getIndex());
        if (opc == 1) {
            // Código para editar
            new ProductosForm(tbvProductos, objProducto);
        } else {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Mensaje de sistema");
            alert.setHeaderText("Confirmación");
            alert.setContentText("¿Desea borrar el producto: " + objProducto.getNombre() + "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                objProducto.ELIMINAR();
                tbvProductos.setItems(objProducto.CONSULTAR());
                tbvProductos.refresh();
            }
        }
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setGraphic(button);
        } else {
            setGraphic(null);
        }
    }
}
