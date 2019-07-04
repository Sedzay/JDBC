package lesson3;

public class Demo {
    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();

        Product product = new Product(10, "test2", "test description2", 199);

        //productDAO.save(product);

        //productDAO.update(product);
        //System.out.println(productDAO.getProducts());

        //productDAO.delete(10);
        System.out.println(productDAO.getProducts());

    }
}
