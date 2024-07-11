package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exceptions.EntityNotFoundException;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
public class AvatarServiceImpl implements AvatarService{
    private final Logger logger = LoggerFactory.getLogger(AvatarServiceImpl.class);

    private final StudentService studentService;
    private final AvatarRepository avatarRepository;
    @Value("${avatars.dir.path}")
    private String avatarsDir;

    public AvatarServiceImpl(StudentService studentService, AvatarRepository avatarRepository) {
        this.studentService = studentService;
        this.avatarRepository = avatarRepository;
    }

    public void uploadAvatar(Long studentId, MultipartFile avatarFile) throws IOException {
        logger.info("Was invoked method for upload avatar");
        Student student = studentService.get(studentId);
        Path filePath = buildFilePath(student, avatarFile.getOriginalFilename());
        saveToDirectory(filePath, avatarFile);
        saveInDb(studentId, student, filePath, avatarFile);
    }

    private String getExtensions(String fileName) {
        logger.info("Was invoked method for get extensions");
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    private Path buildFilePath(Student student, String fileName) {
        logger.info("Was invoked method for build file path of avatar");
        return Path.of(avatarsDir, student.getId() + "_" + student.getName() + "." + getExtensions(fileName));
    }

    private void saveToDirectory(Path filePath, MultipartFile avatarFile) throws IOException {
        logger.info("Was invoked method for save avatar to directory");
        Files.createDirectories(filePath.getParent());
        Files.deleteIfExists(filePath);
        try (
                InputStream is = avatarFile.getInputStream();
                OutputStream os = Files.newOutputStream(filePath, CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }
    }

    private void saveInDb(Long studentId, Student student, Path filePath, MultipartFile avatarFile) throws IOException {
        logger.info("Was invoked method for save avatar in DB");
        Avatar avatar = findOrCreateAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(filePath.toString());
        avatar.setFileSize(avatarFile.getSize());
        avatar.setMediaType(avatarFile.getContentType());
        avatar.setData(avatarFile.getBytes());
        avatarRepository.save(avatar);
    }

    @Override
    public Avatar findAvatar(Long studentId) {
        logger.info("Was invoked method for get avatar");
        return avatarRepository.findById(studentId)
                .orElseThrow(() -> {
                    logger.error("There is not avatar with id = " + studentId);
                    return new EntityNotFoundException();
                });
    }

    private Avatar findOrCreateAvatar(Long studentId) {
        logger.info("Was invoked method for find or create avatar");
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }
    @Override
    public Collection<Avatar> getAvatars(int page, int size) {
        logger.info("Was invoked method for get all avatars");
        Pageable pageable = PageRequest.of(page, size);
        return avatarRepository.findAll(pageable).getContent();
    }
}
