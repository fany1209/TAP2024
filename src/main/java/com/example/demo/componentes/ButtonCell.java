package com.example.demo.componentes;

import com.example.demo.modelos.EmpleadosDAO;
import com.example.demo.vistas.EmpleadosForm;
import javafx.scene.control.*;

import java.util.Optional;

public class ButtonCell extends TableCell<EmpleadosDAO,String> {
    Button btnCelda;
    int opc;
    EmpleadosDAO objEmp;
    public ButtonCell(int opc)
    {
        this.opc=opc;
        String txtButton = (opc==1)? "Editar" : "Eliminar";
        btnCelda = new Button(txtButton);
        btnCelda.setOnAction(event -> AccionBoton(opc));

    }
    private void AccionBoton(int opc){
        TableView<EmpleadosDAO> tbvEmpleados = ButtonCell.this.getTableView();
        objEmp = tbvEmpleados.getItems().get(ButtonCell.this.getIndex());
        if (opc == 1) {
            //codigo editar
            new EmpleadosForm(tbvEmpleados, objEmp);

        }else {
            Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("mensaje de sistema");
            alert.setHeaderText("CONFIRMACION");
            alert.setContentText("¿Desea borrar el Empleado: "+objEmp.getNomEmpleado());
            Optional<ButtonType> result  = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                objEmp.ELIMINAR();
                tbvEmpleados.setItems(objEmp.CONSULTAR());
                tbvEmpleados.refresh();

            }

        }
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item,empty);
        if (!empty)
            this.setGraphic(btnCelda);
        }
    }

