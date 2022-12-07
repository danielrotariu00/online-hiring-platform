package com.licenta.databasemicroservice.persistence.repository;

import com.licenta.databasemicroservice.persistence.entity.City;
import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, Integer> {
    Iterable<City> findCitiesByCountryId(Integer country_id);
}
