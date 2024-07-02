package varejao.objects;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class SaleItemTest {

    private SaleItem saleItem;
    private double expectedTotalPrice;

    public SaleItemTest(SaleItem saleItem, double expectedTotalPrice) {
        this.saleItem = saleItem;
        this.expectedTotalPrice = expectedTotalPrice;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        Product product1 = new Product("001", "Laptop", 1500.0, "unit");
        Product product2 = new Product("002", "Mouse", 50.0, "unit");

        return Arrays.asList(new Object[][]{
            {new SaleItem(product1, 1), 1500.0},
            {new SaleItem(product2, 2), 100.0}
        });
    }

    @Test
    public void testGetTotalPrice() {
        assertEquals(expectedTotalPrice, saleItem.getTotalPrice(), 0.001);
    }
}
