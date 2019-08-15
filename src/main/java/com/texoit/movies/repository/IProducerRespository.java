package com.texoit.movies.repository;

import com.texoit.movies.domain.Producer;
import com.texoit.movies.domain.Studio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IProducerRespository extends JpaRepository<Producer, Long> {
    Optional<Producer> findByName(final String name);
}
