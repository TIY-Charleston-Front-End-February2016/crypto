package com.theironyard.controllers;

import com.theironyard.entities.User;
import com.theironyard.services.CryptogramRepository;
import com.theironyard.services.UserRepository;
import com.theironyard.utils.PasswordStorage;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Created by PiratePowWow on 3/17/16.
 */
@RestController
public class CryptoController {
    @Autowired
    UserRepository users;
    @Autowired
    CryptogramRepository cryptograms;
    @PostConstruct
    public void init() throws SQLException, FileNotFoundException, PasswordStorage.CannotPerformOperationException {
        Server.createWebServer().start();
        File f = new File("testusers.csv");
        Scanner s = new Scanner(f);
        while(s.hasNext()){
            String[] testusers = s.nextLine().split(",");
            User user = new User(testusers[0], PasswordStorage.createHash(testusers[1]));
            users.save(user);
        }
    }

    @RequestMapping(path = "/user", method = RequestMethod.GET)
    public List<User> getUsers(){
        return (List<User>) users.findAll();
    }
    @RequestMapping(path = "/user", method = RequestMethod.POST)
    public void addUser(@RequestBody User user) throws PasswordStorage.CannotPerformOperationException {
        user.setPasswordHash(PasswordStorage.createHash(user.getPasswordHash()));
        users.save(user);
    }
    @RequestMapping(path = "/user/{id}", method = RequestMethod.PUT )
    public void editUser(@RequestBody User user, @PathVariable("id") int id){
        users.save(user);
    }
    @RequestMapping(path = "/user/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id") int id){
        users.delete(id);
    }
    @RequestMapping(path = "/user/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable("id") int id){
        return users.findOne(id);
    }
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public User login(@RequestBody User user, HttpSession session) throws Exception {
        if (!PasswordStorage.verifyPassword(user.getPasswordHash(), users.findOne(user.getId()).getPasswordHash())){
            user = users.findOne(user.getId());
            session.setAttribute("user", user);
        }else{
            throw new Exception("Incorrect Login Credentials");
        }
        return user;
    }
    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public void logout(HttpSession session){
        session.invalidate();
    }

}
