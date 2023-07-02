package com.ecommerce.repository;

import com.ecommerce.model.Auth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAuthenticationRepository extends JpaRepository<Auth,Long> {

    Optional<Auth> findOptionalByMail(String mail);

    Optional<Auth> findOptionalByMailAndPassword(String mail, String password);
}
