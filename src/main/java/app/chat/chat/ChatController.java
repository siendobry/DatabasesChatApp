package app.chat.chat;

import app.chat.model.Message;
import app.chat.model.Room;
import app.chat.service.RoomService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final RoomService roomService;


    // server listens to /app/chatroom/r1
    @MessageMapping("/r{param}")

    // server broadcasts to all listening to /chatroom/r1
    @SendTo("/chatroom/r{param}")
    @Transactional
    public Message receiveMessage(@DestinationVariable String param, @Payload Message message) {

        int room_id = Integer.parseInt(param);
        Room room = roomService.getRoomById(room_id);
        room.addMessage(message);
        roomService.saveRoom(room);

        return message;
    }

}
