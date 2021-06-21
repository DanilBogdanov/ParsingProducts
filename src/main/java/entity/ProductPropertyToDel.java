package entity;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class ProductPropertyToDel {
    private StringProperty name;
    private IntegerProperty price;

    public ProductPropertyToDel(String name, int price) {
        this.name.set(name);
        this.price.set(price);
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

    public int getPrice() {
        return price.get();
    }

    public IntegerProperty priceProperty() {
        return price;
    }

    public void setPrice(int price) {
        this.price.set(price);
    }
}
