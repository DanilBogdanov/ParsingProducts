package entity;

import javafx.beans.property.*;

public class PivotProductFX {
    private StringProperty name = new SimpleStringProperty();
    private DoubleProperty bagiraPrice = new SimpleDoubleProperty();
    private StringProperty bagiraParsingDate = new SimpleStringProperty();
    private DoubleProperty petshopPrice = new SimpleDoubleProperty();
    private DoubleProperty petshopSalePrice = new SimpleDoubleProperty();
    private StringProperty petshopParsingDate = new SimpleStringProperty();
    private DoubleProperty vetnaPrice = new SimpleDoubleProperty();
    private DoubleProperty vetnaSalePrice = new SimpleDoubleProperty();
    private StringProperty vetnaParsingDate = new SimpleStringProperty();

    public PivotProductFX() {
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public double getBagiraPrice() {
        return bagiraPrice.get();
    }

    public DoubleProperty bagiraPriceProperty() {
        return bagiraPrice;
    }

    public void setBagiraPrice(double bagiraPrice) {
        this.bagiraPrice.set(bagiraPrice);
    }

    public String getBagiraParsingDate() {
        return bagiraParsingDate.get();
    }

    public StringProperty bagiraParsingDateProperty() {
        return bagiraParsingDate;
    }

    public void setBagiraParsingDate(String bagiraParsingDate) {
        this.bagiraParsingDate.set(bagiraParsingDate);
    }

    public double getPetshopPrice() {
        return petshopPrice.get();
    }

    public DoubleProperty petshopPriceProperty() {
        return petshopPrice;
    }

    public void setPetshopPrice(double petshopPrice) {
        this.petshopPrice.set(petshopPrice);
    }

    public double getPetshopSalePrice() {
        return petshopSalePrice.get();
    }

    public DoubleProperty petshopSalePriceProperty() {
        return petshopSalePrice;
    }

    public void setPetshopSalePrice(double petshopSalePrice) {
        this.petshopSalePrice.set(petshopSalePrice);
    }

    public String getPetshopParsingDate() {
        return petshopParsingDate.get();
    }

    public StringProperty petshopParsingDateProperty() {
        return petshopParsingDate;
    }

    public void setPetshopParsingDate(String petshopParsingDate) {
        this.petshopParsingDate.set(petshopParsingDate);
    }

    public double getVetnaPrice() {
        return vetnaPrice.get();
    }

    public DoubleProperty vetnaPriceProperty() {
        return vetnaPrice;
    }

    public void setVetnaPrice(double vetnaPrice) {
        this.vetnaPrice.set(vetnaPrice);
    }

    public double getVetnaSalePrice() {
        return vetnaSalePrice.get();
    }

    public DoubleProperty vetnaSalePriceProperty() {
        return vetnaSalePrice;
    }

    public void setVetnaSalePrice(double vetnaSalePrice) {
        this.vetnaSalePrice.set(vetnaSalePrice);
    }

    public String getVetnaParsingDate() {
        return vetnaParsingDate.get();
    }

    public StringProperty vetnaParsingDateProperty() {
        return vetnaParsingDate;
    }

    public void setVetnaParsingDate(String vetnaParsingDate) {
        this.vetnaParsingDate.set(vetnaParsingDate);
    }

    @Override
    public String toString() {
        return "PivotProductFX{" +
                "name=" + name.getValue() +
                ", bagiraPrice=" + bagiraPrice.getValue() +
                ", bagiraParsingDate=" + bagiraParsingDate.getValue() +
                ", petshopPrice=" + petshopPrice.getValue() +
                ", petshopSalePrice=" + petshopSalePrice.getValue() +
                ", petshopParsingDate=" + petshopParsingDate.getValue() +
                ", vetnaPrice=" + vetnaPrice.getValue() +
                ", vetnaSalePrice=" + vetnaSalePrice.getValue() +
                ", vetnaParsingDate=" + vetnaParsingDate.getValue() +
                '}';
    }
}
