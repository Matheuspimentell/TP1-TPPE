package varejao.objects;

import java.time.LocalDateTime;
import java.util.List;

public class Sale {
  private LocalDateTime date;
  private Customer customer;
  private String cardNumber;
  private List<Product> items;

  public Sale(LocalDateTime date, Customer customer, String cardNumber, List<Product> items) {
    this.date = date;
    this.customer = customer;
    this.cardNumber = cardNumber;
    this.items = items;
  }

  // Getters
  public LocalDateTime getDate() {
    return date;
  }

  public Customer getCustomer() {
    return customer;
  }

  public String getCardNumber() {
    return cardNumber;
  }

  public List<Product> getItems() {
    return items;
  }

  // Setters
  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public void setCardNumber(String cardNumber) {
    this.cardNumber = cardNumber;
  }

  public void setItems(List<Product> items) {
    this.items = items;
  }
}
