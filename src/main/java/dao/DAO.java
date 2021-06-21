package dao;

import entity.Product;

import java.sql.SQLException;
import java.util.List;

public interface DAO {
    int insertOrUpdate(Product product) throws SQLException;
    //int insertOrUpdateAll(List<Product> products) throws SQLException;
    List<Product> getAll() throws SQLException;
}
