package com.anodot.aggregation.strategy;

import com.anodot.aggregation.model.AggregationResult;
import com.anodot.aggregation.model.AggregationType;
import com.anodot.api.City;

import java.util.List;
import java.util.Set;

public interface AggregationStrategy {

  int LIMIT = 3;

  boolean accepts(AggregationType type);

  /**
   * Return first 3 cities based on aggregation type
   *
   * @param cities list of cities
   * @return first 3 cites by aggregation function
   */
  AggregationResult findFirstThree(Set<City> cities);
}
