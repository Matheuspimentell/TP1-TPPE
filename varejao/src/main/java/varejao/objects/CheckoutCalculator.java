package varejao.objects;

import varejao.objects.Customer.CustomerType;

public class CheckoutCalculator {

  private double icms;
  private double shippingCost;
  private double municipalTax;
  private double generatedCashback;
  private Customer customer;
  private Address address;
  private Sale sale;

  public CheckoutCalculator(Sale sale) {
    this.sale = sale;
    this.generatedCashback = sale.getCashback();
    this.customer = sale.getCustomer();
    this.address = this.customer.getAddress();
    this.icms = sale.getICMS(this.address);
    this.shippingCost = sale.getShippingCost(this.address);
    this.municipalTax = sale.getMunicipalTax(this.address);
  }

  /**
   * Calculates and returns checkout amount for the current sale.
   *
   * @param usedCashback  the cashback amount that the customer will use in this checkout
   * @return  returns the sale checkout total amount of the sale
  */
  public double checkout(double usedCashback) {
    this.shippingCost = customer.getType() == CustomerType.Prime ? 0.0 : shippingCost; // If customer is a Prime customer, no shipping cost
    this.shippingCost = customer.getType() == CustomerType.Special ? shippingCost*0.7 : shippingCost; // If customer is a Special user, 30% off the shipping cost

    double total = 0;
    total += shippingCost + icms + municipalTax;
    total -= usedCashback; // remove the cashback amount the user wants to use in this checkout

    // Remove the cashback the user used in this checkout, and then add the generated cashback for this checkout
    if (usedCashback > 0.0) {
      customer.setCashback(customer.getCashback()-usedCashback);
      customer.setCashback(generatedCashback);
    }
    
    customer.addOrder(this.sale); // Add the order to the customer's order list
    return total;
  }
}
