package com.msys.repository;

import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.msys.entity.Stock;


@Transactional
public interface StockRepository extends JpaRepository<Stock, Long> {
 
}
