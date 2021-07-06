package ru.demmax93.sweater.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@ToString(of = {"id", "text", "tag"})
public class Message {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "Please fill the message")
    @Length(max = 2048, message = "The message too long (more than 2kB")
    private String text;
    @NotBlank(message = "Please fill the tag")
    @Length(max = 255, message = "The tag too long (more than 255")
    private String tag;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;
    private String filename;
    @ManyToMany
    @JoinTable(
            name = "message_like",
            joinColumns = {@JoinColumn(name = "message_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    Set<User> likes = new HashSet<>();

    public Message(String text, String tag, User user) {
        this.text = text;
        this.tag = tag;
        this.author = user;
    }

    public String getAuthorName() {
        return MessageUtils.getAuthorName(author);
    }
}
