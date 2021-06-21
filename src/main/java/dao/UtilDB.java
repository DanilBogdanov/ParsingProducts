package dao;

import java.sql.Statement;

public class UtilDB {
    public static String petshopTableCreate = "" +
            "set mode MySQL; " +
            "CREATE TABLE PETSHOP (\n" +
            "\tID INTEGER NOT NULL PRIMARY KEY,\n" +
            "\tBRAND VARCHAR(255) NOT NULL,\n" +
            "\tNAME VARCHAR(255) NOT NULL,\n" +
            "\tPARSINGDATE DATE NOT NULL,\n" +
            "\tWEIGHT DOUBLE NOT NULL,\n" +
            "\tPRICE DOUBLE NOT NULL,\n" +
            "\tSALEPRICE DOUBLE,\n" +
            "\tIMGURL VARCHAR(255)\n" +
            ");\n";

    public static String bagiraTableCreate = "" +
            "set mode MySQL; " +
            "CREATE TABLE BAGIRA (\n" +
            "\tID INTEGER NOT NULL PRIMARY KEY,\n" +
            "\tBRAND VARCHAR(255),\n" +
            "\tNAME VARCHAR(255) NOT NULL,\n" +
            "\tPARSINGDATE DATE NOT NULL,\n" +
            "\tWEIGHT DOUBLE,\n" +
            "\tPRICE DOUBLE NOT NULL,\n" +
            "\tSALEPRICE DOUBLE,\n" +
            "\tIMGURL VARCHAR(255)\n" +
            ");\n";

    public static String vetnaTableCreate = "" +
            "set mode MySQL; " +
            "CREATE TABLE VETNA (\n" +
            "\tID INTEGER NOT NULL PRIMARY KEY,\n" +
            "\tBRAND VARCHAR(255) NOT NULL,\n" +
            "\tNAME VARCHAR(255) NOT NULL,\n" +
            "\tPARSINGDATE DATE NOT NULL,\n" +
            "\tWEIGHT DOUBLE NOT NULL,\n" +
            "\tPRICE DOUBLE NOT NULL,\n" +
            "\tSALEPRICE DOUBLE,\n" +
            "\tIMGURL VARCHAR(255)\n" +
            ");\n";

    public static String pivotTableCreate = "" +
            "set mode MySQL; " +
            "create table PIVOT_TABLE\n" +
            "(\n" +
            "\tBAGIRA_ID int not null,\n" +
            "\tPETSHOP_ID int,\n" +
            "\tVETNA_ID int,\n" +
            "\tconstraint PIVOT_TABLE_BAGIRA_ID_FK\n" +
            "\t\tforeign key (BAGIRA_ID) references BAGIRA,\n" +
            "\tconstraint PIVOT_TABLE_PETSHOP_ID_FK\n" +
            "\t\tforeign key (PETSHOP_ID) references PETSHOP,\n" +
            "\tconstraint PIVOT_TABLE_VETNA_ID_FK\n" +
            "\t\tforeign key (VETNA_ID) references VETNA\n" +
            ");\n" +
            "\n" +
            "create unique index PIVOT_TABLE_BAGIRA_ID_UINDEX\n" +
            "\ton PIVOT_TABLE (BAGIRA_ID);\n" +
            "\n" +
            "alter table PIVOT_TABLE\n" +
            "\tadd constraint PIVOT_TABLE_PK\n" +
            "\t\tprimary key (BAGIRA_ID);\n" ;


    public static boolean createTable(String query) throws Exception {
        DBService service = new DBService();
        Statement statement = service.getConnection().createStatement();
        boolean result = statement.execute(query);
        statement.close();
        service.close();
        return result;
    }

    public static void main(String[] args) throws Exception {
        createTable(pivotTableCreate);
    }
}
