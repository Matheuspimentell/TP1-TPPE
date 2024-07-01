package varejao.objects;

import java.util.Objects;

public class Address {
  private String state;
  private boolean is_capital;

  public Address() {
    this.state = "DF";
    this.is_capital = true;
  }

  public Address(String state, boolean is_capital) {
    this.state = state;
    this.is_capital = is_capital;
  }

  // Getters
  public String getState() {
    return state;
  }

  public boolean GetIsCapital() {
    return is_capital;
  }

  // Setters
  public void setState(String state) {
    this.state = state;
  }

  public void setIsCapital(boolean is_capital) {
    this.is_capital = is_capital;
  }

  // Overrides
  @Override
  public String toString() {
    if (is_capital) {
      return "State: " + state + ", capital";
    } else {
      return "State: " + state + ", countryside";
    }
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    if (this == o) return true;
    Address address = (Address) o;
    return address.state == this.state && address.is_capital == this.is_capital;
  }

  @Override
  public int hashCode() {
    return Objects.hash(state, is_capital);
  }
}
