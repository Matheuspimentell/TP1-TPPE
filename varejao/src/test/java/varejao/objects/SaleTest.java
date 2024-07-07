package varejao.objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
              new Customer("anderson", CustomerType.Prime, new Address("MT", true)),
              "4206 1301 1117 7881",
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

    assertEquals(icms, sale.getICMS(customer.getAddress(), total), 0.001);
  }

  @Test
  public void shippingCost() {
    sale = new Sale(date, customer, cardNumber, items);

    switch (customer.getAddress().getState()) {
      case "DF":
        assertEquals(5.0, sale.getShippingCost(customer.getAddress()), 0.001);
      case "GO": case "MT": case "MS":
        if(customer.getAddress().getIsCapital()) {
          assertEquals(10.0, sale.getShippingCost(customer.getAddress()), 0.001);
        } else {
          assertEquals(13.0, sale.getShippingCost(customer.getAddress()), 0.001);
        }
      case "BA": case "PE": case "CE": case "RN": case "PB": case "SE": case "AL": case "MA": case "PI":
        if(customer.getAddress().getIsCapital()) {
          assertEquals(15.0, sale.getShippingCost(customer.getAddress()), 0.001);
        } else {
          assertEquals(18.0, sale.getShippingCost(customer.getAddress()), 0.001);
        }
      case "AM": case "PA": case "AP": case "RO": case "RR": case "AC": case "TO":
        if(customer.getAddress().getIsCapital()) {
          assertEquals(20.0, sale.getShippingCost(customer.getAddress()), 0.001);
        } else {
          assertEquals(25.0, sale.getShippingCost(customer.getAddress()), 0.001);
        }
      case "SP": case "RJ": case "MG": case "ES":
        if(customer.getAddress().getIsCapital()) {
          assertEquals(7.0, sale.getShippingCost(customer.getAddress()), 0.001);
        } else {
          assertEquals(10.0, sale.getShippingCost(customer.getAddress()), 0.001);
        }
      case "PR": case "SC": case "RS":
        if(customer.getAddress().getIsCapital()) {
          assertEquals(10.0, sale.getShippingCost(customer.getAddress()), 0.001);
        } else {
          assertEquals(13.0, sale.getShippingCost(customer.getAddress()), 0.001);
        };
      default:
        if(customer.getAddress().getIsCapital()) {
          assertEquals(0.0, sale.getShippingCost(customer.getAddress()), 0.001);
        } else {
          assertEquals(0.0, sale.getShippingCost(customer.getAddress()), 0.001);
        };
    }
  }

  @Test
  public void municipalTax() {
    sale = new Sale(date, customer, cardNumber, items);
    double total = 0.0;
    
    for(int i = 0; i < items.size(); i++) {
      total += items.get(i).getSalePrice();
    }

    if (customer.getAddress().getState() == "DF") {
      assertEquals(0.0, sale.getMunicipalTax(customer.getAddress(), total), 0.001);
    } else {
      assertEquals(total*0.04, sale.getMunicipalTax(customer.getAddress(), total), 0.001);
    }
  }

  @Test
  public void saleTotal() {
    sale = new Sale(date, customer, cardNumber, items);
    double total = 0.0;

    for(int i = 0; i < items.size(); i++) {
      total+=items.get(i).getSalePrice();
    }

    double icms = total*sale.getICMS(customer.getAddress(), total);
    double municipalTax = total*sale.getMunicipalTax(customer.getAddress(), total);
    
    double shippingCost = sale.getShippingCost(customer.getAddress());
    shippingCost = customer.getType() == CustomerType.Prime ? 0.0 : shippingCost;
    shippingCost = customer.getType() == CustomerType.Special ? shippingCost*0.7 : shippingCost;
    
    total += icms + municipalTax + shippingCost;

    assertEquals(total, sale.getTotal(), 0.001);
  }

  @Test
  public void getCashback() {
    sale = new Sale(date, customer, cardNumber, items);

    double total = sale.getTotal();

    if (sale.getCustomer().getType() != CustomerType.Prime) {
      assertEquals(0.0, sale.getCashback());
      return;
    }

    Pattern companyCard = Pattern.compile("429613\\d{10}");
    Matcher cardMatcher = companyCard.matcher(sale.getCardNumber());
    if(cardMatcher.matches()) {
      assertEquals(0.05*sale.getTotal(), sale.getCashback());
      return;
    }

    assertEquals(0.03*sale.getTotal(), sale.getCashback());
  }
}