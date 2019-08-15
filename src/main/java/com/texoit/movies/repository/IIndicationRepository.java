package com.texoit.movies.repository;

import com.texoit.movies.domain.Indication;
import com.texoit.movies.domain.Premium;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IIndicationRepository extends JpaRepository<Indication, Long> {
}
