package varejao.objects;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ProductTest {
  @Test
  public void createProduct() {
    Product product = new Product("001", "Laptop", 1500.0, "unit");

    assertEquals("001", product.getItemCode());
    assertEquals("Laptop", product.getDescription());
    assertEquals(1500.0, product.getSalePrice(), 0.001);
    assertEquals("unit", product.getUnit());
  }
  
  @Test
  public void testSettersAndGetters() {
    Product product = new Product("001", "Laptop", 1500.0, "unit");
    product.setItemCode("002");
    product.setDescription("Smartphone");
    product.setSalePrice(800.0);
    product.setUnit("piece");

    assertEquals("002", product.getItemCode());
    assertEquals("Smartphone", product.getDescription());
    assertEquals(800.0, product.getSalePrice(), 0.001);
    assertEquals("piece", product.getUnit());
  }
}
