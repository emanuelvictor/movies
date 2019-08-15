package com.texoit.movies.repository;

import com.texoit.movies.domain.Indication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IIndicationRepository extends JpaRepository<Indication, Long> {
}
