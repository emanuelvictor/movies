package com.texoit.movies.domain.repository;

import com.texoit.movies.domain.entities.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IProducerRepository extends JpaRepository<Producer, Long> {
    Optional<Producer> findByName(final String name);
}
