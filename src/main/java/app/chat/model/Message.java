package app.chat.model;

import app.chat.util.MessageState;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="MESSAGES")
public class Message implements Comparable<Message> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int MessageID;

    @Column(name="CONTENT")
    private String content;

    @Column(name="SENT_DATE")
    private Date sentDate;

    @Column(name="STATE")
    private MessageState state;

    public Message() {}

    public Message(String content, Date sentDate, MessageState state) {
        this.content = content;
        this.sentDate = sentDate;
        this.state = state;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public MessageState getState() {
        return state;
    }

    public void setState(MessageState state) {
        this.state = state;
    }

    @Override
    public int compareTo(Message o) {
        return this.sentDate.compareTo(o.sentDate);
    }
}
