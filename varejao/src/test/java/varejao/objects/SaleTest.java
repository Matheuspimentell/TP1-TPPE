package varejao.objects;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class SaleTest {

    private Sale sale;
    private double expectedTotal;
    private double expectedDiscount;
    private double expectedFreight;
    private double expectedICMS;
    private double expectedMunicipalTax;
    private double expectedCashback;
    private double expectedFinalPrice;

    public SaleTest(Sale sale, double expectedTotal, double expectedDiscount, double expectedFreight, double expectedICMS, double expectedMunicipalTax, double expectedCashback, double expectedFinalPrice) {
        this.sale = sale;
        this.expectedTotal = expectedTotal;
        this.expectedDiscount = expectedDiscount;
        this.expectedFreight = expectedFreight;
        this.expectedICMS = expectedICMS;
        this.expectedMunicipalTax = expectedMunicipalTax;
        this.expectedCashback = expectedCashback;
        this.expectedFinalPrice = expectedFinalPrice;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Product product1 = new Product("001", "Laptop", 1500.0, "unit");
        Product product2 = new Product("002", "Mouse", 50.0, "unit");
        SaleItem item1 = new SaleItem(product1, 1);
        SaleItem item2 = new SaleItem(product2, 2);

        Customer customer1 = new Customer("johndoe", Customer.CustomerType.Default, new Address("SP", true));
        Customer customer2 = new Customer("janedoe", Customer.CustomerType.Special, new Address("RJ", false));
        Customer customer3 = new Customer("maryjane", Customer.CustomerType.Prime, new Address("DF", true));

        return Arrays.asList(new Object[][]{
            {new Sale(Arrays.asList(item1, item2), customer1, PaymentMethod.OTHER, "2023-07-01"), 1600.0, 0.0, 7.0, 192.0, 64.0, 0.0, 1863.0},
            {new Sale(Arrays.asList(item1, item2), customer2, PaymentMethod.CREDIT_CARD_COMPANY, "2023-07-01"), 1600.0, 320.0, 7.0, 192.0, 64.0, 0.0, 1043.0},
            {new Sale(Arrays.asList(item1, item2), customer3, PaymentMethod.OTHER, "2023-07-01"), 1600.0, 0.0, 0.0, 288.0, 0.0, 48.0, 1840.0}
        });
    }

    @Test
    public void testCalculateTotal() {
        assertEquals(expectedTotal, sale.calculateTotal(), 0.001);
    }

    @Test
    public void testCalculateDiscount() {
        assertEquals(expectedDiscount, sale.calculateDiscount(), 0.001);
    }

    @Test
    public void testCalculateFreight() {
        assertEquals(expectedFreight, sale.calculateFreight(), 0.001);
    }

    @Test
    public void testCalculateICMS() {
        assertEquals(expectedICMS, sale.calculateICMS(), 0.001);
    }

    @Test
    public void testCalculateMunicipalTax() {
        assertEquals(expectedMunicipalTax, sale.calculateMunicipalTax(), 0.001);
    }

    @Test
    public void testCalculateCashback() {
        assertEquals(expectedCashback, sale.calculateCashback(), 0.001);
    }

    @Test
    public void testCalculateFinalPrice() {
        assertEquals(expectedFinalPrice, sale.calculateFinalPrice(), 0.001);
    }
}
