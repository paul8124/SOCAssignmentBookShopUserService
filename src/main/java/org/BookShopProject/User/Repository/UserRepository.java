package org.BookShopProject.User.Repository;


import org.BookShopProject.User.Entity.UserDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserDetailsEntity,Long> {
    Optional<UserDetailsEntity>  findByEmailId(String email);

}
