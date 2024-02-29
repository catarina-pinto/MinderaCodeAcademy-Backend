package com.mindera.user.repository;

import com.mindera.user.domain.User;
import com.mindera.user.enums.Role;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByUsernameAndPassword(String username, String password);

    @Query("SELECT U FROM User U JOIN FETCH U.addresses A WHERE (U.id = :id OR :id is null) AND (U.role = :role OR :role is null) AND (A.country = :country OR :country is null) AND (A.city = :city OR :city is null)")
    List<User> findByIdAndRoleAndCountryAndCity(@Param("id") Integer id, @Param("role") Role role, @Param("country") String country, @Param("city") String city);
}
