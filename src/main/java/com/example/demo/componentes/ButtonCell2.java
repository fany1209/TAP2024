package com.example.demo.componentes;

import com.example.demo.modelos.CategoriasDAO;

import com.example.demo.vistas.CategoriasForm;

import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ButtonCell2 extends TableCell<CategoriasDAO, String> {
    Button button;
int opc;
    CategoriasDAO objCategoria;
    public ButtonCell2(int opc) {
        this.opc = opc;
        String buttonText = (opc == 1) ? "Editar" : "Eliminar";
        button = new Button(buttonText);
        button.setOnAction(event -> handleButtonAction(opc));
    }

    private void handleButtonAction(int opc) {
        TableView<CategoriasDAO> tbvCategoria = ButtonCell2.this.getTableView();
        objCategoria = tbvCategoria.getItems().get(ButtonCell2.this.getIndex());
        if (opc == 1) {
            //codigo editar
            new CategoriasForm(tbvCategoria, objCategoria);

        }else {
            Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("mensaje de sistema");
            alert.setHeaderText("CONFIRMACION");
            alert.setContentText("¿Desea borrar la categoria: "+objCategoria.getIdCategoria());
            Optional<ButtonType> result  = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                objCategoria.ELIMINAR();
                tbvCategoria.setItems(objCategoria.CONSULTAR());
                tbvCategoria.refresh();

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
