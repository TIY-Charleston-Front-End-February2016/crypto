package com.theironyard.services;

import com.theironyard.entities.Cryptogram;
import com.theironyard.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by PiratePowWow on 3/17/16.
 */
public interface CryptogramRepository extends CrudRepository<Cryptogram, Integer> {
    List<Cryptogram> findBySender(User sender);
    List<Cryptogram> findByRecipient(User recipient);
}
