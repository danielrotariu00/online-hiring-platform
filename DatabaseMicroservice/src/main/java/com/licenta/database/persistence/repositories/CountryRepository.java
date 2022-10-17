package com.licenta.database.persistence.repositories;

import com.licenta.database.persistence.entities.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, Integer>  {
}
