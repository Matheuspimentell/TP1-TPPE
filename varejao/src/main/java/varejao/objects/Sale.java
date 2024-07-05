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

  // Other methods
  public double getICMS(Address address) {
    return address.getState() == "DF" ? 0.18 : 0.12;
  }

  public double getShippingCost(Address address) {
    String state = address.getState();
    boolean isCapital = address.getIsCapital();
    switch (state) {
      case "DF":
        return 5.0;
      case "GO": case "MT": case "MS":
        return isCapital ? 10.0 : 13.0;
      case "BA": case "PE": case "CE": case "RN": case "PB": case "SE": case "AL": case "MA": case "PI":
        return isCapital ? 15.0 : 18.0;
      case "AM": case "PA": case "AP": case "RO": case "RR": case "AC": case "TO":
        return isCapital ? 20.0 : 25.0;
      case "SP": case "RJ": case "MG": case "ES":
        return isCapital ? 7.0 : 10.0;
      case "PR": case "SC": case "RS":
        return isCapital ? 10.0 : 13.0;
      default:
        return 0.0;
    }
  }

  public double getMunicipalTax(Address address) {
    return address.getState() == "DF" ? 0.0 : 0.04; 
  }
}
