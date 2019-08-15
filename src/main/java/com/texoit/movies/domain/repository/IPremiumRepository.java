package com.texoit.movies.domain.repository;

import com.texoit.movies.domain.entities.Premium;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPremiumRepository extends JpaRepository<Premium, Long> {
    Optional<Premium> findByNameAndYear(final String name, final Integer year);
}
