package com.texoit.movies.repository;

import com.texoit.movies.domain.Producer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IProducerRepository extends JpaRepository<Producer, Long> {
    Optional<Producer> findByName(final String name);
}
