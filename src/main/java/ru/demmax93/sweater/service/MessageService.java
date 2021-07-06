package ru.demmax93.sweater.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.demmax93.sweater.domain.Message;
import ru.demmax93.sweater.domain.User;
import ru.demmax93.sweater.domain.dto.MessageDto;
import ru.demmax93.sweater.repository.MessageRepository;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    @Value("${upload.path}")
    private String uploadPath;

    public Page<MessageDto> getMessages(Pageable pageable, String filter, User user) {
        if (filter != null && !filter.isEmpty()) {
            return messageRepository.findByTag(pageable, filter, user);
        } else {
            return messageRepository.findAll(pageable, user);
        }
    }

    public Page<MessageDto> getMessagesByAuthor(Pageable pageable, User author, User user) {
        return messageRepository.findByAuthor(pageable, author, user);
    }

    public void createMessage(Message message, User author, MultipartFile file) throws IOException {
        message.setAuthor(author);
        ServiceUtils.saveFile(message, file, uploadPath);
        messageRepository.save(message);
    }

    public void updateMessage(User currentUser, Message message, String text, String tag, MultipartFile file) throws IOException {
        if (message.getAuthor().equals(currentUser)) {
            if (StringUtils.hasLength(text)) {
                message.setText(text);
            }
            if (StringUtils.hasLength(tag)) {
                message.setTag(tag);
            }
            ServiceUtils.saveFile(message, file, uploadPath);
            messageRepository.save(message);
        }
    }
}
