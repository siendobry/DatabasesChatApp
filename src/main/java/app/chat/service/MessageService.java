package app.chat.service;

import app.chat.model.Message;
import app.chat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

}
