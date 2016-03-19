package com.theironyard.dataTransferObjects;

/**
 * Created by PiratePowWow on 3/19/16.
 */
public class CryptogramDto {
    String originalMessage;
    String hint;
    String sender;
    String recipient;

    public CryptogramDto(String originalMessage, String hint, String sender, String recipient) {
        this.originalMessage = originalMessage;
        this.hint = hint;
        this.sender = sender;
        this.recipient = recipient;
    }

    public CryptogramDto() {
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
}
