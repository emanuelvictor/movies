package com.texoit.movies.domain.repository;

import com.texoit.movies.domain.AbstractIntegrationTests;
import com.texoit.movies.domain.entities.Movie;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

public class ProducerRepositoryIntegrationTests extends AbstractIntegrationTests {

    /**
     *
     */
    @Autowired
    private IMovieRepository movieRepository;

    /**
     *
     */
    @Test(expected = javax.validation.ConstraintViolationException.class)
    public void saveMovieMustFail() {
        this.movieRepository.save(new Movie());
    }

    /**
     *
     */
    @Test
    public void saveMovieMustPass() {
        final String title = "Os Vingadores: Ultimato";
        final Movie movie = new Movie(title);
        this.movieRepository.save(movie);

        Assert.assertNotNull(movie);
        Assert.assertNotNull(movie.getId());
        Assert.assertEquals(movie.getTitle(), title);

        this.movieRepository.deleteById(movie.getId());
    }

    /**
     *
     */
    @Test(expected = DataIntegrityViolationException.class)
    @Sql({"/dataset/movies.sql"})
    public void saveRepeatedMovieMustPass() {
        final String title = "Warcraft";
        final Movie movie = new Movie(title);
        this.movieRepository.save(movie);
    }

    /**
     *
     */
    @Test
    @Sql({"/dataset/movies.sql"})
    public void findMovieByIdMustPass() {
        final Movie movie = this.movieRepository.findById(1003L).orElseThrow();

        Assert.assertNotNull(movie);
        Assert.assertNotNull(movie.getId());
        Assert.assertEquals("Warcraft", movie.getTitle());
    }

    /**
     *
     */
    @Test
    @Sql({"/dataset/movies.sql"})
    public void listMoviesByTitleMustReturn2() {
        final Page<Movie> pageOfMovies = this.movieRepository.findAllByTitleContaining("Guerra", null);

        Assert.assertNotNull(pageOfMovies);
        Assert.assertEquals(2, pageOfMovies.getTotalElements());
    }

    /**
     *
     */
    @Test
    @Sql({"/dataset/movies.sql"})
    public void listMoviesByTitleMustReturn1() {
        final Page<Movie> movies = this.movieRepository.findAllByTitleContaining("mediterrâneo", null);

        Assert.assertNotNull(movies);
        Assert.assertEquals(1, movies.getTotalElements());
    }

    /**
     *
     */
    @Test
    @Sql({"/dataset/movies.sql"})
    public void listMoviesMustReturnAll() {
        final List<Movie> movies = this.movieRepository.findAll();

        Assert.assertNotNull(movies);
        Assert.assertEquals(4, movies.size());
    }

}
