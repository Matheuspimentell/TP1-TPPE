package varejao.objects;

import java.util.List;

public class Sale {
  private List<SaleItem> items;
  private Customer customer;
  private PaymentMethod paymentMethod;
  private String date;

  public Sale(List<SaleItem> items, Customer customer, PaymentMethod paymentMethod, String date) {
    this.items = items;
    this.customer = customer;
    this.paymentMethod = paymentMethod;
    this.date = date;
  }

  // Getters
  public List<SaleItem> getItems() {
    return items;
  }

  public Customer getCustomer() {
    return customer;
  }

  public PaymentMethod getPaymentMethod() {
    return paymentMethod;
  }

  public String getDate() {
    return date;
  }

  // Métodos para cálculos de impostos, frete, descontos e cashback
  public double calculateTotal() {
    double total = 0.0;
    for (SaleItem item : items) {
      total += item.getTotalPrice();
    }
    return total;
  }

  public double calculateDiscount() {
    double discount = 0.0;
    if (customer.getType() == Customer.CustomerType.Special) {
      discount += calculateTotal() * 0.10;
      if (paymentMethod == PaymentMethod.CREDIT_CARD_COMPANY) {
        discount += calculateTotal() * 0.10;
      }
    }
    return discount;
  }

  public double calculateFreight() {
    Address address = customer.getAddress();
    double freight = 0.0;
    if (customer.getType() == Customer.CustomerType.Prime) {
      return 0.0;
    } else if (customer.getType() == Customer.CustomerType.Special) {
      // 30% de desconto no frete
      freight = calculateBaseFreight(address) * 0.70;
    } else {
      freight = calculateBaseFreight(address);
    }
    return freight;
  }

  private double calculateBaseFreight(Address address) {
    String state = address.getState();
    boolean isCapital = address.GetIsCapital();
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

  public double calculateICMS() {
    Address address = customer.getAddress();
    if (address.getState().equals("DF")) {
      return calculateTotal() * 0.18;
    } else {
      return calculateTotal() * 0.12;
    }
  }

  public double calculateMunicipalTax() {
    Address address = customer.getAddress();
    if (address.getState().equals("DF")) {
      return 0.0;
    } else {
      return calculateTotal() * 0.04;
    }
  }

  public double calculateCashback() {
    if (customer.getType() == Customer.CustomerType.Prime) {
      if (paymentMethod == PaymentMethod.CREDIT_CARD_COMPANY) {
        return calculateTotal() * 0.05;
      } else {
        return calculateTotal() * 0.03;
      }
    }
    return 0.0;
  }

  public double calculateFinalPrice() {
    return calculateTotal() - calculateDiscount() + calculateFreight() + calculateICMS() + calculateMunicipalTax() - calculateCashback();
  }
}
