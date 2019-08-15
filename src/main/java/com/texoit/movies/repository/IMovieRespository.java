package com.texoit.movies.repository;

import com.texoit.movies.domain.Movie;
import com.texoit.movies.domain.Studio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMovieRespository extends JpaRepository<Movie, Long> {
}
