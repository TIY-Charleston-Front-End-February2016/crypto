package com.theironyard.services;

import com.theironyard.entities.Cryptogram;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by PiratePowWow on 3/17/16.
 */
public interface CryptogramRepository extends CrudRepository<Cryptogram, Integer> {
}
