package com.ecommerce.repository;

import com.ecommerce.model.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<UserProfile,Long> {
}
