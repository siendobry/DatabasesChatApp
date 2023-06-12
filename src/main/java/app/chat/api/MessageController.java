package app.chat.api;

import app.chat.model.Room;
import app.chat.model.User;
import app.chat.service.MessageService;
import app.chat.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;
    private final RoomService roomService;

    record MessageRequest(
            User user,
            Integer roomId,
            String message
    ) {
    }

    // idk if it would be the proper path for this
    @PostMapping("/send/{roomId}")
    public boolean sendMessage(@RequestBody String message, @PathVariable int roomName) {

    }

    @DeleteMapping("/delete/{roomName}/{messageId}")
    public boolean deleteMessage(@RequestBody String message, @PathVariable String roomName, @PathVariable int messageId) {

    }
}
