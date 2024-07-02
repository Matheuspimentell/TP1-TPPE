package varejao.objects;

public class SaleItem {
  private Product product;
  private int quantity;

  public SaleItem(Product product, int quantity) {
    this.product = product;
    this.quantity = quantity;
  }

  // Getters
  public Product getProduct() {
    return product;
  }

  public int getQuantity() {
    return quantity;
  }

  // Setters
  public void setProduct(Product product) {
    this.product = product;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public double getTotalPrice() {
    return product.getSalePrice() * quantity;
  }
}
