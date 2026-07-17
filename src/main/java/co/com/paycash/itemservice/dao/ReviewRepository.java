package co.com.paycash.itemservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.paycash.itemservice.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
