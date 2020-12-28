package oit.is.chisakiken.hondaboard.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import oit.is.chisakiken.hondaboard.repository.ProfileImageRepository;

@Service
public class UserService {
    @Autowired
    ProfileImageRepository profileImageRepository;

    public Optional<Path> getUserImage(int user_id) {
        var result = profileImageRepository.findByUserId(user_id);
        if (result.isPresent()) {
            return Optional.of(Paths.get(result.get()));
        }
        return Optional.empty();
    }

    private byte[] resizeImage(byte[] image_bytes) throws IOException {
        var image = ImageIO.read(new ByteArrayInputStream(image_bytes));
        return resizeImage(image);
    }

    private byte[] resizeImage(BufferedImage image) throws IOException {
        var resizedImage = Scalr.resize(image, 300, 300);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "png", baos);
        return baos.toByteArray();
    }

    public void updateImage(int user_id, byte[] image) throws IOException {
        var resized_image = resizeImage(image);

        // ファイル保存
        File saveFile = new File("img/user/" + user_id + ".png");

        saveFile.getParentFile().mkdirs();
        saveFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(saveFile);

        fos.write(resized_image);
        fos.close();

        profileImageRepository.save(user_id, saveFile.getPath());
    }
}
