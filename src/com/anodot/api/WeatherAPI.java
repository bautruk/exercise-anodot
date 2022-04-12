package com.anodot.api;

import java.util.List;
import java.util.Set;

public interface WeatherAPI {
  Set<City> getAllCitiesByIds(Set<String> cityIds);
  List<DailyTemp> getLastYearTemperature(String cityId);
}
