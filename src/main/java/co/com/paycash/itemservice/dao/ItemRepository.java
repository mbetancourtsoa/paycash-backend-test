package co.com.paycash.itemservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import co.com.paycash.itemservice.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("""
            SELECT i FROM Item i
            WHERE COALESCE((SELECT AVG(r.rating) FROM Review r WHERE r.item = i), 0) < :rating
            """)
    List<Item> findItemsWithAverageRatingLowerThan(@Param("rating") Double rating);
}
