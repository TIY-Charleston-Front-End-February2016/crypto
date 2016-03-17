package com.theironyard.services;

import com.theironyard.entities.User;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by PiratePowWow on 3/17/16.
 */
public interface UserRepository extends CrudRepository<User, Integer> {
}
