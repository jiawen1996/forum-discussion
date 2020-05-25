/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sr03.forumdiscussion.model;

import java.util.Date;

/**
 *
 * @author lounis
 */
public class Message {

    // une clé composite
    private MessageId messageId;
    private String content;
    private Date datePublication;
    //
    private User editor;
    private Forum destination;

    /*
     * java.text.SimpleDateFormat sdf = new
     * java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     * 
     * String currentTime = sdf.format(dt);
     */
    public Message() {
        this.datePublication = new Date();

    }

    public Message(String contenu, User editeur, Forum destination) {
        this.content = contenu;
        this.datePublication = new Date();
        this.editor = editeur;
        this.destination = destination;
        
    }

    public Forum getDestination() {
        return destination;
    }

    public void setDestination(Forum destination) {
        this.destination = destination;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDatePublication() {
        return datePublication;
    }

    public void setDatePublication(Date datePublication) {
        this.datePublication = datePublication;
    }

    public User getEditor() {
        return editor;
    }

    public void setEditor(User editor) {
        this.editor = editor;
    }

    public MessageId getMessageId() {
        return messageId;
    }

    public void setMessageId(MessageId messageId) {
        this.messageId = messageId;
    }

}
