package com.licenta.database.persistence.repositories;

import com.licenta.database.persistence.entities.UserDetails;
import org.springframework.data.repository.CrudRepository;

public interface UserDetailsRepository extends CrudRepository<UserDetails, String>  {
}
