package app.chat.api;

import app.chat.model.Room;
import app.chat.model.User;
import app.chat.service.RoomService;
import app.chat.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {
    private final RoomService roomService;
    private final UserService userService;

    // TODO move somewhere else
    record RoomCreateRequest(
            User creator,
            String name,
            int capacity,
            String password
    ) {
    }

    record JoinRequest(
            User user,
            String password
    ) {
    }

    // TODO move room instance creation to RoomService
    @PostMapping("/create")
    public Room.RoomResponse createRoom(@RequestBody RoomCreateRequest req) {
        Room newRoom = new Room(req.name, req.capacity, req.password);
        User user = userService.getUserByUsername(req.creator.getUsername());
        newRoom.addUser(user, req.password);
        return roomService.saveRoom(newRoom).convertToResponse();
    }

    @GetMapping("/{id}")
    public Room.RoomResponse getRoomById(@PathVariable int id) {
        return roomService.getRoomById(id).convertToResponse();
    }

    // what is this supposed to return??????
    @PostMapping("/{id}/join")
    @Transactional
    public Room.RoomResponse joinRoom(@RequestBody JoinRequest req, @PathVariable int id) {
        Room room = roomService.getRoomById(id);
        User user = userService.getUserByUsername(req.user.getUsername());
        if (user.joinRoom(room, req.password)) {
            return roomService.updateRoom(room).convertToResponse();
        } else {
            return null;
        }
    }

    @PostMapping("/{id}/leave")
    @Transactional
    public String leaveRoom(@RequestBody User passedUser, @PathVariable int id) {
        Room room = roomService.getRoomById(id);
        User user = userService.getUserByUsername(passedUser.getUsername());
        if (user.leaveRoom(room)) {
            if (room.noCurrentUsers() == 0) {
                roomService.deleteRoom(id);
            }
            return "You have left the room";
        }
        return "Could not leave the room";
    }
}
