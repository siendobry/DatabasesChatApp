package app.chat.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.TreeSet;

@Data
@NoArgsConstructor
@Entity
@Table(name="users")
public class User implements Comparable<User> {

    @Id
    @Column(name="username", unique = true)
    private String username;

    @Column(name="password")
    private String password;

    @ManyToMany(mappedBy="users")
    private final TreeSet<Room> rooms = new TreeSet<>();


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public void joinRoom(Room room) {
        this.rooms.add(room);
        room.addUser(this);
    }

    public void sendMessage(Room room, Message message) {
        room.addMessage(message);
    }

    @Override
    public int compareTo(User o) {
        return this.username.compareTo(o.username);
    }

}
