package com.anodot.aggregation.strategy;

import com.anodot.api.City;
import com.anodot.api.DailyTemp;
import com.anodot.api.WeatherAPI;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public abstract class AbstractAggregationStrategy implements AggregationStrategy {

  private WeatherAPI weatherAPI;

  /**
   * Provide information about daily temperature group by city
   *
   * @param cities set of cities for getting data
   * @return map of city and list of daile temperature
   */
  protected Map<City, List<DailyTemp>> getDailyTemp(Set<City> cities) {
    ExecutorService executorService =
        Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    List<CompletableFuture<AbstractMap.SimpleEntry<City, List<DailyTemp>>>> completableFutures =
        cities.stream()
            .map(
                city ->
                    CompletableFuture.supplyAsync(
                        () ->
                            new AbstractMap.SimpleEntry<>(
                                city, getLastYearTemperature(city.getId())),
                        executorService))
            .collect(Collectors.toList());

    CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0])).join();

    return completableFutures.stream()
        .map(CompletableFuture::join)
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
  }

  private List<DailyTemp> getLastYearTemperature(String id) {
    return weatherAPI.getLastYearTemperature(id);
  }
}
