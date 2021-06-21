package service;

import dao.DBService;
import entity.Company;
import entity.PivotProductFX;
import entity.Product;
import parser.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Service {
    private DBService dbService = new DBService();
    private List<Parser> parsers = new ArrayList<>();

    public Service() {
        parsers.add(new VetnaParser());
        parsers.add(new PetshopParser());
        parsers.add(new BagiraParser());
    }

    public void parsingToDB() {
        for (Parser parser : parsers) {
            List<Product> products = parser.parsing();
            try {
                dbService.putProducts(products);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public void parsingVetnaToDB() {
        List<Product> products = new VetnaParser().parsing();
        try {
            dbService.putProducts(products);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<Product> getProductsByCompany(Company company) throws SQLException {
        return dbService.getProductsByCompany(company);
    }

    public List<PivotProductFX> getPivotProducts() throws SQLException {
        return dbService.getPivotProducts();
    }

    //todo del
    public List<Product> getTestProduct() {
        List<Product> result = new ArrayList<>();
        Product pr1 = new Product(Company.Bagira);
        pr1.setName("TestName");
        pr1.setCode(1);
        pr1.setBrandName("brand");
        pr1.setCurrentPrice(12);
        pr1.setWeight(5);
        result.add(pr1);
        return result;
    }
}
