package com.theironyard.controllers;

import com.theironyard.dataTransferObjects.CryptogramDto;
import com.theironyard.entities.Cryptogram;
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
    public List<User> getUsers(){
        return (List<User>) users.findAll();
    }
    @RequestMapping(path = "/users", method = RequestMethod.POST)
    public void addUser(@RequestBody User user) throws PasswordStorage.CannotPerformOperationException {
        user.setPasswordHash(PasswordStorage.createHash(user.getPasswordHash()));
        users.save(user);
    }
    @RequestMapping(path = "/users/{id}", method = RequestMethod.PUT )
    public void editUser(@RequestBody User user, @PathVariable("id") int id, HttpSession session){
        users.save(user);
    }
    @RequestMapping(path = "/users/{id}", method = RequestMethod.DELETE)
    public void deleteUser(@PathVariable("id") int id){
        users.delete(id);
    }
    @RequestMapping(path = "/users/{id}", method = RequestMethod.GET)
    public User getUser(@PathVariable("id") int id){
        return users.findOne(id);
    }
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public User login(@RequestBody User user, HttpSession session) throws Exception {
        if (PasswordStorage.verifyPassword(user.getPasswordHash(), users.findFirstByName(user.getName()).getPasswordHash())){
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
    @RequestMapping(path = "/cryptograms", method = RequestMethod.GET)
    public List<Cryptogram> getCryptograms(){
        return (List<Cryptogram>) cryptograms.findAll();
    }
    @RequestMapping(path = "/cryptograms/{sender_id}", method = RequestMethod.GET)
    public List<Cryptogram> getSenderCryptograms(@PathVariable("sender_id") int id){
        return cryptograms.findBySender(users.findOne(id));
    }
    @RequestMapping(path = "/cryptograms/{recipient_id}", method = RequestMethod.GET)
    public List<Cryptogram> getRecipientCryptograms(@PathVariable("recipient_id") int id){
        return cryptograms.findByRecipient(users.findOne(id));
    }
    @RequestMapping(path = "/cryptograms", method = RequestMethod.POST)
    public void addCryptogram(@RequestBody CryptogramDto cryptogramDto){
        User sender = users.findFirstByName(cryptogramDto.getSender());
        User recipient = users.findFirstByName((cryptogramDto.getRecipient()));
        Cryptogram cryptogram = new Cryptogram(generateScramble(cryptogramDto), cryptogramDto.getOriginalMessage(), cryptogramDto.getHint(), sender, recipient, false, LocalDateTime.now());
        cryptograms.save(cryptogram);
    }
    @RequestMapping(path = "/cryptograms/{id}", method = RequestMethod.PUT)
    public void editCryptogram(@RequestBody Cryptogram cryptogram, @PathVariable("id") int id){
        cryptograms.save(cryptogram);
    }
    @RequestMapping(path = "/cryptograms/{id}", method = RequestMethod.DELETE)
    public void deleteCryptogram(@PathVariable("id") int id){
        cryptograms.delete(id);
    }
    @RequestMapping(path = "/cryptograms/{id}", method = RequestMethod.GET)
    public Cryptogram getCryptogram(@PathVariable("id") int id){
        return cryptograms.findOne(id);
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
        System.out.println(cryptogram);

        return cryptogram;
    }
}
