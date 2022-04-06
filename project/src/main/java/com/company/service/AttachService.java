package com.company.service;

import com.company.dto.AttachDTO;
import com.company.entity.AttachEntity;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.AttachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
public class AttachService {
    @Autowired
    private AttachRepository attachRepository;

    @Value("${attach.upload.folder}")
    private String uploadFolder; // uploads
    @Value("${attach.open.url}")
    private String attachOpenUrl;

    public AttachDTO saveFile(MultipartFile file) {

        String filePath = getYmDString(); // 2021/07/13
        String key = UUID.randomUUID().toString();
        String extension = getExtension(file.getOriginalFilename()); // .jpg,.pdf

        File folder = new File(uploadFolder + "/" + filePath); // uploads/2021/07/13
        if (!folder.exists()) {
            folder.mkdirs();
        }

        try {
            byte[] bytes = file.getBytes();
            //  uploads/2021/07/13/dasda_dasda_dasda_dasda.jpg
            Path path = Paths.get(uploadFolder + "/" + filePath + "/" + key + "." + extension);
            Files.write(path, bytes);

            AttachEntity entity = new AttachEntity();
            entity.setKey(key);
            entity.setExtension(extension);
            entity.setOriginName(file.getOriginalFilename());
            entity.setSize(file.getSize());
            entity.setFilePath(filePath);
            attachRepository.save(entity);

            AttachDTO attachDTO = new AttachDTO();
            attachDTO.setId(entity.getId());
            attachDTO.setKey(entity.getKey());
            attachDTO.setFilePath(entity.getFilePath());
            attachDTO.setCreatedDate(entity.getCreated_date());
            attachDTO.setSize(entity.getSize());
            attachDTO.setOriginName(entity.getOriginName());
            attachDTO.setExtension(entity.getExtension());
            attachDTO.setUrl(attachOpenUrl + "/" + key);
            return attachDTO;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] loadAttach(String key) throws IOException {
        byte[] imageInByte;
        Optional<AttachEntity> optional = attachRepository.findByKey(key);
        if (!optional.isPresent()) {
            return new byte[0];
        }
        BufferedImage originalImage;
        String filePath = optional.get().getFilePath() + "/" + key + "." + optional.get().getExtension();
        try {
            originalImage = ImageIO.read(new File(uploadFolder + "/" + filePath));
        } catch (Exception e) {
            return new byte[0];
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(originalImage, optional.get().getExtension(), baos);

        baos.flush();
        imageInByte = baos.toByteArray();
        baos.close();
        return imageInByte;
    }


    public static String getYmDString() {
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        int day = Calendar.getInstance().get(Calendar.DATE);

        return year + "/" + month + "/" + day + "/";
    }

    public String getExtension(String fileName) {
        int lastIndex = fileName.lastIndexOf(".");
        return fileName.substring(lastIndex + 1);
    }

    /*    public byte[] loadGeneral(String key) {
        Optional<AttachEntity> optional = attachRepository.findByKey(key);
        if (!optional.isPresent()) {
            return new byte[0];
        }
        String filePath = optional.get().getFilePath() + "/" + key + "." + optional.get().getExtension();
        File file = new File(uploadFolder + "/" + filePath);
        if (file.exists()) {
            InputStream inputStream = null;
            try {
                inputStream = new FileInputStream(file);
                return inputStream.readAllBytes();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new byte[0];
    }
*/
    public void delete(String key) {
        Optional<AttachEntity> optional = attachRepository.findByKey(key);
        if (!optional.isPresent()) {
            return;
        }
        String filePath = optional.get().getFilePath() + "/" + key + "." + optional.get().getExtension();
        File file = new File(uploadFolder + "/" + filePath);
        if (file.exists()) {
           boolean t= file.delete();
            System.out.println(t);
        }
        attachRepository.delete(optional.get());
    }

    public AttachEntity get(Integer id) {
        return attachRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Attach not found"));
    }

    public AttachDTO toDto(AttachEntity entity){
        AttachDTO attachDTO = new AttachDTO();
        attachDTO.setId(entity.getId());
        attachDTO.setKey(entity.getKey());
        attachDTO.setFilePath(entity.getFilePath());
        attachDTO.setCreatedDate(entity.getCreated_date());
        attachDTO.setSize(entity.getSize());
        attachDTO.setOriginName(entity.getOriginName());
        attachDTO.setExtension(entity.getExtension());
        attachDTO.setUrl(attachOpenUrl + "/" + entity.getKey());
        return attachDTO;
    }
}
