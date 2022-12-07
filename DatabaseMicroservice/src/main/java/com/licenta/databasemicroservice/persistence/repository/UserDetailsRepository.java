package com.licenta.databasemicroservice.persistence.repository;

import com.licenta.databasemicroservice.persistence.entity.UserDetails;
import org.springframework.data.repository.CrudRepository;

public interface UserDetailsRepository extends CrudRepository<UserDetails, Long>  {
}
