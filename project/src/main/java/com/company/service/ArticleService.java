package com.company.service;

import com.company.dto.ArticleDto;
import com.company.entity.ArticleEntity;
import com.company.enums.ArticleEnum;
import com.company.exceptions.ItemNotFoundException;
import com.company.repository.ArticleRepository;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    AttachService attachService;

    //    ***
//    NEW ARTICLE CREATE
//    ***
    public ArticleDto create(ArticleDto dto) {
        ArticleEntity entity = new ArticleEntity();
        entity.setContentUZ(dto.getContentUZ());
        entity.setContentRU(dto.getContentRU());
        entity.setAttach(attachService.get(dto.getAttach().getId()));
        entity.setStatus(ArticleEnum.ACTIVE);
        articleRepository.save(entity);

        dto.setId(entity.getId());
        return dto;
    }

//    ***
//    GET ALL ARTICLE
//    ***

    public PageImpl<ArticleDto> getAllArticle(int size, int page) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "created_date");
        Page<ArticleEntity> page1 = articleRepository.getAllArticle(ArticleEnum.ACTIVE, pageable);
        Long totalElements = page1.getTotalElements();
        List<ArticleEntity> entityList = page1.getContent();
        List<ArticleDto> dto = new LinkedList<>();
        for (ArticleEntity article : entityList) {
            dto.add(toDto(article));
        }
        return new PageImpl<>(dto, pageable, totalElements);

    }


//    ***
//    DELETE ARTICLE BY ID
//    ***

    public String deleteById(String jwt) {
        Integer articleId = JwtUtil.decodeJwt(jwt);
        if (!articleRepository.existsById(articleId)) {
            throw new ItemNotFoundException("Article not found");
        }
        articleRepository.deleteById(articleId);
        return "delete";
    }


//    ***
//    UPDATE ARTICLE BY ID
//    ***

    public String updateById(ArticleDto dto) {
        Integer articleId = JwtUtil.decodeJwt(dto.getJwt());
        if (!articleRepository.existsById(articleId)) {
            throw new ItemNotFoundException("Article not found");
        }
        Optional<ArticleEntity> response = articleRepository.findById(articleId);
        if (!response.get().getStatus().equals(dto.getStatus())) {
            response.get().setStatus(dto.getStatus());
        }
        response.get().setContentUZ(dto.getContentUZ());
        response.get().setContentRU(dto.getContentRU());
        articleRepository.save(response.get());
        return "update";
    }


//    ***
//    GET ARTICLE BY ID
//    ***

    public ArticleDto getById(String jwt) {
        Integer articleId = JwtUtil.decodeJwt(jwt);
        if (!articleRepository.existsById(articleId)) {
            throw new ItemNotFoundException("Article not found");
        }
        Optional<ArticleEntity> article = articleRepository.findById(articleId);
        return toDto(article.get());
    }



//    ***
//    ENTITY TO DTO
//    ***

    public ArticleDto toDto(ArticleEntity entity) {
        ArticleDto dto = new ArticleDto();
        dto.setContentUZ(entity.getContentUZ());
        dto.setContentRU(entity.getContentRU());
        dto.setStatus(entity.getStatus());
        dto.setAttach(attachService.toDto(entity.getAttach()));
        dto.setJwt(JwtUtil.createJwtById(entity.getId()));
        return dto;
    }


}
