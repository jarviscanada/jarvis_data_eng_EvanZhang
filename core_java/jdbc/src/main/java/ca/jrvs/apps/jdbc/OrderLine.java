package ca.jrvs.apps.jdbc;

import ca.jrvs.apps.jdbc.util.DataTransferObject;
import java.math.BigDecimal;

public class OrderLine implements DataTransferObject {

  private int orderQuantity;
  private String productCode;
  private String productName;
  private int productSize;
  private String productVariety;
  private BigDecimal productPrice;

  @Override
  public long getId() {
    return 0;
  }

  public long getOrderQuantity() {
    return orderQuantity;
  }

  public void setOrderQuantity(int orderQuantity) {
    this.orderQuantity = orderQuantity;
  }

  public String getProductCode() {
    return productCode;
  }

  public void setProductCode(String productCode) {
    this.productCode = productCode;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public long getProductSize() {
    return productSize;
  }

  public void setProductSize(int productSize) {
    this.productSize = productSize;
  }

  public String getProductVariety() {
    return productVariety;
  }

  public void setProductVariety(String productVariety) {
    this.productVariety = productVariety;
  }

  public BigDecimal getProductPrice() {
    return productPrice;
  }

  public void setProductPrice(BigDecimal productPrice) {
    this.productPrice = productPrice;
  }

  @Override
  public String toString() {
    return "OrderLine{" +
        "orderQuantity=" + orderQuantity +
        ", productCode='" + productCode + '\'' +
        ", productName='" + productName + '\'' +
        ", productSize=" + productSize +
        ", productVariety='" + productVariety + '\'' +
        ", productPrice=" + productPrice +
        '}';
  }
}
