package com.example.demo.repository;

import com.example.demo.entity.Convert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConvertRepository extends JpaRepository<Convert, Long> {
}
