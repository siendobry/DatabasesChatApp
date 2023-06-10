package app.chat.model;

import app.chat.util.MessageState;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Entity
@Table(name="messages")
public class Message implements Comparable<Message> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="message_id")
    private int MessageID;

    @Column(name="content")
    private String content;

    @Column(name="sent_date")
    private Date sentDate;

    @Column(name="state")
    private MessageState state;


    public Message(String content, Date sentDate, MessageState state) {
        this.content = content;
        this.sentDate = sentDate;
        this.state = state;
    }


    @Override
    public int compareTo(Message o) {
        return this.sentDate.compareTo(o.sentDate);
    }

}
