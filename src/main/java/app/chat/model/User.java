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
@Table(name="users")
public class User implements Comparable<User> {

    public record UserResponse(
            int UserID,
            String username,
            String password,
            Set<Integer> roomIds
    ) {
    }

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer UserID;

    @Column(name="username", unique = true, nullable = false)
    private String username;

    @Column(name="password", nullable = false)
    private String password;

    @ManyToMany(mappedBy="users")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private final Set<Room> rooms = new HashSet<>();


    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public boolean joinRoom(Room room, String password) {
        if (room.addUser(this, password)) {
            this.rooms.add(room);
            return true;
        }
        return false;
    }


    public boolean leaveRoom(Room room) {
        if (room.removeUser(this)) {
            rooms.remove(room);
            return true;
        }
        return false;
    }


    public void sendMessage(Room room, Message message) {
        room.addMessage(message);
    }

    public UserResponse convertToResponse() {
        Set<Integer> roomIds = rooms.stream()
                .map(Room::getRoomID)
                .collect(Collectors.toSet());
        return new UserResponse(
                this.UserID,
                this.username,
                this.password,
                roomIds
        );
    }

    @Override
    public int compareTo(User o) {
        if (this.username == null && o.username == null) {
            return 0;
        } else if (this.username == null) {
            return -1;
        } else if (o.username == null) {
            return 1;
        }
        return this.username.compareTo(o.username);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        User other = (User) obj;
        return Objects.equals(UserID, other.UserID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(UserID);
    }

}
