package app.chat.model;

import jakarta.persistence.*;

@Entity
@Table(name="ROOM")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int RoomID;

    @Column(name="CAPACITY")
    private int capacity;

    @Column(name="PASSWORD")
    private String password;

    public Room() {}

    public Room(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

}
