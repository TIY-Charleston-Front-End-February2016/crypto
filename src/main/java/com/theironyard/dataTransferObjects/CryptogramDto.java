package com.theironyard.dataTransferObjects;

import com.theironyard.entities.User;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Created by PiratePowWow on 3/19/16.
 */
public class CryptogramDto {
    String originalMessage;
    String hint;
    String sender;
    String recipient;
    String timeStamp;

    public CryptogramDto(String originalMessage, String hint, String sender, String recipient, String timeStamp) {
        this.originalMessage = originalMessage;
        this.hint = hint;
        this.sender = sender;
        this.recipient = recipient;
        this.timeStamp = timeStamp;
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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
