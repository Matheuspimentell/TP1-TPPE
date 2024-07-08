package varejao.objects;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Customer {
  enum CustomerType {
    Default,
    Special,
    Prime
  }
  
  private String username;
  private CustomerType type;
  private Address address;
  private double cashback;
  private List<Sale> orders;

  public Customer(String username, CustomerType type, Address address) {
    this.username = username;
    this.type = type;
    this.address = address;
    this.cashback = 0.0;
    this.orders = new ArrayList<Sale>();
  }

  // Getters
  public String getUsername() {
    return username;
  }

  public CustomerType getType() {
    return type;
  }

  public Address getAddress() {
    return address;
  }

  public double getCashback() {
    return cashback;
  }

  public List<Sale> getOrders() {
    return orders;
  }

  // Setters
  public void setUsername(String username) {
    this.username = username;
  }

  public void setType(CustomerType type) {
    this.type = type;
  }

  public void setAddress(Address address) {
    this.address.setState(address.getState());
    this.address.setIsCapital(address.getIsCapital());
  }

  public void setCashback(double cashback) {
    this.cashback = cashback;
  }

  public void setOrders(List<Sale> orders) {
    this.orders = orders;
  }

  // Other methods
  public void addOrder(Sale sale) {
    this.orders.add(sale);
  }
}
