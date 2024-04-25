package com.kss.repository;

import com.kss.domains.Category;
import com.kss.domains.enums.CategoryStatus;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {

    @NotNull
    Page<Category> findAll(@NotNull Pageable pageable);

    Boolean existsByTitle(String title);

    Optional<Page<Category>> findAllByStatus(Pageable pageable, CategoryStatus status);

    @Query("SELECT c from Category c WHERE c.status=:status and c.id=:id")
    Optional<Category>  getCategoryByStatus_PublishedAndId(@Param("status") CategoryStatus status, @Param("id") Long id);

    void deleteAllByBuiltInFalse();
}
