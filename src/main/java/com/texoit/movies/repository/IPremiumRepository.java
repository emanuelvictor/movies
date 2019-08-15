package com.texoit.movies.repository;

import com.texoit.movies.domain.Premium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.Year;
import java.util.Optional;

public interface IPremiumRepository extends JpaRepository<Premium, Long> {
//    @Query("FROM Premium premium WHERE (premium.name = :name AND premium.year = :year)")
    Optional<Premium> findByNameAndYear(final String name, final Integer year);
}
