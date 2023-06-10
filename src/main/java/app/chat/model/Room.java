package app.chat.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.TreeSet;

@Data
@NoArgsConstructor
@Entity
@Table(name="rooms")
public class Room implements Comparable<Room> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="room_id")
    private int RoomID;

    @Column(name="name")
    private String name;

    @Column(name="capacity")
    private int capacity;

    @Column(name="password")
    private String password;

    @ManyToMany
    private final TreeSet<User> users = new TreeSet<>();

    @OneToMany
    private final TreeSet<Message> messages = new TreeSet<>();


    public Room(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }


    public void addUser(User user) {
        this.users.add(user);
    }

    public void addMessage(Message message) {
        this.messages.add(message);
    }

    @Override
    public int compareTo(Room o) {
        return this.name.compareTo(o.name);
    }

}
