package com.anodot.api;

import java.util.Objects;

public class City {
  private String id;
  private String name;
  private int population;

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public int getPopulation() {
    return population;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    City city = (City) o;
    return Objects.equals(id, city.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, population);
  }
}
