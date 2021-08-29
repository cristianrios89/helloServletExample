package com.greeninc.user.repository;

import com.greeninc.user.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository that manages the {@link User} entities.
 */
@Repository
public interface UsersRepository extends JpaRepository<User, Long> {

    /**
     * Validates if a {@link User} entity exists by first name and last name.
     *
     * @param firstName of the user.
     * @param lastName  of the user.
     * @return True, if the user exists, False otherwise.
     */
    public boolean existsByFirstNameAndLastName(final String firstName, final String lastName);

}
