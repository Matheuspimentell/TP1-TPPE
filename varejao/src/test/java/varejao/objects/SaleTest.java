package varejao.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import varejao.objects.Customer.CustomerType;

@RunWith(Parameterized.class)
public class SaleTest {
  Sale sale;

  LocalDateTime date;
  Customer customer;
  String cardNumber;
  List<Product> items;

  @Parameters
  public static Collection<Object[]> getParameters() {
    Object[][] sales = new Object[][] {
      {
        LocalDateTime.parse("2024-06-20 10:30:04"),
        new Customer("joao", CustomerType.Prime, new Address("DF", false)),
        "4296 1301 1907 7845",
        Arrays.asList(
          new Product("001", "headset", 120.0, "unit"),
          new Product("002", "keyboard", 180.0, "unit"),
          new Product("003", "red bull energy drink", 5.25, "ml"),
          new Product("004", "mousepad", 85.25, "unit"),
          new Product("005", "chocolate", 4.75, "g")
        )
      }
    };
    
    return Arrays.asList(sales);
  }

  public SaleTest(LocalDateTime date, Customer customer, String cardNumber, List<Product> items) {
    this.date = date;
    this.customer = customer;
    this.cardNumber = cardNumber;
    this.items = items;
  }

  @Test
  public void sellToCustomerTest() {
    sale = new Sale(date, customer, cardNumber, items);

    assertNotNull(sale);
    assertEquals(date, sale.getDate());
    assertEquals(customer, sale.getCustomer());
    assertEquals(cardNumber, sale.getCardNumber());
    assertEquals(items, sale.getItems());
  }
}
