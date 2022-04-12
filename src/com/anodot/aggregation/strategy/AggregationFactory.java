package com.anodot.aggregation.strategy;

import com.anodot.aggregation.model.AggregationType;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AggregationFactory {

  private final List<AggregationStrategy> aggregationStrategies;

  public AggregationFactory() {
    aggregationStrategies = Stream.of(new MaxStrategy()).collect(Collectors.toList());
  }

  /**
   * Provide strategy based on aggregation type
   *
   * @param type of aggregation
   * @return strategy for make aggregation functionality
   */
  public AggregationStrategy getStrategy(AggregationType type) {
    if (type == null) {
      throw new UnsupportedOperationException("Type can't be null value");
    }

    Set<AggregationStrategy> strategies =
        aggregationStrategies.stream()
            .filter(accessor -> accessor.accepts(type))
            .collect(Collectors.toSet());

    validate(strategies, type);

    return strategies.iterator().next();
  }

  private void validate(Set<AggregationStrategy> strategies, AggregationType type) {
    int sizeOfSuitableStrategies = strategies.size();
    if (sizeOfSuitableStrategies == 0) {
      throw new UnsupportedOperationException(
          String.format("Can't find strategy for %s type", type));
    }
    if (sizeOfSuitableStrategies > 1) {
      throw new UnsupportedOperationException(
          String.format("Found more than one strategy for %s type", type));
    }
  }
}
