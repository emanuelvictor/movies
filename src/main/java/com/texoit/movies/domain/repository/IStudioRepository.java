package com.texoit.movies.domain.repository;

import com.texoit.movies.domain.entities.Studio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IStudioRepository extends JpaRepository<Studio, Long> {
    Optional<Studio> findByName(final String name);
}
