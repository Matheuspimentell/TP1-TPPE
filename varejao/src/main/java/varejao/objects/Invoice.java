package varejao.objects;

public class Invoice {
  private Sale sale;

  public Invoice(Sale sale) {
    this.sale = sale;
  }

  public void printInvoice() {
    System.out.println("Invoice for Customer: " + sale.getCustomer().getUsername());
    System.out.println("Date: " + sale.getDate());
    System.out.println("Items:");
    for (SaleItem item : sale.getItems()) {
      System.out.println("  - " + item.getProduct().getDescription() + ": " + item.getQuantity() + " x " + item.getProduct().getSalePrice() + " = " + item.getTotalPrice());
    }
    System.out.println("Total: " + sale.calculateTotal());
    System.out.println("Discount: " + sale.calculateDiscount());
    System.out.println("Freight: " + sale.calculateFreight());
    System.out.println("ICMS: " + sale.calculateICMS());
    System.out.println("Municipal Tax: " + sale.calculateMunicipalTax());
    System.out.println("Cashback: " + sale.calculateCashback());
    System.out.println("Final Price: " + sale.calculateFinalPrice());
  }
}
