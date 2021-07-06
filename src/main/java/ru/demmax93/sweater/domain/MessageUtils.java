package ru.demmax93.sweater.domain;

public class MessageUtils {
    public static String getAuthorName(User author) {
        return author != null ? author.getUsername() : "<none>";
    }
}
