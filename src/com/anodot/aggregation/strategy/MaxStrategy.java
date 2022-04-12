package com.anodot.aggregation.strategy;

import com.anodot.aggregation.model.AggregationResult;
import com.anodot.aggregation.model.AggregationType;
import com.anodot.api.City;
import com.anodot.api.DailyTemp;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Collectors;

public class MaxStrategy extends AbstractAggregationStrategy {

  @Override
  public boolean accepts(AggregationType type) {
    return AggregationType.MAX == type;
  }

  @Override
  public AggregationResult findFirstThree(Set<City> cities) {
    Map<City, List<DailyTemp>> dailyTempByCity = getDailyTemp(cities);
    Map<City, Double> cityTemperatureMap = findFirstThree(dailyTempByCity);
    return new AggregationResult(cityTemperatureMap, AggregationType.MAX);
  }

  private Map<City, Double> findFirstThree(Map<City, List<DailyTemp>> dailyTempByCity) {
    Map<City, OptionalDouble> aggregationResultByCity =
        dailyTempByCity.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, entry -> findMax(entry.getValue())));

    return aggregationResultByCity.entrySet().stream()
        .filter(entry -> entry.getValue().isPresent())
        .sorted(Map.Entry.comparingByValue(Comparator.comparingDouble(OptionalDouble::getAsDouble)))
        .limit(LIMIT)
        .collect(Collectors.toMap(Map.Entry::getKey, key -> key.getValue().getAsDouble()));
  }

  private OptionalDouble findMax(List<DailyTemp> temps) {
    return temps.stream().map(DailyTemp::getTemperature).mapToDouble(Double::doubleValue).max();
  }
}
