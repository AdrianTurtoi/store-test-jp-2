package com.msys.repository;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.msys.entity.Article;

@Transactional
public interface ArticleRepository extends JpaRepository<Article, Long> {

}
