package lesson3hw1;

public class Demo {
    public static void main(String[] args) throws Exception{

        Solution solution = new Solution();

        System.out.println(solution.findProductsByPrice(610, 10));

        System.out.println(solution.findProductsByName("cdn kl"));

        System.out.println(solution.findProductsWithEmptyDescription());
    }
}
