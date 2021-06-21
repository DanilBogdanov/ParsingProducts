package entity;

import java.util.Date;

public class Product {
    private int code;
    private final Company company;
    private Date parsingDate;
    private String brandName;
    private String name;
    private double salePrice;
    private double currentPrice;
    private double weight;
    private String imgUrl;

    public Product(Company company) {
        parsingDate = new Date();
        this.company = company;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Company getCompany() {
        return company;
    }

    public Date getParsingDate() {
        return parsingDate;
    }

    public void setParsingDate(Date date) {
        this.parsingDate = date;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    public String toString() {
        return "Product{" +
                "code=" + code +
                ", company=" + company +
                ", parsingDate=" + parsingDate +
                ", brandName='" + brandName + '\'' +
                ", name='" + name + '\'' +
                ", salePrice=" + salePrice +
                ", currentPrice=" + currentPrice +
                ", weight=" + weight +
                ", imgUrl='" + imgUrl + '\'' +
                '}';
    }
}
