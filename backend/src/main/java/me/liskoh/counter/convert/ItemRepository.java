package me.liskoh.counter.convert;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    @Query("SELECT i FROM Item i JOIN FETCH i.priceUpdates WHERE i.id = :id")
    Optional<Item> findByIdWithPriceUpdates(@Param("id") Long id);

    @Query("SELECT i.name FROM Item i")
    List<String> findAllNames();

    @Query("SELECT i FROM Item i JOIN FETCH i.priceUpdates WHERE i.name = :name")
    Optional<Item> findByNameWithPriceUpdates(String name);

    Optional<Item> findByName(String name);
}
