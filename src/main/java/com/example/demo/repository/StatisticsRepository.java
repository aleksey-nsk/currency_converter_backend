package com.example.demo.repository;

import com.example.demo.entity.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

    Statistics findFirstByFromValuteAndToValute(String fromValute, String toValute);

    @Query("UPDATE Statistics SET amountConvert = :a, averageRate = :ar, sumConvert = :s WHERE id = :id")
    @Modifying
    void updateStat(Long id, Integer a, BigDecimal ar, BigDecimal s);
}
