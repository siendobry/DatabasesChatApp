package app.chat.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
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

    // TODO add constraint/check - capacity > 1
    @Column(name="capacity", nullable = false)
    private int capacity;

    @Column(name="password")
    private String password;

    @ManyToMany
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private final Set<User> users = new HashSet<>();

    @OneToMany
    private final Set<Message> messages = new HashSet<>();


    public Room(String name, int capacity, String password) {
        this.name = name;
        this.capacity = capacity;
        this.password = password;
    }


    public boolean addUser(User user, String password) {
        if (Objects.equals(this.password, password) && this.users.size() < this.capacity) {
            this.users.add(user);
            return true;
        }
        return false;
    }

    public boolean removeUser(User user) {
        if (users.contains(user)) {
            users.remove(user);
            return true;
        }
        return false;
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
        if (this.name == null && o.name == null) {
            return 0;
        } else if (this.name == null) {
            return -1;
        } else if (o.name == null) {
            return 1;
        }
        return Objects.compare(this.name, o.name, String::compareTo);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Room other = (Room) obj;
        return Objects.equals(RoomID, other.RoomID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(RoomID);
    }

}
