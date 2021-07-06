package ru.demmax93.sweater.service;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.demmax93.sweater.domain.Message;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ServiceUtils {
    public static void saveFile(Message message, MultipartFile file, String uploadPath) throws IOException {
        if (file != null && StringUtils.hasLength(file.getOriginalFilename())) {
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String fileName = UUID.randomUUID() + "." + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + fileName));
            message.setFilename(fileName);
        }
    }
}
