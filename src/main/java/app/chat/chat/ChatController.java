package app.chat.chat;

import app.chat.model.Message;
import app.chat.model.Room;
import app.chat.model.User;
import app.chat.service.RoomService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import java.util.Date;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final RoomService roomService;


    // server listens to /app/chatroom/r1
    @MessageMapping("/{roomId}")

    // server broadcasts to all listening to /chatroom/r1
    @SendTo("/chatroom/{roomId}")
    @Transactional
    public Message receiveMessage(@DestinationVariable Integer roomId, @Payload Message message) {
        Room room = roomService.getRoomById(roomId);
        message.setSentDate(new Date());
        room.addMessage(message);
        roomService.saveRoom(room);
        return message;
    }

}
