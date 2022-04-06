package com.company.repository;

import com.company.entity.ArticleEntity;
import com.company.enums.ArticleEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArticleRepository extends PagingAndSortingRepository<ArticleEntity, Integer> {
    @Query("select a from ArticleEntity a where a.status=?1")
    Page<ArticleEntity> getAllArticle(ArticleEnum status, Pageable pageable);

}
