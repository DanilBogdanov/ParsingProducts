package parser;

import entity.Company;
import entity.Product;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BagiraParser implements Parser{
    @Override
    public List<Product> parsing() {
        return parsingJson(getJson());
    }

    public static String getJson() {
        HttpClient httpClient = HttpClientBuilder.create().build();
        String result = "";
        try {

            HttpPost request = new HttpPost("http://localhost/1c/ws/ws1.1cws");
            request.addHeader("Accept-Encoding", "gzip,deflate");
            request.addHeader("Content-Type", "application/soap+xml;charset=UTF-8;action=\"http://localhost/uri/#Goods:getPriceJSON\"");
            request.addHeader("Authorization", "Basic 0JTQsNC90LjQuzoxNDEyNTY=");
            request.addHeader("Host", "localhost");
            request.addHeader("User-Agent", "Apache-HttpClient/4.5.5 (Java/12.0.1)");
            StringEntity params = new StringEntity("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:uri=\"http://localhost/uri/\">\n" +
                    "   <soapenv:Header/>\n" +
                    "   <soapenv:Body>\n" +
                    "      <uri:getPriceJSON/>\n" +
                    "   </soapenv:Body>\n" +
                    "</soapenv:Envelope>");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);

            HttpEntity entity = response.getEntity();

            BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()));
            StringBuilder sb = new StringBuilder();
            while (reader.ready()) {
                sb.append(reader.readLine());
            }

            result = sb.substring(sb.indexOf("instance\">") + "instance\">".length(), sb.indexOf("</m:return>"));


        } catch (Exception ex) {
            System.out.println("trouble http ");
            ex.printStackTrace();
        }

        return result;
    }

    public static List<Product> parsingJson(String json) {
        List<Product> products = new ArrayList<Product>();
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
            JSONArray items = (JSONArray) jsonObject.get("items");

            for (Object object : items) {
                JSONObject item = (JSONObject) object;
                Product product = new Product(Company.Bagira);
                product.setCode(Integer.parseInt((String)item.get("id")));
                product.setName((String) item.get("name"));
                //delete all not number
                String sPrice = ((String) item.get("price")).replaceAll("[^0-9]", "");
                product.setCurrentPrice(Integer.parseInt(sPrice));

                products.add(product);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return products;
    }
}
