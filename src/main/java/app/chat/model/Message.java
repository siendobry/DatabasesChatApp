package app.chat.model;

import jakarta.persistence.*;

@Entity
@Table(name="MESSAGE")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int MessageID;

    @Column(name="CONTENT")
    private String content;

    public Message() {}

    public Message(String content) {
        this.content = content;
    }

    public int getMessageID() {
        return MessageID;
    }

    public void setMessageID(int messageID) {
        MessageID = messageID;
    }
}
