package com.licenta.database.persistence.repositories;

import com.licenta.database.persistence.entities.City;
import org.springframework.data.repository.CrudRepository;

public interface CityRepository extends CrudRepository<City, Integer> {
}
