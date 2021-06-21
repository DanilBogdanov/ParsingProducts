package parser;

import entity.Company;
import entity.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class VetnaParser extends AbstractWebParser {
    private static final String VETNA_URL = "https://vetna.info/";
    private final static List<String> parsingUrls = new ArrayList<>();

    {
        parsingUrls.add("https://vetna.info/catalog/korm-suhoy-vlazhniy/");
        //parsingUrls.add("https://vetna.info/catalog/suhoj-vlazhnyj-korm-dlja-sobak/");
        //parsingUrls.add("https://vetna.info/catalog/napolniteli-i-tualety-dly-koshek/");

    }

    private void checkCity() {
        setTimeout(10);
        WebElement element = getWebDriver().findElement(By.xpath("//div[@class=\"wrapGeoIpReaspekt\"]/span[contains(@class, \"cityLinkPopupReaspekt\")]"));
        String city = element.getText();
        if (!"Уфа".equals(city)) {
            element.click();
            getWebDriver().findElement(By.xpath("//div[contains(@class, \"reaspektGeobaseAct\")]/a[contains(@title, \"Уфа\")]")).click();
        }
        setTimeout(0);
    }

    @Override
    public List<Product> parsing() {
        List<Product> products = new ArrayList<>();
        getWebDriver().manage().window().maximize();
        getWebDriver().get(VETNA_URL);
        checkCity();

        for (String url : parsingUrls) {
            products.addAll(getProductsFromUrl(url));
        }
        getWebDriver().close();
        return products;
    }

    private List<Product> getProductsFromUrl(String url) {
        List<Product> products = new ArrayList<Product>();
        try {
            getWebDriver().get(url);
            //отображать по 66 товаров на странице
            getWebDriver().findElement(By.xpath("//span[contains(@onclick, \"setAttrCatalogueNav('nav','66');\")]")).click();

            while (true) {
                Thread.sleep(5000);//время подгрузиться товару
                setTimeout(5);
                products.addAll(getProductsFromPage(getWebDriver().findElement(By.xpath("//div[contains(@class, \"catalogue-item-card-block\")]"))));

                //если есть блок навигации, кнопка некст и кнопка в конец, кликаем. Иначе выходим из цикла
                if (containsClass("flex-main-block-ir") &&
                        containsClass("catalogue-pagination-prev-next") && containsXPath("//li[contains(@class, \"catalogue-pagination-prev-next\")]/a[contains(text(), \"В конец\")]")) {
                    getWebDriver().findElement(By.xpath("//li/a[contains(@class, \"catalogue-pagination-next\")]")).click();
                } else {
                    break;
                }
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return products;
    }

    private List<Product> getProductsFromPage(WebElement webElement) {
        ArrayList<Product> products = new ArrayList<Product>();
        //list of elements with goods
        List<WebElement> elements = webElement.findElements(By.xpath("//div[contains(@class, \"catalogue-item-card1\") and contains(@data-entity, \"item\")]"));
        //todo to del выводит номер страницы
        WebElement elementtt = getWebDriver().findElement(By.xpath("//span[contains(@class, \"catalogue-pagination-current\")]"));
        System.out.print(elementtt.getText());//todo del
        //проход по найденным позициям
        setTimeout(0);
        for (WebElement el : elements) {
            try {

                String sCode = el.findElement(By.xpath(".//div[contains(@class, \"catalogue-item-card1-bottom-block\")]/span[contains(text(), \"Код: \")]")).getText();
                sCode = sCode.replace("Код: ", "").replace(" ", "");
                int code = Integer.parseInt(sCode);

                String brand = el.findElement(By.xpath(".//input[contains(@class, \"commerc_brand\")]")).getAttribute("value");

                String name = el.findElement(By.xpath(".//input[contains(@class, \"commerc_name\")]")).getAttribute("value");

                double price = Double.parseDouble(el.findElement(By.xpath(".//input[contains(@class, \"commerc_price\")]")).getAttribute("value"));

                Product product = new Product(Company.Vetna);
                product.setCode(code);
                product.setBrandName(brand);
                product.setName(name);


                //если есть значок акция, то цена акционная иначе обычная
                if (containsXPath(el, ".//div[contains(@class, \"catalogue-item-card1-shild-block\")]/div/span")) {
                    product.setSalePrice(price);
                } else {
                    product.setCurrentPrice(price);
                }

                products.add(product);
                System.out.println(product);//todo del
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Ошибка в загрузке товаров с сайта Vetna: " + el);
            }
        }

        return products;
    }


}
