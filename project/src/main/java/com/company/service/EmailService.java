package com.company.service;

import com.company.dto.ArticleDto;
import com.company.dto.EmailDTO;
import com.company.entity.EmailEntity;
import com.company.enums.ItemStatus;
import com.company.repository.EmailRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EmailService {
    @Autowired
    private EmailRepository emailRepository;
    @Autowired
    private JavaMailSender javaMailSender;


    public EmailDTO create(EmailDTO dto){
        EmailEntity entity = new EmailEntity();
        entity.setEmail(dto.getEmail());
        entity.setStatus(ItemStatus.BLOCK);
        emailRepository.save(entity);
        dto.setId(entity.getId());
        sendEmail(dto.getEmail(),dto.getId());
        return dto;
    }

    public List<EmailDTO> list(){
        List<EmailEntity> subjectEntities = emailRepository.findAll();
        return subjectEntities.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public EmailDTO update(EmailDTO dto){
        EmailEntity email = getById(dto.getId());
        if (dto.getEmail() != null) {
            email.setEmail(dto.getEmail());
        }
        emailRepository.save(email);
        return toDto(email);
    }

    public void delete(Integer id)
    {
        emailRepository.delete(getById(id));
    }

    public EmailEntity getById(Integer id){
        return emailRepository.getById(id);
    }

    public void sendEmail(String toAccount, Integer id) {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Salom jigar qalaysan\n");
        stringBuilder.append("Agar bu sen bo'lsang shu linkga bos : ");
        stringBuilder.append("http://localhost:8080/email/verification/" + id);

        String title = "Verificate Email test";
        String text = stringBuilder.toString();

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toAccount);
        message.setSubject(title);
        message.setText(text);
        javaMailSender.send(message);

    }

    public void verification(Integer id){
        Optional<EmailEntity> optional = emailRepository.findById(id);
        if(!optional.isPresent()){
            log.warn("Email not found with this id {}",id);
        }
        if(optional.get().getStatus().equals(ItemStatus.BLOCK))
        {
            optional.get().setStatus(ItemStatus.ACTIVE);
            emailRepository.save(optional.get());
        }
    }

    public void sendToEmails(ArticleDto article){
        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(article)
    }

    public EmailDTO toDto(EmailEntity entity){
        EmailDTO dto = new EmailDTO();
        dto.setId(entity.getId());
        dto.setEmail(entity.getEmail());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}
