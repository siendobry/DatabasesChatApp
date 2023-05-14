package app.chat.model;

import jakarta.persistence.*;

import java.util.TreeSet;

@Entity
@Table(name="`USERS`")
public class User implements Comparable<User> {

    @Id
    @Column(name="`USERNAME`", unique = true)
    private String username;

    @Column(name="`PASSWORD`")
    private String password;

    @ManyToMany(mappedBy="users")
    private final TreeSet<Room> rooms = new TreeSet<>();

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        username = username;
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
