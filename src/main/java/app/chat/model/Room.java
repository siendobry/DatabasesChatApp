package app.chat.model;

import jakarta.persistence.*;

import java.util.TreeSet;

@Entity
@Table(name="ROOMS")
public class Room implements Comparable<Room> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int RoomID;

    @Column(name="NAME")
    private String name;

    @Column(name="CAPACITY")
    private int capacity;

    @Column(name="PASSWORD")
    private String password;

    @ManyToMany
    private final TreeSet<User> users = new TreeSet<>();

    @OneToMany
    private final TreeSet<Message> messages = new TreeSet<>();

    public Room() {}

    public Room(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
