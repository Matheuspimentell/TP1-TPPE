package varejao.objects;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import varejao.objects.Customer.CustomerType;

public class Sale {
  private LocalDateTime date;
  private Customer customer;
  private String cardNumber;
  private List<Product> items;
  private double total;

  public Sale(LocalDateTime date, Customer customer, String cardNumber, List<Product> items) {
    this.date = date;
    this.customer = customer;
    this.cardNumber = cardNumber;
    this.items = items;
    recalculateTotal();
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

  public double getTotal() {
    return total;
  }

  public void recalculateTotal() {
    total = 0.0;
    for(int i = 0; i < items.size(); i++) {
      total+= items.get(i).getSalePrice();
    }
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
  public void addItem(Product item) {
    this.items.add(item);
    this.total += item.getSalePrice();
  }

  public double getICMS(Address address) {
    return address.getState() == "DF" ? 0.18 * total : 0.12 * total;
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
    return address.getState() == "DF" ? 0.0 : 0.04 * total; 
  }

  private boolean isCompanyCard(String cardNumber) {
    Pattern companyCard = Pattern.compile("429613\\d{10}");
    Matcher cardMatcher = companyCard.matcher(cardNumber);

    return cardMatcher.matches();
  }

  public double getCashback() {
    if (customer.getType() != CustomerType.Prime) {
      return 0.0;
    }

    if (this.isCompanyCard(this.cardNumber)) {
      return total*0.05;
    }

    return total*0.03;
  }

  public void checkout(double cashback) {

    double shippingCost = getShippingCost(customer.getAddress());
    shippingCost = customer.getType() == CustomerType.Prime ? 0.0 : shippingCost;
    shippingCost = customer.getType() == CustomerType.Special ? shippingCost*0.7 : shippingCost;

    double icms = getICMS(customer.getAddress());
    double municipalTax = getMunicipalTax(customer.getAddress());
    
    total += shippingCost+icms+municipalTax;
    total -= cashback;

    if (cashback > 0.0) {
      customer.setCashback(customer.getCashback()-cashback);
      customer.setCashback(getCashback());
    }
    
    customer.addOrder(this);
  }
}
