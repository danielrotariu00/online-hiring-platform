package com.licenta.database.persistence.repositories;

import com.licenta.database.persistence.models.Country;
import org.springframework.data.repository.CrudRepository;

public interface CountryRepository extends CrudRepository<Country, String>  {
}
