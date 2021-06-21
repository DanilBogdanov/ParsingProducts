package parser;

import entity.Company;
import entity.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class PetshopParser extends AbstractWebParser {
    private static final String PETSHOP_URL = "https://www.petshop.ru/";

    private final List<String> parsingUrls = new ArrayList<String>();

    {//todo добавить другие страницы
        parsingUrls.add("https://www.petshop.ru/catalog/cats/syxkor/");//сухой для кошек
    }

    //id="products-wrapper" блок с товарами
    private List<Product> getProductsFromPage(WebElement webElement) {
        ArrayList<Product> products = new ArrayList<Product>();
        List<WebElement> elements = webElement.findElements(By.className("product-item")); //class="product-item j_product j_adj_item" блок с товаром
        //todo to del выводит номер страницы
        WebElement elementtt = getWebDriver().findElement(By.xpath("//div[@class=\"page-navigation\"]/span[contains(@class, \"current\")]"));
        System.out.print(elementtt.getText());//todo del
        //проход по найденным позициям
        for (WebElement el : elements) {
            try {
                String brand = el.findElement(By.className("j_product-brand")).getText();//class=j_product-brand j_product-link
                String name = el.findElement(By.className("j_product-name")).getText();//class=inner j_product-name j_adj_subtitle
                String imgUrl = el.findElement(By.xpath(".//img")).getAttribute("src");
                //если картинка не подгрузилась то null
                if ("https://www.petshop.ru/bitrix/templates/ps/img/load.png".equals(imgUrl)) {
                    imgUrl = null;
                }

                for (WebElement offer : el.findElements(By.className("offer-item"))) { //class=offer-item  goods-discount
                    //price  class="price-optimal" (1 950)
                    double price = 0;
                    String priceString = offer.findElement(By.className("price-optimal")).getText();
                    //если начинается "От 123 руб" переходим на следующий товар, проверять до веса
                    if (priceString.contains("От "))
                        break;
                    price = Double.parseDouble(priceString.replace(" ", "").replace("i", ""));//"5 632 i" убрать пробел и i

                    //old price
                    double priceOld = 0;
                    if (offer.findElements(By.className("price-old")).size() > 0) {//проверка наличия класса старая цена, если есть, то считываем
                        String priceOldString = offer.findElement(By.className("price-old")).findElement(By.tagName("span")).getText();  //class=price-old => <span>
                        priceOld = !("".equals(priceOldString)) ? Double.parseDouble(priceOldString.replace(" ", "")) : 0;
                    }

                    //weight  class="type"
                    double weight;
                    if (!containsClass("type")) {
                        break;
                    }
                    String weightString = offer.findElement(By.className("type")).getText();//class="type" - weight(2 кг)
                    weightString = weightString.replace(",", ".");
                    weight = Double.parseDouble(weightString.split(" ")[0]);//"2 кг" разделяем по пробелу и первый элемент массива
                    if (weightString.split(" ")[1].equals("г")) {//если граммы, то делим на 1000
                        weight /= 1000;
                    }



                    int code = Integer.parseInt(offer.getAttribute("data-id"));

                    Product product = new Product(Company.PetShop);
                    product.setCode(code);
                    product.setBrandName(brand);
                    product.setName(name);
                    product.setWeight(weight);
                    if (priceOld != 0) {
                        product.setCurrentPrice(priceOld);
                        product.setSalePrice(price);
                    } else {
                        product.setCurrentPrice(price);
                    }
                    product.setImgUrl(imgUrl);

                    System.out.println(product);//todo del
                    products.add(product);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Ошибка в загрузке товаров Петшоп:" + el.getText());

            }
        }
        System.out.println();//todo del
        return products;
    }

    private List<Product> getProductsFromUrl(String url) {
        List<Product> products = new ArrayList<Product>();
        try {
            getWebDriver().get(url);
            //отображать по 100 товаров на странице
            getWebDriver().findElement(By.className("show-by")).findElement(By.partialLinkText("100")).click();

            while (true) {
                Thread.sleep(5000);//время подгрузиться товару
                products.addAll(getProductsFromPage(getWebDriver().findElement(By.id("products-wrapper"))));

                //если есть сдедующая страница кликаем, если нет выходим из цикла
                if (containsClass("page-navigation") &&
                        containsClass(getWebDriver().findElement(By.className("page-navigation")), "next")) {
                    //элемент может быть не доступен, несколько раз пробуем перезагрузить страницу
                    for (int i = 0; i < 5; i++) {
                        try {
                            getWebDriver().findElement(By.className("page-navigation")).findElement(By.className("next")).click();
                            break;
                        } catch (Exception e) {
                            System.err.println("Can't get page navigator");
                            getWebDriver().navigate().refresh();
                            Thread.sleep(3000);
                        }
                    }
                } else {
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    //на всплывающем окне "првильный город?" нажимаем на "нет, другой"
    public void setHomeRegion() {
        int lastTimeout = getTimeout();
        setTimeout(20);
        getWebDriver().findElement(By.xpath("//button/span[contains(text(), \"Нет, другой\")]")).click();
        getWebDriver().findElement(By.xpath("//div/div/span[contains(text(), \"Уфа\")]")).click();
        try {
            Thread.sleep(0);//подождать пока прогрузятся баннеры и перегрузить страницу
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        getWebDriver().navigate().refresh();
        setTimeout(lastTimeout);
    }

    @Override
    public List<Product> parsing() {
        List<Product> products = new ArrayList<Product>();
        getWebDriver().get(PETSHOP_URL);
        setHomeRegion();

        for (String url : parsingUrls) {
            products.addAll(getProductsFromUrl(url));
        }
        getWebDriver().close();
        return products;
    }
}
