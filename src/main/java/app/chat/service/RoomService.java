package app.chat.service;

import app.chat.model.Room;
import app.chat.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room getRoomById(Integer id) {
        return roomRepository.getReferenceById(id);
    }

}
