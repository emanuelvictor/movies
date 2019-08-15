package com.texoit.movies.domain.repository;

import com.texoit.movies.domain.entities.Indication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IIndicationRepository extends JpaRepository<Indication, Long> {
}
