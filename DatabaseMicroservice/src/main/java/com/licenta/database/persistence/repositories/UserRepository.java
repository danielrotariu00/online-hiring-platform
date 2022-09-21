package com.licenta.database.persistence.repositories;

import com.licenta.database.persistence.models.UserModel;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserModel, String> {
}
