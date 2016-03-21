package com.theironyard.entities;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Created by PiratePowWow on 3/17/16.
 */
@Entity
public class Cryptogram {
    @Id
    @GeneratedValue
    int id;
    @NotNull
    String scramble;
    @NotNull
    String originalMessage;
    String hint;
    @NotNull
    @ManyToOne
    User sender;
    @NotNull
    @ManyToOne
    User recipient;
    Boolean isSolved;
    @NotNull
    LocalDateTime timeStamp;

    public Cryptogram(String scramble, String originalMessage, String hint, User sender, User recipient, Boolean isSolved, LocalDateTime timeStamp) {
        this.scramble = scramble;
        this.originalMessage = originalMessage;
        this.hint = hint;
        this.sender = sender;
        this.recipient = recipient;
        this.isSolved = isSolved;
        this.timeStamp = timeStamp;
    }

    public Cryptogram() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getScramble() {
        return scramble;
    }

    public void setScramble(String scramble) {
        this.scramble = scramble;
    }

    public String getOriginalMessage() {
        return originalMessage;
    }

    public void setOriginalMessage(String originalMessage) {
        this.originalMessage = originalMessage;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public Boolean getSolved() {
        return isSolved;
    }

    public void setSolved(Boolean solved) {
        isSolved = solved;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
