package varejao.objects;

public class Product {
  private String itemCode;
  private String description;
  private double salePrice;
  private String unit;

  public Product(String itemCode, String description, double salePrice, String unit) {
    this.itemCode = itemCode;
    this.description = description;
    this.salePrice = salePrice;
    this.unit = unit;
  }

  // Getters
  public String getItemCode() {
    return itemCode;
  }

  public String getDescription() {
    return description;
  }

  public double getSalePrice() {
    return salePrice;
  }

  public String getUnit() {
    return unit;
  }

  // Setters
  public void setItemCode(String itemCode) {
    this.itemCode = itemCode;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setSalePrice(double salePrice) {
    this.salePrice = salePrice;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }
}
