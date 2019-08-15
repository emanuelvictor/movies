package com.texoit.movies.service;

import com.texoit.movies.domain.Movie;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.stream.Collectors;

import static com.texoit.movies.service.ImportService.*;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.JVM)
public class ImportIntegrationTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportIntegrationTests.class);

    @Autowired
    private ImportService importService;

    private final Movie movie = new Movie();

    private final BufferedReaderBuilder bufferedReaderBuilder;

    public ImportIntegrationTests() {
        bufferedReaderBuilder = new BufferedReaderBuilder().path(PATH_CSV_FILE).build();
    }

    @Test
    public void buildBufferedReaderAndReadLinesMustPass() {
        Assert.notNull(bufferedReaderBuilder, "Arquivo não encontrado");
        Assert.notNull(bufferedReaderBuilder.lines(), "Erro na leitura do arquivo");
        Assert.notEmpty(bufferedReaderBuilder.lines(), "Lista vazia");
    }

    @Test
    public void buildBufferedReaderWithoutFirstLineMustPass() {
        buildBufferedReaderAndReadLinesMustPass();
        Assert.isTrue(!bufferedReaderBuilder.lines().stream().findFirst().orElseThrow().split(";")[0].equals("title"), "A primeira linha do arquivo deve ser pulada");
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void buildBufferedReaderMustFail() {
        new BufferedReaderBuilder().path("movielist.csv");
    }

    @Test
    public void extractTitleOfMovieFromColumnsMustPass() {
        importService.extractTitleMovieFromColumns(movie, bufferedReaderBuilder.lines().stream().findFirst().orElseThrow().split(COLUMN_DIVISOR));
        Assert.isTrue(movie.getTitle().equals("Can't Stop the Music"), "Nome incorreto");
    }

    @Test
    public void extractStudiosFromColumnsMustPass() {
        importService.extractStudiosFromColumns(movie, bufferedReaderBuilder.lines().stream().findFirst().orElseThrow().split(COLUMN_DIVISOR));
        Assert.isTrue(movie.getStudios().stream().findFirst().orElseThrow().getStudio().getName().equals("Associated Film Distribution"), "Estúdio incorreto");
    }

    @Test
    public void extractProducersFromColumnsMustPass() {
        importService.extractProducersFromColumns(movie, bufferedReaderBuilder.lines().stream().findFirst().orElseThrow().split(COLUMN_DIVISOR));
        Assert.isTrue(movie.getProducers().stream().findFirst().orElseThrow().getProducer().getName().equals("Allan Carr"), "Produtor incorreto");
    }

    @Test
    public void extractPremiumAndIndicationsFromColumnsMustPass() {
        importService.extractPremiumAndIndicationsFromColumns(movie, bufferedReaderBuilder.lines().stream().findFirst().orElseThrow().split(COLUMN_DIVISOR));
        Assert.isTrue(movie.getIndications().stream().findFirst().orElseThrow().getPremium().getName().equals(DEFAULT_NAME_OF_THE_PREMIUM), "Nome do prêmio incorreto");
        Assert.isTrue(movie.getIndications().stream().findFirst().orElseThrow().getPremium().getYear().equals(1980), "Ano do prêmio incorreto");
        Assert.isTrue(movie.getIndications().stream().findFirst().orElseThrow().getWinner(), "Esse registro deveria ser vencedor");
    }

    @Test
    public void saveMovieImportedMustPass() {
        Assert.isNull(movie.getId(), "Movie inserido");

        extractTitleOfMovieFromColumnsMustPass();
        extractStudiosFromColumnsMustPass();
        extractProducersFromColumnsMustPass();
        extractPremiumAndIndicationsFromColumnsMustPass();

        importService.save(movie);

        Assert.isTrue(movie.getIndications().stream().findFirst().orElseThrow().getPremium().getName().equals(DEFAULT_NAME_OF_THE_PREMIUM), "Nome do prêmio incorreto");
        Assert.isTrue(movie.getIndications().stream().findFirst().orElseThrow().getPremium().getYear().equals(1980), "Ano do prêmio incorreto");
        Assert.isTrue(movie.getIndications().stream().findFirst().orElseThrow().getWinner(), "Esse registro deveria ser vencedor");
        Assert.notNull(movie.getId(), "Movie não inserido");
    }

    @Test
    public void saveAllMoviesImportedsMustPass() {

        importService.getMovieRepository().deleteAll();

        importService.importt();

        int moviesCount = importService.getMovieRepository().findAll().size();
        int studiosCount = importService.getStudioRepository().findAll().size();
        int producersCount = importService.getProducerRepository().findAll().size();
        int premiunsCount = importService.getPremiumRepository().findAll().size();
        int indicationsCount = importService.getIndicationRepository().findAll().size();
        int winnersCount = importService.getIndicationRepository().findAll().stream().filter(indication -> Optional.ofNullable(indication.getWinner()).orElse(false)).collect(Collectors.toSet()).size();

        LOGGER.info(moviesCount + " movies inserteds");
        LOGGER.info(studiosCount + " studios inserteds");
        LOGGER.info(producersCount + " producers inserteds");
        LOGGER.info(premiunsCount + " premiuns inserteds");
        LOGGER.info(indicationsCount + " indications inserteds");
        LOGGER.info(winnersCount + " winners inserteds");
    }
}
