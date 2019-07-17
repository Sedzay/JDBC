package lesson4;

import lesson3.Product;

import java.util.ArrayList;
import java.util.List;


public class Demo {
    public static void main(String[] args) {
        Product product1 = new Product(88, "!!!", "!!!", 7777);
        Product product2 = new Product(88, "!!!", "!!!", 7777);
        Product product3 = new Product(99, "!!!", "!!!", 7777);

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);

        TransactionDemo transactionDemo = new TransactionDemo();

        transactionDemo.save(products);
    }
}
