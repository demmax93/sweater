package ru.demmax93.sweater.domain.dto;

import lombok.Getter;
import lombok.ToString;
import ru.demmax93.sweater.domain.Message;
import ru.demmax93.sweater.domain.MessageUtils;
import ru.demmax93.sweater.domain.User;

@Getter
@ToString
public class MessageDto {
    private final Long id;
    private final String text;
    private final String tag;
    private final User author;
    private final String filename;
    private final Long likes;
    private final Boolean meLiked;

    public MessageDto(Message message, Long likes, Boolean meLiked) {
        this.id = message.getId();
        this.text = message.getText();
        this.tag = message.getTag();
        this.author = message.getAuthor();
        this.filename = message.getFilename();
        this.likes = likes;
        this.meLiked = meLiked;
    }

    public String getAuthorName() {
        return MessageUtils.getAuthorName(author);
    }
}
