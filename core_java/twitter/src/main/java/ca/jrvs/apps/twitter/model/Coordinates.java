package ca.jrvs.apps.twitter.model;

import java.util.List;

public class Coordinates {
  private List<Float> coordinates;
  private String type;


  public List<Float> getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(List<Float> coordinates) {
    this.coordinates = coordinates;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}
