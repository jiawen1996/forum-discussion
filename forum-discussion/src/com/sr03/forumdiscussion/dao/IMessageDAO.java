package com.sr03.forumdiscussion.dao;

import com.sr03.forumdiscussion.model.*;

public interface IMessageDAO<Message> {
    public MessageId _insert(String content, Integer idUser, Integer idForum);

    public void _update(Message f);

    public void _delete(Message f);
}
