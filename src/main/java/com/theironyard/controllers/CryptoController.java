package com.theironyard.controllers;

import com.theironyard.dataTransferObjects.CryptogramDto;
import com.theironyard.entities.Cryptogram;
import com.theironyard.entities.User;
import com.theironyard.services.CryptogramRepository;
import com.theironyard.services.UserRepository;
import com.theironyard.utils.PasswordStorage;
import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

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

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public ResponseEntity<Object> getUsers(HttpSession session){
        if (session.getAttribute("user")== null){
            return new ResponseEntity<Object>("Login Required To View Users", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<Object>((List<User>) users.findAll(),HttpStatus.OK);
    }
    @RequestMapping(path = "/users", method = RequestMethod.POST)
    public ResponseEntity<Object> addUser(@RequestBody User user) throws PasswordStorage.CannotPerformOperationException {
        if(user.getName().isEmpty()||user.getPasswordHash().isEmpty()||user.getPasswordHash()==null||user.getName()==null||user.getId()!=0){
            return new ResponseEntity<Object>("Incomplete Or Incorrect User Information", HttpStatus.BAD_REQUEST);
        }
        try {
            user.setPasswordHash(PasswordStorage.createHash(user.getPasswordHash()));
        }catch (Exception x){
            return new ResponseEntity<Object>("Error Creating Password Hash: " + x.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try {
            users.save(user);
        }catch (Exception x){
            return new ResponseEntity<Object>("Error Saving New User To Database: " + x.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Object>(user, HttpStatus.OK);
    }
    @RequestMapping(path = "/users/{id}", method = RequestMethod.PUT )
    public ResponseEntity<Object> editUser(@RequestBody User user, @PathVariable("id") int id, HttpSession session){
        User loggedInUser = (User) session.getAttribute("user");
        if(loggedInUser == null){
            return new ResponseEntity<Object>("Must Be Logged In To Edit Your User", HttpStatus.UNAUTHORIZED);
        }
        else if(loggedInUser.getId() != user.getId()){
            return new ResponseEntity<Object>("Must Be Logged In As Same User Being Edited", HttpStatus.UNAUTHORIZED);
        }
        if(user.getId()==0 || user.getPasswordHash()==null || user.getName()==null||user.getName().isEmpty()||user.getPasswordHash().isEmpty()){
            return new ResponseEntity<Object>("Incomplete User Object Submitted to Server", HttpStatus.BAD_REQUEST);
        }
        try {
            users.save(user);
        }catch (Exception x){
            return new ResponseEntity<Object>("Error Editing User In Database: " + x.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Object>(user, HttpStatus.OK);
    }
    @RequestMapping(path = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteUser(@PathVariable("id") int id, HttpSession session){
        User loggedInUser = (User) session.getAttribute("user");
        if(loggedInUser == null){
            return new ResponseEntity<Object>("Must Be Logged In To Delete Your User", HttpStatus.UNAUTHORIZED);
        }
        else if(loggedInUser.getId() != id){
            return new ResponseEntity<Object>("Must Be Logged In As Same User Being Deleted", HttpStatus.UNAUTHORIZED);
        }
        try {
            users.delete(id);
        }catch (Exception x){
            return new ResponseEntity<Object>("Error Deleting User From Database: ", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Object>("User Deletion Successful", HttpStatus.OK);
    }
    @RequestMapping(path = "/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getUser(@PathVariable("id") int id, HttpSession session){
        User loggedInUser = (User) session.getAttribute("user");
        if(loggedInUser == null){
            return new ResponseEntity<Object>("Must Be Logged In To View A User", HttpStatus.UNAUTHORIZED);
        }
        User user = users.findOne(id);
        if (user == null){
            return new ResponseEntity<Object>("User Not Found In Database", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Object>(user, HttpStatus.OK);
    }
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<Object> login(@RequestBody User user, HttpSession session) throws Exception {
        if(user.getName() == null || user.getPasswordHash() == null||user.getName().isEmpty()||user.getPasswordHash().isEmpty()){
            return new ResponseEntity<Object>("Both User Name And Password Fields Must Be Filled In", HttpStatus.BAD_REQUEST);
        }
        User loginUser = users.findFirstByName(user.getName());
        if (PasswordStorage.verifyPassword(user.getPasswordHash(), loginUser.getPasswordHash())){
            user = loginUser;
            session.setAttribute("user", user);
        }else{
            return new ResponseEntity<Object>("Incorrect Login Credentials", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<Object>(user, HttpStatus.OK);
    }
    @RequestMapping(path = "/logout", method = RequestMethod.POST)
    public ResponseEntity<Object> logout(HttpSession session){
        try {
            session.invalidate();
        }catch (Exception x){
            return new ResponseEntity<Object>("Error Logging Out: " + x.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Object>("Logout Successful", HttpStatus.OK);
    }
    @RequestMapping(path = "/cryptograms", method = RequestMethod.GET)
    public ResponseEntity<Object> getCryptograms(HttpSession session){
        User loggedInUser = (User) session.getAttribute("user");
        if(loggedInUser == null){
            return new ResponseEntity<Object>("Must Be Logged In To View Cryptograms", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<Object>((List<Cryptogram>) cryptograms.findAll(), HttpStatus.OK);
    }
    @RequestMapping(path = "/cryptograms/{sender_id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getSenderCryptograms(@PathVariable("sender_id") int id, HttpSession session){
        User loggedInUser = (User) session.getAttribute("user");
        if(loggedInUser == null){
            return new ResponseEntity<Object>("Must Be Logged In To Retrieve Sender Cryptograms", HttpStatus.UNAUTHORIZED);
        }
        User sender = users.findOne(id);
        if(sender==null){
            return new ResponseEntity<Object>("Sender Not Found In Database", HttpStatus.NOT_FOUND);
        }else if(sender.getId()!=loggedInUser.getId()){
            return new ResponseEntity<Object>("Logged In User ID Does Not Match Sender ID", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<Object>(cryptograms.findBySender(sender), HttpStatus.OK);
    }
    @RequestMapping(path = "/cryptograms/{recipient_id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getRecipientCryptograms(@PathVariable("recipient_id") int id, HttpSession session){
        User loggedInUser = (User) session.getAttribute("user");
        if(loggedInUser == null){
            return new ResponseEntity<Object>("Must Be Logged In To Retrieve Recipient Cryptograms", HttpStatus.UNAUTHORIZED);
        }
        User recipient = users.findOne(id);
        if(recipient==null){
            return new ResponseEntity<Object>("Recipient Not Found In Database", HttpStatus.NOT_FOUND);
        }else if(recipient.getId()!=loggedInUser.getId()){
            return new ResponseEntity<Object>("Logged In User ID Does Not Match Recipient ID", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<Object>(cryptograms.findByRecipient(recipient), HttpStatus.OK);
    }
    @RequestMapping(path = "/cryptograms", method = RequestMethod.POST)
    public ResponseEntity<Object> addCryptogram(@RequestBody CryptogramDto cryptogramDto, HttpSession session){
        if(cryptogramDto.getSender()==null||cryptogramDto.getRecipient()==null||cryptogramDto.getOriginalMessage()==null){
            return new ResponseEntity<Object>("Incomplete cryptogramDto Parameters", HttpStatus.BAD_REQUEST);
        }
        User loggedInUser = (User) session.getAttribute("user");
        if(loggedInUser == null){
            return new ResponseEntity<Object>("Must Be Logged In To Add A Cryptogram", HttpStatus.UNAUTHORIZED);
        }
        User sender = users.findFirstByName(cryptogramDto.getSender());
        if (sender.getId()!=loggedInUser.getId()){
            return new ResponseEntity<Object>("Sender Must Be Same As Logged In User", HttpStatus.UNAUTHORIZED);
        }
        User recipient = users.findFirstByName((cryptogramDto.getRecipient()));
        if (recipient == null){
            return new ResponseEntity<Object>("Recipient Could Not Be Found In Database", HttpStatus.NOT_FOUND);
        }
        Cryptogram cryptogram = new Cryptogram(generateScramble(cryptogramDto), cryptogramDto.getOriginalMessage(), cryptogramDto.getHint(), sender, recipient, false, LocalDateTime.now());
        try {
            cryptograms.save(cryptogram);
        }catch (Exception x){
            return new ResponseEntity<Object>("Error Saving New Cryptogram To Database: " + x.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Object>(cryptogram, HttpStatus.OK);
    }
    @RequestMapping(path = "/cryptograms/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> editCryptogram(@RequestBody Cryptogram cryptogram, @PathVariable("id") int id, HttpSession session){
        User loggedInUser = (User) session.getAttribute("user");
        if(loggedInUser == null){
            return new ResponseEntity<Object>("Must Be Logged In To Edit A Cryptogram", HttpStatus.UNAUTHORIZED);
        }
        try {
            cryptograms.save(cryptogram);
        }catch (Exception x){
            return new ResponseEntity<Object>("Error Editing Cryptogram In Database: " + x.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Object>(cryptogram, HttpStatus.OK);
    }
    @RequestMapping(path = "/cryptograms/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteCryptogram(@PathVariable("id") int id, HttpSession session){
        User loggedInUser = (User) session.getAttribute("user");
        if(loggedInUser == null){
            return new ResponseEntity<Object>("Must Be Logged In To Delete A Cryptogram", HttpStatus.UNAUTHORIZED);
        }
        if(loggedInUser.getId()!=cryptograms.findOne(id).getSender().getId()){
            return new ResponseEntity<Object>("User ID Does Not Match Cryptogram Sender ID", HttpStatus.UNAUTHORIZED);
        }
        try {
            cryptograms.delete(id);
        }catch(Exception x){
            return new ResponseEntity<Object>("Error Deleting Cryptogram From Database: " + x.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Object>("Cryptogram Deletion Successful", HttpStatus.OK);
    }
    @RequestMapping(path = "/cryptograms/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> getCryptogram(@PathVariable("id") int id, HttpSession session){
        User loggedInUser = (User) session.getAttribute("user");
        if(loggedInUser == null){
            return new ResponseEntity<Object>("Must Be Logged In To View A Cryptogram", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<Object>(cryptograms.findOne(id), HttpStatus.OK);
    }
    public String generateScramble(CryptogramDto cryptogramDto){
        String originalMessage = cryptogramDto.getOriginalMessage().toLowerCase();
        String[] alphabetPrim ={"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
        ArrayList<String> alphabet = new ArrayList<>(Arrays.asList(alphabetPrim));
        HashMap<String, String> cipher = new HashMap<>();
        int a;
        for(a = 0;a < alphabet.size();a++){
            cipher.put(alphabet.get(a), "");
        }
        for (String letter: cipher.keySet()){
            Random random = new Random();
            int rand = random.nextInt(alphabet.size());
            cipher.put(letter, alphabet.get(rand));
            alphabet.remove(alphabet.get(rand));
        }
        int i;
        ArrayList<String> cryptogramArr = new ArrayList();
        for(i = 0;i < originalMessage.length();i++){
            if(originalMessage.charAt(i)!=' '){
                cryptogramArr.add(String.valueOf(cipher.get(String.valueOf(originalMessage.charAt(i))).charAt(0)));
            }else{
                cryptogramArr.add(" ");
            }
        }
        String cryptogram = "";
        for(String l:cryptogramArr){
            cryptogram += l;
        }
        return cryptogram;
    }
}
