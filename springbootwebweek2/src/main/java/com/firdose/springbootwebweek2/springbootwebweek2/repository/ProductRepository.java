package com.firdose.springbootwebweek2.springbootwebweek2.repository;

import com.firdose.springbootwebweek2.springbootwebweek2.entity.ProductEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByTitle(String title);

    List<ProductEntity> findByPriceGreaterThan(double price);

    Optional<ProductEntity> findByTitleAndPrice(String sku, double price);

    List<ProductEntity> findByPriceBetween(double starting, double ending);

    List<ProductEntity> findByCreatedAtIsNull();

    List<ProductEntity> findByTitleLike(String s);

    List<ProductEntity> findByTitleStartingWith(String startingWith);

    List<ProductEntity> findByTitleStartingWithIgnoreCase(String s);

    List<ProductEntity> findBySkuOrderByPrice(String sku);

    @Transactional
    @Modifying
    @Query("Update ProductEntity p SET p.isAvailable=:status where p.sku=:sku")
    void updateAvailableStatusBySku(@Param("status") Boolean status,@Param("sku") String sku);
}
