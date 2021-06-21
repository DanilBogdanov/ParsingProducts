package dao;

import entity.Product;
import entity.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetshopDAO implements DAO {
    private Connection connection;
    private String insertOrUpdateQuery = "INSERT INTO PETSHOP(id, brand, name, parsingDate, weight, price, salePrice, imgUrl) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +

            "ON DUPLICATE KEY UPDATE " +
            "brand=VALUES(brand), " +
            "name=VALUES(name), " +
            "parsingDate=VALUES(parsingDate), " +
            "weight=VALUES(weight), " +
            "price=VALUES(price), " +
            "salePrice=VALUES(salePrice), " +
            "imgUrl=VALUES(imgUrl);";


    public PetshopDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public int insertOrUpdate(Product pr) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(insertOrUpdateQuery);
        statement.setInt(1, pr.getCode());
        statement.setString(2, pr.getBrandName());
        statement.setString(3, pr.getName());
        statement.setDate(4, new Date(pr.getParsingDate().getTime()));
        statement.setDouble(5, pr.getWeight());
        statement.setDouble(6, pr.getCurrentPrice());
        statement.setDouble(7, pr.getSalePrice());
        statement.setString(8, pr.getImgUrl());

        int lineChanged = statement.executeUpdate();
        statement.close();
        return lineChanged;

    }

//    @Override
//    public int insertOrUpdateAll(List<Product> products) throws SQLException {
//        int lineChanged = 0;
//        PreparedStatement statement = connection.prepareStatement(insertOrUpdateQuery);
//
//        for (Product pr : products) {
//            statement.setInt(1, pr.getCode());
//            statement.setString(2, pr.getBrandName());
//            statement.setString(3, pr.getName());
//            statement.setDate(4, new Date(pr.getParsingDate().getTime()));
//            statement.setDouble(5, pr.getWeight());
//            statement.setDouble(6, pr.getCurrentPrice());
//            statement.setDouble(7, pr.getSalePrice());
//            statement.setString(8, pr.getImgUrl());
//
//            lineChanged += statement.executeUpdate();
//        }
//        statement.close();
//        return lineChanged;
//    }

    @Override
    public List<Product> getAll() throws SQLException {
        Statement statement = connection.createStatement();
        List<Product> products = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM petshop");

        while (resultSet.next()) {
            products.add(getProductFromResultSet(resultSet));
        }

        resultSet.close();
        statement.close();
        return products;
    }

    public Product getProductById(int id) throws SQLException {
        String getProductByIdQuery = "SELECT * FROM petshop WHERE id=?;";
        PreparedStatement statement = connection.prepareStatement(getProductByIdQuery);
        ResultSet resultSet = statement.executeQuery();
        Product product = getProductFromResultSet(resultSet);
        resultSet.close();
        return product;
    }

    private Product getProductFromResultSet(ResultSet resultSet) throws SQLException {
        int code = resultSet.getInt("id");
        String brand = resultSet.getString("brand");
        String name = resultSet.getString("name");
        Date date = resultSet.getDate("parsingDate");
        double weight = resultSet.getDouble("weight");
        double price = resultSet.getDouble("price");
        double salePrice = resultSet.getDouble("salePrice");
        String imgUrl = resultSet.getString("imgurl");

        Product product = new Product(Company.PetShop);
        product.setCode(code);
        product.setBrandName(brand);
        product.setName(name);
        product.setParsingDate(date);
        product.setWeight(weight);
        product.setCurrentPrice(price);
        product.setSalePrice(salePrice);
        product.setImgUrl(imgUrl);

        return product;
    }
}
