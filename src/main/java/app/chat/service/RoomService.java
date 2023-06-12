package app.chat.service;

import app.chat.model.Room;
import app.chat.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepository;

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    public Room getRoomById(Integer id) {
        return roomRepository.getReferenceById(id);
    }

    public void deleteRoom(Integer id) {roomRepository.deleteById(id);}
}
