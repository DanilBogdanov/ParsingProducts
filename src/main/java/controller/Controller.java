package controller;


import entity.Company;
import entity.PivotProductFX;
import entity.Product;
import entity.ProductPropertyToDel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import service.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Controller {
    @FXML
    private Label labelId;
    @FXML
    private Button pivotButton;
    @FXML
    private Button bagiraButton;
    @FXML
    private Button petshopButton;
    @FXML
    private Button vetnaButton;
    @FXML
    private Button vetnaParsingButton;
    @FXML
    TableView tableView;
    @FXML
    TableView<ProductPropertyToDel> testTableView;

    private final Service service = new Service();


    @FXML
    private void clickPivotButton() {
        try {
            //get pivotProducts from db and clear table
            List<PivotProductFX> pivotProductsFX = service.getPivotProducts();
            ObservableList<PivotProductFX> observableList = FXCollections.observableList(pivotProductsFX);
            tableView.getColumns().clear();

            //name column
            TableColumn<PivotProductFX, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(cell -> cell.getValue().nameProperty());
            tableView.getColumns().add(nameColumn);

            //Bagira price
            TableColumn<PivotProductFX, Double> bagiraPriceColumn = new TableColumn<>("B.price");
            bagiraPriceColumn.setCellValueFactory(cell -> cell.getValue().bagiraPriceProperty().asObject());
            tableView.getColumns().add(bagiraPriceColumn);

            //Petshop price
            TableColumn<PivotProductFX, Double> petshopPriceColumn = new TableColumn<>("P.price");
            petshopPriceColumn.setCellValueFactory(cell -> cell.getValue().petshopPriceProperty().asObject());
            tableView.getColumns().add(petshopPriceColumn);

            //Vetna price
            TableColumn<PivotProductFX, Double> vetnaPriceColumn = new TableColumn<>("V.price");
            vetnaPriceColumn.setCellValueFactory(cell -> cell.getValue().vetnaPriceProperty().asObject());
            tableView.getColumns().add(vetnaPriceColumn);

            tableView.setItems(observableList);




        } catch (SQLException sqlException) {
            sendMessage("ошибка загрузки данных из базы данных\n" +sqlException.getMessage());
        }
    }



    @FXML
    private void clickBagiraButton() {
        setProductsToViewList(Company.Bagira, true, true, true, true,
                true, true, true);
    }

    @FXML
    private void clickPetshopButton() {
        setProductsToViewList(Company.PetShop, true, true, true, true,
                true, true, true);
    }

    @FXML
    private void clickVetnaButton() {
        setProductsToViewList(Company.Vetna, true, true, true, true,
                true, true, true);
    }

    @FXML
    private void clickVetnaParsingButton() {
        service.parsingVetnaToDB();
    }

    @FXML
    private void clickTestButton(){
        List<ProductPropertyToDel> list = new ArrayList<>();
        ProductPropertyToDel productProperty = new ProductPropertyToDel("name", 10);
        list.add(productProperty);
        ObservableList<ProductPropertyToDel> observableList = FXCollections.observableList(list);
        testTableView.setItems(observableList);

        testTableView.getColumns().clear();
        //lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        TableColumn<ProductPropertyToDel, String> nameColumn = new TableColumn<>("=name=");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        TableColumn<ProductPropertyToDel, Integer> priceColumn = new TableColumn<>("=price=");
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        testTableView.getColumns().add(nameColumn);
        testTableView.getColumns().add(priceColumn);

    }

    @FXML
    private void clickTestButton1() {

    }

    private void setProductsToViewList(Company company, boolean hasCode, boolean hasBrandName, boolean hasName,
                                       boolean hasWeight, boolean hasCurrentPrice, boolean hasSalePrice,
                                       boolean hasParsingDate) {
        try {
            //get products from service and clear tableView
            List<Product> products = service.getProductsByCompany(company);
            ObservableList<Product> observableList = FXCollections.observableList(products);
            tableView.getColumns().clear();

            //code
            if (hasCode) {
                TableColumn<Product, Integer> codeColumn = new TableColumn<>("Code");
                codeColumn.setCellValueFactory(new PropertyValueFactory<Product, Integer>("code"));
                tableView.getColumns().add(codeColumn);
            }

            //brandName
            if (hasBrandName) {
                TableColumn<Product, String> brandColumn = new TableColumn<>("Brand Name");
                brandColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("brandName"));
                tableView.getColumns().add(brandColumn);
            }

            //name
            if (hasName) {
                TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
                nameColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("name" ));
                tableView.getColumns().add(nameColumn);
            }

            //weight
            if (hasWeight) {
                TableColumn<Product, Double> weightColumn = new TableColumn<>("Weight");
                weightColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("weight"));
                tableView.getColumns().add(weightColumn);
            }

            //current Price
            if (hasCurrentPrice) {
                TableColumn<Product, Double> priceColumn = new TableColumn<>("Current Price");
                priceColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("currentPrice"));
                tableView.getColumns().add(priceColumn);
            }

            //sale Price
            if (hasSalePrice) {
                TableColumn<Product, Double> saleColumn = new TableColumn<>("Sale Price");
                saleColumn.setCellValueFactory(new PropertyValueFactory<Product, Double>("salePrice"));
                tableView.getColumns().add(saleColumn);
            }

            //parsing date
            if (hasParsingDate) {
                TableColumn<Product, String> dateColumn = new TableColumn<>("Parsing Date");
                dateColumn.setCellValueFactory(new PropertyValueFactory<Product, String>("parsingDate"));
                tableView.getColumns().add(dateColumn);
            }

            tableView.setItems(observableList);
        } catch (SQLException e) {
            sendMessage(e.getMessage());
        }
    }

    private void sendMessage(String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(text);

        alert.showAndWait();
    }

    @FXML
    private void clickedItem() {
        Object product = tableView.getSelectionModel().getSelectedItem();
        int i = tableView.getSelectionModel().getFocusedIndex();
        //Product product =
        labelId.setText(product.toString());
        //product.setBrandName("test");
    }

}
