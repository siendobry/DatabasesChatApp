package app.chat.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@Entity
@Table(name="rooms")
public class Room implements Comparable<Room> {

    public record RoomResponse(
            int RoomID,
            String name,
            int capacity,
            Set<String> usernames,
            Set<Message> messages
    ) {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="room_id")
    private int RoomID;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="capacity", nullable = false, columnDefinition = "CHECK (capacity > 1)")
    private int capacity;

    @Column(name="password")
    private String password;

    @ManyToMany
    private final Set<User> users = new TreeSet<>();

    @OneToMany
    private final Set<Message> messages = new TreeSet<>();


    public Room(String name, int capacity, String password) {
        this.name = name;
        this.capacity = capacity;
        this.password = password;
    }


    public void addUser(User user) {
        this.users.add(user);
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    public int noCurrentUsers() {return users.size();}

    public RoomResponse convertToResponse() {
        Set<String> usernames = users.stream()
                .map(User::getUsername)
                .collect(Collectors.toSet());
        return new RoomResponse(
                this.getRoomID(),
                this.getName(),
                this.getCapacity(),
                usernames,
                this.getMessages()
        );
    }

    @Override
    public int compareTo(Room o) {
        return this.name.compareTo(o.name);
    }

}
