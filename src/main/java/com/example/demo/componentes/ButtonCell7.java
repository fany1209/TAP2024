package com.example.demo.componentes;

import com.example.demo.modelos.OrdenesDAO;
import com.example.demo.vistas.OrdenForm;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.util.Callback;

import java.util.Optional;

public class ButtonCell7 extends TableCell<OrdenesDAO, String> {
    private final Button btnCell;
    private final int option;
    private OrdenesDAO objorder;

    public ButtonCell7(int option) {
        this.option = option;
        String buttonText = (option == 1) ? "Editar" : "Eliminar";
        btnCell = new Button(buttonText);
        btnCell.setOnAction(event -> buttonAction(option));
    }

    private void buttonAction(int option) {
        TableView<OrdenesDAO> tableView = ButtonCell7.this.getTableView();
        objorder = tableView.getItems().get(ButtonCell7.this.getIndex());

        if (option == 1) {
            // Edit
            new OrdenForm(tableView, objorder);
        } else {
            // Delete
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Delete Order");
            alert.setContentText("Are you sure you want to delete order ID " + objorder.getIdOrden() + "?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                objorder.ELIMINAR();
                tableView.setItems(objorder.CONSULTAR());
                tableView.refresh();
            }
        }
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setGraphic(btnCell);
        }
    }
}
