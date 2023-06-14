package app.chat.chat;

import app.chat.model.Message;
import app.chat.model.Room;
import app.chat.model.User;
import app.chat.service.RoomService;
import app.chat.service.UserService;
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
    private final UserService userService;

    record SendRequest(
            String sender,
            String message
    ) {
    }

    // server listens to /app/chatroom/r1
    @MessageMapping("/{roomId}")

    // TODO CHECK IF USER IS A MEMBER OF THE ROOM (!!!!!!!!!!!!!!!!!!!!!!!!!!!)
    // server broadcasts to all listening to /chatroom/r1
    @SendTo("/chatroom/{roomId}")
    @Transactional
    public Message receiveMessage(@DestinationVariable Integer roomId, @Payload SendRequest req) {
        Room room = roomService.getRoomById(roomId);
        User sender = userService.getUserByUsername(req.sender);
        if (room == null || sender == null || !isInRoom(req.sender, roomId)) {
            return null;
        }
        Message message = new Message(req.sender, req.message, new Date());
        room.addMessage(message);
        roomService.saveRoom(room);
        return message;
    }

    private boolean isInRoom(String username, Integer roomId) {
        return userService
                .getUserByUsername(username)
                .getRooms()
                .contains(roomService.getRoomById(roomId));
    }

}
