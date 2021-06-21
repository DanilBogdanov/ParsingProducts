package dao;

import entity.Company;
import entity.PivotProductFX;
import entity.Product;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

//todo connection to close

/**
 * todo проверить изменение цены при акционных ценах
 */
public class DBService implements AutoCloseable {
    private Connection connection;
    private final Map<Company, DAO> companyMap = new HashMap<>();

    public DBService() {
        connection = getConnection();
        companyMap.put(Company.PetShop, new PetshopDAO(connection));
        companyMap.put(Company.Vetna, new VetnaDAO(connection));
        companyMap.put(Company.Bagira, new BagiraDAO(connection));
    }

    private DAO getDao(Company company) {
        return companyMap.get(company);
    }

    Connection getConnection() {
        Connection conn = null;
        try (InputStream is = Files.newInputStream(Paths.get("properties/prop.properties"))) {
            Properties properties = new Properties();
            properties.load(is);
            String url = properties.getProperty("url");
            String user = properties.getProperty("user");
            String pass = properties.getProperty("pass");
            conn = DriverManager.getConnection(url, user, pass);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public int putProduct(Product product) throws SQLException {
        return getDao(product.getCompany()).insertOrUpdate(product);
    }

    //todo
    public int putProducts(List<Product> products) throws SQLException {
        int countLines = 0;

        for (Product product : products) {
            countLines += getDao(product.getCompany()).insertOrUpdate(product);
        }

        return countLines;
    }

    public List<Product> getProductsByCompany(Company company) throws SQLException {
        List<Product> products = new ArrayList<>();
        DAO dao = companyMap.get(company);
        products = dao.getAll();
        return products;
    }

    public int putPivotBagiraId(int bagira_id) throws SQLException {
        return new PivotTableDao(connection).insertOrUpdateBagiraId(bagira_id);
    }

    public int putPivotPetshopId(int bagira_id, int petshop_id) throws SQLException {
        return new PivotTableDao(connection).insertOrUpdatePetshopId(bagira_id, petshop_id);
    }

    public int putPivotVetnaId(int bagira_id, int vetna_id) throws SQLException {
        return new PivotTableDao(connection).insertOrUpdateVetnaId(bagira_id, vetna_id);
    }

    public List<PivotProductFX> getPivotProducts() throws SQLException {
        return new PivotTableDao(connection).getAll();
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
