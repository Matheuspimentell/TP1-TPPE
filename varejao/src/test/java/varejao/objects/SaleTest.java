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
      Object[][] sales = new Object[][]{
          {
              LocalDateTime.parse("2024-06-20T10:30:04"),
              new Customer("joao", CustomerType.Prime, new Address("DF", true)),
              "4296 1301 1907 7845",
              Arrays.asList(
                  new Product("001", "headset", 120.0, "unit"),
                  new Product("002", "keyboard", 180.0, "unit"),
                  new Product("006", "water bottle", 10.0, "ml"),
                  new Product("007", "notebook", 45.0, "unit"),
                  new Product("008", "pen", 1.5, "unit")
              )
          },
          {
              LocalDateTime.parse("2024-06-20T12:10:04"),
              new Customer("vinicius", CustomerType.Default, new Address("GO", false)),
              "4226 1301 1807 7845",
              Arrays.asList(
                  new Product("001", "headset", 120.0, "unit"),
                  new Product("009", "laptop", 1500.0, "unit"),
                  new Product("003", "red bull energy drink", 5.25, "ml"),
                  new Product("004", "mousepad", 85.25, "unit"),
                  new Product("005", "chocolate", 4.75, "g")
              )
          },
          {
              LocalDateTime.parse("2024-06-20T10:30:04"),
              new Customer("lucas", CustomerType.Special, new Address("RJ", false)),
              "4299 1301 1900 7845",
              Arrays.asList(
                  new Product("010", "monitor", 300.0, "unit"),
                  new Product("011", "desk", 200.0, "unit"),
                  new Product("012", "chair", 150.0, "unit"),
                  new Product("004", "mousepad", 85.25, "unit"),
                  new Product("013", "cable", 20.0, "m")
              )
          },
          {
              LocalDateTime.parse("2024-06-20T10:30:04"),
              new Customer("anderson", CustomerType.Default, new Address("MT", true)),
              "4296 1301 1117 7881",
              Arrays.asList(
                  new Product("014", "tablet", 250.0, "unit"),
                  new Product("015", "smartphone", 800.0, "unit"),
                  new Product("016", "smartwatch", 200.0, "unit"),
                  new Product("017", "earbuds", 50.0, "unit"),
                  new Product("018", "charger", 25.0, "unit")
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

  @Test
  public void saleICMS() {
    sale = new Sale(date, customer, cardNumber, items);
    double icms = 0.0;
    double total = 0.0;

    for(int i = 0; i < items.size(); i++) {
      total+=items.get(i).getSalePrice();
    }

    if(customer.getAddress().getState() == "DF") {
      icms = total * 0.18;
    } else  {
      icms = total * 0.12;
    }

    assertEquals(icms, sale.getICMS(customer.getAddress()), 0.001);
  }
}
