package varejao.objects;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ProductTest {
  Product product;

  String itemCode;
  String description;
  double salePrice;
  String unit;

  @Parameters
  public static Collection<Object[]> getParameters() {
    Object[][] products = new Object[][] {
      {"001", "Laptop", 1500.0, "unit"},
      {"002", "Smartphone", 800.0, "piece"},
      {"003", "Tablet", 500.0, "unit"},
      {"004", "SmartWatch", 2000.0, "unit"}
    };
    
    return Arrays.asList(products);
  }

  public ProductTest(String itemCode, String description, double salePrice, String unit) {
    this.itemCode = itemCode;
    this.description = description;
    this.salePrice = salePrice;
    this.unit = unit;
  }
  
  @Test
  public void createProduct() {
    product = new Product(itemCode, description, salePrice, unit);

    assertNotNull(product);

    assertEquals(itemCode, product.getItemCode());
    assertEquals(description, product.getDescription());
    assertEquals(salePrice, product.getSalePrice(), 0.001);
    assertEquals(unit, product.getUnit());
  }
  
  @Test
  public void testSettersAndGetters() {
    product = new Product(itemCode, description, salePrice, unit);

    product.setItemCode("005");
    product.setDescription("Headphones");
    product.setSalePrice(100.0);
    product.setUnit("piece");
    
    assertEquals("005", product.getItemCode());
    assertEquals("Headphones", product.getDescription());
    assertEquals(100.0, product.getSalePrice(), 0.001);
    assertEquals("piece", product.getUnit());
  }
}
