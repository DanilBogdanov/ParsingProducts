package dao;

import entity.PivotProductFX;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PivotTableDao {
    private Connection connection;


    public PivotTableDao(Connection connection) {
        this.connection = connection;
    }

    public int insertOrUpdateBagiraId(Integer bagira_id) throws SQLException {
        String insertQuery = "INSERT INTO Pivot_Table(bagira_id) " +
                "VALUES (?) on duplicate key update bagira_id=values(bagira_id);";
        PreparedStatement statement = connection.prepareStatement(insertQuery);
        statement.setInt(1, bagira_id);

        int lineChanged = statement.executeUpdate();
        statement.close();
        return lineChanged;
    }

    public int insertOrUpdatePetshopId(int bagira_id, int petshop_id) throws SQLException {
        //price=case VALUES(price) when 0 then price else values(price) end,
        String insertOrUpdatePetshopIdQuery = "INSERT INTO Pivot_Table(bagira_id, petshop_id) " +
                "VALUES (?, ?) " +

                "ON DUPLICATE KEY UPDATE " +
                "petshop_id=VALUES(petshop_id); ";
        PreparedStatement statement = connection.prepareStatement(insertOrUpdatePetshopIdQuery);
        statement.setInt(1, bagira_id);
        statement.setInt(2, petshop_id);

        int lineChanged = statement.executeUpdate();
        statement.close();
        return lineChanged;
    }

    public int insertOrUpdateVetnaId(int bagira_id, int vetna_id) throws SQLException {
        String insertOrUpdateVetnaIdQuery = "INSERT INTO Pivot_Table(bagira_id, vetna_id) " +
                "VALUES (?, ?) " +

                "ON DUPLICATE KEY UPDATE " +
                "vetna_id=VALUES(vetna_id); ";
        PreparedStatement statement = connection.prepareStatement(insertOrUpdateVetnaIdQuery);
        statement.setInt(1, bagira_id);
        statement.setInt(2, vetna_id);

        int lineChanged = statement.executeUpdate();
        statement.close();
        return lineChanged;
    }

    public List<PivotProductFX> getAll() throws SQLException {
        Statement statement = connection.createStatement();
        List<PivotProductFX> pivotProducts = new ArrayList<>();
        ResultSet resultSet = statement.executeQuery("" +
                "SELECT B.NAME , B.PRICE, B.PARSINGDATE, P.PRICE, P.SALEPRICE, P.PARSINGDATE, V.PRICE, V.SALEPRICE, V.PARSINGDATE " +
                "FROM Bagira AS B RIGHT JOIN PIVOT_TABLE AS Piv ON B.id = Piv.bagira_id " +
                "     LEFT JOIN PETSHOP P on Piv.PETSHOP_ID = P.ID " +
                "     LEFT JOIN VETNA V on Piv.VETNA_ID = V.ID;");

        while (resultSet.next()) {
            pivotProducts.add(getPivotProductFXFromResultSet(resultSet));
        }

        return pivotProducts;
    }

    private PivotProductFX getPivotProductFXFromResultSet(ResultSet resultSet) throws SQLException {
        PivotProductFX pivotProductFX = new PivotProductFX();
        pivotProductFX.setName(resultSet.getString("name"));
        pivotProductFX.setBagiraPrice(resultSet.getDouble("BAGIRA.PRICE"));
        pivotProductFX.setBagiraParsingDate(resultSet.getDate("Bagira.parsingDate").toString());
        pivotProductFX.setPetshopPrice(resultSet.getDouble("Petshop.price"));
        pivotProductFX.setPetshopSalePrice(resultSet.getDouble("Petshop.salePrice"));
        pivotProductFX.setPetshopParsingDate("" + resultSet.getDate("Petshop.parsingDate"));
        pivotProductFX.setVetnaPrice(resultSet.getDouble("Vetna.price"));
        pivotProductFX.setVetnaSalePrice(resultSet.getDouble("vetna.salePrice"));
        pivotProductFX.setVetnaParsingDate("" + resultSet.getDate("vetna.parsingDate"));

        return pivotProductFX;
    }
}