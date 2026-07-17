package co.com.paycash.itemservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import co.com.paycash.itemservice.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
