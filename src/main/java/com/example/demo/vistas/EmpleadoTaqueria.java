package com.example.demo.vistas;


import com.example.demo.componentes.ButtonCell;
import com.example.demo.modelos.EmpleadosDAO;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class EmpleadoTaqueria extends Stage {
    private VBox vbxPrincipal;
    private ToolBar tlbMenu;
    private Scene escena;
    private Button btnAgregarEmpleado;
    private TableView<EmpleadosDAO> tbvEmpleados;


    public EmpleadoTaqueria(){
        CrearUI();
        this.setTitle("Taqueria Los Inges :)");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        ImageView imvEmp= new ImageView(
                getClass().getResource("/imagenes/employee.png").toString()
        );
        tbvEmpleados = new TableView<EmpleadosDAO>();

        imvEmp.setFitHeight(50);
        imvEmp.setFitWidth(50);
        btnAgregarEmpleado = new Button();
        btnAgregarEmpleado.setOnAction(event -> new EmpleadosForm(tbvEmpleados));
        btnAgregarEmpleado.setPrefSize(30,30);
        btnAgregarEmpleado.setGraphic(imvEmp);
        tlbMenu = new ToolBar(btnAgregarEmpleado);

        CrearTable();
        vbxPrincipal = new VBox(tlbMenu,tbvEmpleados);
        escena = new Scene(vbxPrincipal, 300,200);
    }

    private void  CrearTable(){
        EmpleadosDAO objEmp= new EmpleadosDAO();
        tbvEmpleados = new TableView<EmpleadosDAO>();
        TableColumn<EmpleadosDAO,String> tbcNomEmp= new TableColumn<>("Empleado");
        tbcNomEmp.setCellValueFactory(new PropertyValueFactory<>("nomEmpleado"));

        TableColumn<EmpleadosDAO,String> tbcRfcEmp= new TableColumn<>("RFC");
        tbcRfcEmp.setCellValueFactory(new PropertyValueFactory<>("RFCEmpleado"));

        TableColumn<EmpleadosDAO,Float> tbcSueldoEmp= new TableColumn<>("Salario");
        tbcSueldoEmp.setCellValueFactory(new PropertyValueFactory<>("salario"));

        TableColumn<EmpleadosDAO,String> tbcTelEmp= new TableColumn<>("Telefono");
        tbcTelEmp.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        TableColumn<EmpleadosDAO,String> tbcDirEmp= new TableColumn<>("Direccion");
        tbcDirEmp.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        //....
        TableColumn<EmpleadosDAO,String> tbcEditar = new TableColumn<EmpleadosDAO,String>("editar");
        tbcEditar.setCellFactory(
                new Callback<TableColumn<EmpleadosDAO, String>, TableCell<EmpleadosDAO, String>>() {
                    @Override
                    public TableCell<EmpleadosDAO, String> call(TableColumn<EmpleadosDAO, String> empleadosDAOStringTableColumn) {
                        return new ButtonCell();
                    }
                }
        );

        tbvEmpleados.getColumns().addAll(tbcNomEmp,tbcRfcEmp,tbcSueldoEmp,tbcTelEmp,tbcDirEmp,tbcEditar);
        tbvEmpleados.setItems(objEmp.CONSULTAR());
    }
}