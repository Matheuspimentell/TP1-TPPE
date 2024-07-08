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
public class CustomerTest {
  Customer customer;
  
  String username;
  CustomerType type;
  Address address;

  @Parameters
  public static Collection<Object[]> getParameters() {
    Object[][] users = new Object[][] {
      {"joao", CustomerType.Default, new Address("DF", true)},
      {"marcos", CustomerType.Special, new Address("GO", false)},
      {"cristiano", CustomerType.Prime, new Address("RJ", true)},
      {"leonel", CustomerType.Special, new Address("SP", false)}
    };
    
    return Arrays.asList(users);
  }

  public CustomerTest(String username, CustomerType type, Address address) {
    this.username = username;
    this.type = type;
    this.address = address;
  }
  
  @Test
  public void createCustomer() {
    customer = new Customer(username, type, address);

    assertNotNull(customer);
    assertEquals(username, customer.getUsername());
    assertEquals(type, customer.getType());
    assertEquals(address, customer.getAddress());
  }

  @Test
  public void verifySpecialEligibility() {
    customer = new Customer(username, type, address);

    List<Sale> orders = customer.getOrders();

    LocalDateTime now = LocalDateTime.now();
    double total = 0.0;

    for (int i = 0; i < orders.size(); i++) {
      LocalDateTime orderDate = orders.get(i).getDate();
      if (orderDate.isAfter(now.minusMonths(1)) && orderDate.isBefore(now)) {
        total += orders.get(i).getTotal();
      }
    }

    boolean isSpecialEligible = total > 100.0;

    assertEquals(isSpecialEligible, customer.isSpecialEligible());
  }
}