package varejao.objects;

public class Customer {
  enum CustomerType {
    Default,
    Special,
    Prime
  }
  
  private String username;
  private CustomerType type;
  private Address address;

  public Customer(String username, CustomerType type, Address address) {
    this.username = username;
    this.type = type;
    this.address = address;    
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
}
