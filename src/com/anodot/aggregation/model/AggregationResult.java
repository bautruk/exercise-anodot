package com.anodot.aggregation.model;

import com.anodot.api.City;

import java.util.Map;

public class AggregationResult {

  private Map<City, Double> cityAggregatedTemperatureMap;
  private AggregationType type;

  public AggregationResult(Map<City, Double> cityAggregatedTemperatureMap, AggregationType type) {
    this.cityAggregatedTemperatureMap = cityAggregatedTemperatureMap;
    this.type = type;
  }

  public Map<City, Double> getCityAggregatedTemperatureMap() {
    return cityAggregatedTemperatureMap;
  }

  public AggregationType getType() {
    return type;
  }
}
