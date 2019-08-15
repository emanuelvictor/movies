package com.texoit.movies.domain.repository;

import com.texoit.movies.domain.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMovieRepository extends JpaRepository<Movie, Long> {
}