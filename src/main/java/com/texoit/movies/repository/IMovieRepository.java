package com.texoit.movies.repository;

import com.texoit.movies.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMovieRepository extends JpaRepository<Movie, Long> {
}
