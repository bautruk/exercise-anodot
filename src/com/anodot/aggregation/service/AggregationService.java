package com.anodot.aggregation.service;

import com.anodot.aggregation.model.AggregationResult;
import com.anodot.aggregation.model.AggregationType;
import com.anodot.aggregation.strategy.AggregationFactory;
import com.anodot.aggregation.strategy.AggregationStrategy;
import com.anodot.api.City;
import com.anodot.api.WeatherAPI;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class AggregationService {

  public static final int MINIMAL_POPULATION = 50_000;

  private final AggregationFactory aggregationFactory = new AggregationFactory();
  private WeatherAPI weatherAPI;

  /**
   * Provide functionality to getting top 3 cities based on aggregation type from requested cities
   *
   * @param cityIds set of city ids
   * @param type of aggregation function
   * @return collection of top 3 cities with the aggregated temperature
   */
  public AggregationResult getTopThreeCitiesByAggregationType(Set<String> cityIds, AggregationType type) {
    if (cityIds == null || cityIds.size() == 0) {
      return new AggregationResult(Collections.emptyMap(), type);
    }

    Set<City> cities = weatherAPI.getAllCitiesByIds(cityIds);
    Set<City> citiesOverMinimalPopulation =
        cities.stream()
            .filter(city -> city.getPopulation() > MINIMAL_POPULATION)
            .collect(Collectors.toSet());

    AggregationStrategy strategy = aggregationFactory.getStrategy(type);

    return strategy.findFirstThree(citiesOverMinimalPopulation);
  }
}
