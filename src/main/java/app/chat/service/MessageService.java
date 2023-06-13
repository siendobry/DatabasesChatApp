package app.chat.service;

import app.chat.model.Message;
import app.chat.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }
    public void removeMessage(Message message){messageRepository.deleteById(message.getMessageID());}

}
