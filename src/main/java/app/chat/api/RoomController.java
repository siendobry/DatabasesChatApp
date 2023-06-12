package app.chat.api;

import app.chat.model.Room;
import app.chat.model.User;
import app.chat.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;

    // TODO move somewhere else
    record RoomCreateRequest(
            User creator,
            String name,
            int capacity,
            String password
    ) {
    }

    @PostMapping("/create")
    public Room.RoomResponse createRoom(@RequestBody RoomCreateRequest req) {
        Room newRoom = new Room(req.name, req.capacity, req.password);
        newRoom.addUser(req.creator);
        return roomService.saveRoom(newRoom).convertToResponse();
    }

    @GetMapping("/{id}")
    public Room.RoomResponse getRoomById(@PathVariable int roomId) {
        return roomService.getRoomById(roomId).convertToResponse();
    }

    // what is this supposed to return??????
    @PostMapping("/{id}/join")
    @Transactional
    public Room.RoomResponse joinRoom(@RequestBody User user, @PathVariable int roomId) {
        Room room = roomService.getRoomById(roomId);
        if (room.noCurrentUsers() < room.getCapacity()) {
            room.addUser(user);
            return room.convertToResponse();
        } else {
            return null;
        }
    }

    @PostMapping("/{id}/leave")
    @Transactional
    public String leaveRoom(@PathVariable int roomId) {
        Room room = roomService.getRoomById(roomId);
        if (room.noCurrentUsers() == 1) {
            roomService.deleteRoom(roomId);
        }
        return "You have left the room";
    }
}
