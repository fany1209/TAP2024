package com.example.demo.vistas;

import com.example.demo.modelos.EmpleadosDAO;
import com.example.demo.modelos.MesasDAO;
import com.example.demo.modelos.OrdenDetDAO;
import com.example.demo.modelos.ProductosDAO;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class MenuTaqueria extends Stage {

    private Map<Button, String> categoriaMap;


    public MenuTaqueria() {
        crearUI();
        Scene scene = new Scene(crearUI(), 800, 600); // Ajusta el tamaño de la escena según tus necesidades

        this.setTitle("Menú Taquería");
        this.setScene(scene);

        this.show();
    }

    private VBox crearUI() {
        GridPane gridPane = new GridPane();

        Button loginButton = new Button("Login");


        gridPane.setAlignment(Pos.TOP_CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        // Crear botones para cada categoría
        Button tortasButton = crearBoton("Tortas", "/imagenes/tortas.jpg");
        Button tacosButton = crearBoton("Tacos", "/imagenes/tacos.jpg");
        Button especialidadesButton = crearBoton("Especialidades", "/imagenes/especialidades.jpg");
        Button bebidasButton = crearBoton("Bebidas", "/imagenes/refrescos.jpg");
        Button mesasButton = crearBoton("Mesas", "/imagenes/mesas.jpg");
        Button empleadosButton = crearBoton("Empleados", "/imagenes/employee.png");


        // Agregar botones al gridPane
        gridPane.add(loginButton, 0, 0);
        gridPane.add(empleadosButton, 1, 0);
        gridPane.add(mesasButton, 2, 0);
        gridPane.add(tortasButton, 3, 0);
        gridPane.add(tacosButton, 4, 0);
        gridPane.add(especialidadesButton, 5, 0);
        gridPane.add(bebidasButton, 6, 0);

        // Crear una tabla para mostrar los productos
        TableView<ProductosDAO> tablaProductos = new TableView<>();
        tablaProductos.setPrefSize(200, 200);

        // Crear columnas para la tabla
        TableColumn<ProductosDAO, String> columnaNombre = new TableColumn<>("Nombre");
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        TableColumn<ProductosDAO, Double> columnaPrecio = new TableColumn<>("Precio");
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        // Agregar las columnas a la tabla
        tablaProductos.getColumns().addAll(columnaNombre, columnaPrecio);

        TableView<OrdenDetDAO> tablaOrden = new TableView<>();
        tablaOrden.setPrefSize(200, 200);
        TableColumn<OrdenDetDAO, Integer> columnaIdOrden = new TableColumn<>("ID Orden");
        columnaIdOrden.setCellValueFactory(new PropertyValueFactory<>("idOrden"));

        TableColumn<OrdenDetDAO, Integer> columnaIdProducto = new TableColumn<>("ID Producto");
        columnaIdProducto.setCellValueFactory(new PropertyValueFactory<>("idProducto"));

        TableColumn<OrdenDetDAO, Integer> columnaCantidad = new TableColumn<>("Cantidad");
        columnaCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        TableColumn<OrdenDetDAO, Double> columnaPrecios = new TableColumn<>("Precio");
        columnaPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));

        TableColumn<OrdenDetDAO, Integer> columnaIdPromocion = new TableColumn<>("ID Promoción");
        columnaIdPromocion.setCellValueFactory(new PropertyValueFactory<>("idPromocion"));

        TableColumn<OrdenDetDAO, Integer> columnaIdMesa = new TableColumn<>("ID Mesa");
        columnaIdMesa.setCellValueFactory(new PropertyValueFactory<>("idMesa"));

// Agregar las columnas a la tabla de orden
        tablaOrden.getColumns().addAll(columnaIdOrden, columnaIdProducto, columnaCantidad, columnaPrecios, columnaIdPromocion, columnaIdMesa);


        // Crear una tabla para mostrar los productosTableView<ProductosDAO> tablaProductos = new TableView<>();tablaProductos.setPrefSize(200, 200);


        // Crear un VBox para contener los botones y la tabla
        VBox root = new VBox(20);
        root.getChildren().addAll(gridPane, tablaProductos,tablaOrden);
        root.setAlignment(Pos.TOP_CENTER);




        // Asignar la acción para mostrar productos cuando se haga clic en un botón
        tortasButton.setOnAction(event -> mostrarProductosDeCategoria("Tortas", tablaProductos));
        tacosButton.setOnAction(event -> mostrarProductosDeCategoria("Tacos", tablaProductos));
        especialidadesButton.setOnAction(event -> mostrarProductosDeCategoria("Especialidades", tablaProductos));
        bebidasButton.setOnAction(event -> mostrarProductosDeCategoria("Bebidas", tablaProductos));
        empleadosButton.setOnAction(event -> abrirVentanaDeEmpleados());
        mesasButton.setOnAction(event -> abrirVentanaDeMesas());



        return root;
    }

    private void abrirVentanaDeEmpleados() {
        Stage ventanaEmpleados = new Stage();
        ventanaEmpleados.setTitle("Lista de Empleados");

        TableView<EmpleadosDAO> tablaEmpleados = new TableView<>();
        TableColumn<EmpleadosDAO, String> columnaNombre = new TableColumn<>("Empleado");
        columnaNombre.setCellValueFactory(new PropertyValueFactory<>("nomEmpleado"));

        TableColumn<EmpleadosDAO, String> columnaRFC = new TableColumn<>("RFC");
        columnaRFC.setCellValueFactory(new PropertyValueFactory<>("RFCEmpleado"));

        TableColumn<EmpleadosDAO, Float> columnaSalario = new TableColumn<>("Salario");
        columnaSalario.setCellValueFactory(new PropertyValueFactory<>("salario"));

        TableColumn<EmpleadosDAO, String> columnaTelefono = new TableColumn<>("Teléfono");
        columnaTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        TableColumn<EmpleadosDAO, String> columnaDireccion = new TableColumn<>("Dirección");
        columnaDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));

        tablaEmpleados.getColumns().addAll(columnaNombre, columnaRFC, columnaSalario,columnaTelefono,columnaDireccion/* Agrega las columnas adicionales aquí */);

        EmpleadosDAO empleadoDAO = new EmpleadosDAO();
        ObservableList<EmpleadosDAO> empleados = empleadoDAO.CONSULTAR();
        tablaEmpleados.setItems(empleados);

        VBox layout = new VBox(10);
        layout.getChildren().add(tablaEmpleados);
        Scene scene = new Scene(layout, 400, 300);
        ventanaEmpleados.setScene(scene);
        ventanaEmpleados.show();
    }

    private void abrirVentanaDeMesas() {
        Stage ventanaMesas = new Stage();
        ventanaMesas.setTitle("Mesas Disponibles");

        TableView<MesasDAO> tablaMesas = new TableView<>();
        TableColumn<MesasDAO, Integer> columnaIdMesa = new TableColumn<>("ID Mesa");
        columnaIdMesa.setCellValueFactory(new PropertyValueFactory<>("idMesa"));

        TableColumn<MesasDAO, Integer> columnaNumero = new TableColumn<>("Número de Mesa");
        columnaNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));

        TableColumn<MesasDAO, Integer> columnaEstado = new TableColumn<>("Estado");
        columnaEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));

        tablaMesas.getColumns().addAll(columnaIdMesa, columnaNumero,columnaEstado
        );

        MesasDAO mesasDAO = new MesasDAO();
        ObservableList<MesasDAO> mesas = mesasDAO.CONSULTAR();
        tablaMesas.setItems(mesas);

        VBox layout = new VBox(10);
        layout.getChildren().add(tablaMesas);
        Scene scene = new Scene(layout, 400, 300);
        ventanaMesas.setScene(scene);
        ventanaMesas.show();
    }



    private Button crearBoton(String texto, String imagePath) {
        Button button = new Button(texto);
        button.setPrefSize(100, 100);

        // Cargar la imagen y establecerla como gráfico del botón
        Image imagen = new Image(getClass().getResourceAsStream(imagePath));
        ImageView imageView = new ImageView(imagen);
        imageView.setFitWidth(100); // Ajustar el ancho de la imagen
        imageView.setFitHeight(100);
        button.setGraphic(imageView);


        return button;
    }

    private void mostrarProductosDeCategoria(String categoria, TableView<ProductosDAO> tablaProductos) {
        // Obtener los productos de la categoría seleccionada
        ProductosDAO productosDAO = new ProductosDAO();
        ObservableList<ProductosDAO> productos = productosDAO.obtenerProductosPorCategoria(categoria);

        // Agregar los productos a la tabla
        tablaProductos.setItems(productos);

    }


}
