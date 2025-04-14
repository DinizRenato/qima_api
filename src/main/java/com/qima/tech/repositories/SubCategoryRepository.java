package com.qima.tech.repositories;

import com.qima.tech.entities.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    @Query("SELECT s FROM SubCategory s LEFT JOIN FETCH s.products WHERE s.id = :id")
    Optional<SubCategory> findByIdWithProducts(@Param("id") Long id);
}
