import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        String CVCD = "CVCD";
        String SDFD = "SDFD";
        String DDDF = "DDDF";
        String VERSION = "version";
        String EDITION = "edition";
        Map<String, Object> mapCVCD = new HashMap<>();
        mapCVCD.put(VERSION, 1);
        mapCVCD.put(EDITION, "X");
        Map<String, Object> mapSDFD = new HashMap<>();
        mapSDFD.put(VERSION, 2);
        mapSDFD.put(EDITION, "Z");
        Map<String, Object> mapDDDF = new HashMap<>();
        mapDDDF.put(VERSION, 1);

        Map<String, Map<String, Object>> mappings = new HashMap<>();
        mappings.put(CVCD, mapCVCD);
        mappings.put(SDFD, mapSDFD);
        mappings.put(DDDF, mapDDDF);
        List<String> profuctNames = List.of(CVCD, SDFD, DDDF, SDFD);

        method(profuctNames, mappings);
    }

    /**
     * Write a method that takes two inputs: a list of purchased product codes and a map of
     * mappings for these codes. The method should return an aggregated list of purchased
     * products and quantity based on the list of purchased products codes.
     * Inputs
     * List of products: ["CVCD", "SDFD", "DDDF", "SDFD"]
     * Mappings: {"CVCD": {"version": 1,"edition": "X"},"SDFD": {"version": 2,"edition":
     * "Z"},"DDDF": {"version": 1}}
     * Expected Output
     * Purchased items:
     * [{"version":1,"edition":"X","quantity":1},{"version":1,"quantity":1},{"version":2,"edition":"Z","qu
     * antity":2}]
     * @param productNames - list of product names
     * @param mappings - map for product names with characteristics
     */
    public static void method(List<String> productNames, Map<String, Map<String, Object>> mappings) {
        Map<String, Product> productMap = ProductFactory.initMap();
        Map<String, Product> count = new HashMap<>();
        for (String productName : productNames) {
            Product product = productMap.get(productName);
            Map<String, Object> ms = mappings.get(productName);
            StringBuilder stringBuilder = new StringBuilder(productName);
            Object object = ms.get("version");
            Integer version = null;
            if (object != null) {
                version = (Integer) object;
                stringBuilder.append(version);
            }
            object = ms.get("edition");
            String edition = null;
            if (object != null) {
                edition = (String) object;
                stringBuilder.append(edition);
            }
            String name = stringBuilder.toString();
            Product newProduct = product.buildInstance(version, edition);
            if (count.get(name) == null) {
                count.put(stringBuilder.toString(), newProduct);
            } else {
                count.get(name).setQuantity(count.get(name).getQuantity() + 1);
            }
        }
        System.out.println(count.values());
    }

}

class ProductFactory {
    public static Map<String, Product> initMap() {
        CVCD cvcd = new CVCD();
        SDFD sdfd = new SDFD();
        DDDF dddf = new DDDF();
        return Map.of("CVCD", cvcd, "SDFD", sdfd, "DDDF", dddf);
    }
}

interface Factory {
    Product buildInstance(Integer version, String edition);
}

abstract class Product implements Factory {
    Integer version;
    Integer quantity;

    public Product() {
    }

    public Product(Integer version) {
        this.version = version;
        this.quantity = 1;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

abstract class Edittion extends Product {
    String edition;

    public Edittion() {
        super();
    }

    public Edittion(String edition, Integer version) {
        super(version);
        this.edition = edition;
    }

    @Override
    public String toString() {
        return "{\"version\":" + version +
                ", \"quantity\":" + quantity +
                ", \"edition\":'" + edition + '\'' +
                '}';
    }
}

class SDFD extends Edittion {

    public SDFD() {
        super();
    }

    public SDFD(String edition, Integer version) {
        super(edition, version);
    }

    @Override
    public Product buildInstance(Integer version, String edition) {
        return new SDFD(edition, version);
    }
}

class CVCD extends Edittion {

    public CVCD(String edition, Integer version) {
        super(edition, version);
    }

    public CVCD() {
        super();
    }

    @Override
    public Product buildInstance(Integer version, String edition) {
        return new CVCD(edition, version);
    }
}

class DDDF extends Product {
    public DDDF(Integer version) {
        super(version);
    }

    public DDDF() {
        super();
    }

    @Override
    public Product buildInstance(Integer version, String edition) {
        return new DDDF(version);
    }

    @Override
    public String toString() {
        return "{\"version\":" + version +
                ", \"quantity\":" + quantity +
                '}';
    }
}

