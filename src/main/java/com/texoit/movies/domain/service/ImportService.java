package com.texoit.movies.domain.service;

import com.texoit.movies.domain.entities.*;
import com.texoit.movies.domain.repository.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class ImportService implements ApplicationListener<ApplicationReadyEvent> {

    static String COLUMN_DIVISOR = ";";
    private static String INNER_DIVISOR = ",";

    static String DEFAULT_NAME_OF_THE_PREMIUM = "Golden Raspberry Awards";

    static String PATH_CSV_FILE = "static/movielist.csv";

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportService.class);

    @Getter
    private final IMovieRepository movieRepository;
    @Getter
    private final IStudioRepository studioRepository;
    @Getter
    private final IProducerRepository producerRepository;
    @Getter
    private final IPremiumRepository premiumRepository;
    @Getter
    private final IIndicationRepository indicationRepository;

    /**
     * Executado logo após o início da aplicação
     *
     * @param applicationReadyEvent ApplicationReadyEvent
     */
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent applicationReadyEvent) {

        if (Arrays.asList(applicationReadyEvent.getApplicationContext().getEnvironment().getActiveProfiles()).isEmpty())
            this.importt();

    }

    /**
     *
     */
    @Async
    public void importt() {
        final Stream<String> lines = new BufferedReaderBuilder().path(PATH_CSV_FILE).build().lines().stream();

        lines.forEach(line -> {
            final String[] columns = line.split(COLUMN_DIVISOR);

            // Set title of the movie
            final Movie movie = new Movie();

            // Extract title of the movie from the columns
            extractTitleMovieFromColumns(movie, columns);

            // Extract studios from the columns
            extractStudiosFromColumns(movie, columns);

            // Extract producers from the columns
            extractProducersFromColumns(movie, columns);

            // Extract indications from the columns
            extractPremiumAndIndicationsFromColumns(movie, columns);

            System.out.println(movie.getTitle());
            // Save all via cascades
            this.save(movie);
        });

        int moviesCount = this.movieRepository.findAll().size();
        int studiosCount = this.studioRepository.findAll().size();
        int producersCount = this.producerRepository.findAll().size();
        int premiunsCount = this.premiumRepository.findAll().size();
        int indicationsCount = this.indicationRepository.findAll().size();
        int winnersCount = this.indicationRepository.findAll().stream().filter(indication -> Optional.ofNullable(indication.getWinner()).orElse(false)).collect(Collectors.toSet()).size();

        LOGGER.info(moviesCount + " movies inserteds");
        LOGGER.info(studiosCount + " studios inserteds");
        LOGGER.info(producersCount + " producers inserteds");
        LOGGER.info(premiunsCount + " premiuns inserteds");
        LOGGER.info(indicationsCount + " indications inserteds");
        LOGGER.info(winnersCount + " winners inserteds");
    }

    /**
     * @param movie   Movie
     * @param columns String[]
     */
    public void extractTitleMovieFromColumns(final Movie movie, final String[] columns) {
        // Set title of the movie
        movie.setTitle(columns[1]);
    }

    /**
     * @param movie   Movie
     * @param columns String[]
     */
    public void extractStudiosFromColumns(final Movie movie, final String[] columns) {
        final Set<MovieStudio> studios = new HashSet<>();

        Arrays.asList(columns[2].split(INNER_DIVISOR)).forEach(studioName -> {
            if (studioName.contains(" and "))
                Arrays.asList(studioName.split(" and ")).forEach(s -> {
                    final Studio studio = this.studioRepository.findByName(s).orElse(new Studio(s));
                    studios.add(new MovieStudio(movie, studio));
                });
            else {
                final Studio studio = this.studioRepository.findByName(studioName).orElse(new Studio(studioName));
                studios.add(new MovieStudio(movie, studio));
            }
        });

        movie.setStudios(studios);
    }

    /**
     * @param movie   Movie
     * @param columns String[]
     */
    public void extractProducersFromColumns(final Movie movie, final String[] columns) {
        final Set<MovieProducer> producers = new HashSet<>();

        Arrays.asList(columns[3].replaceAll(", ", INNER_DIVISOR).split(INNER_DIVISOR)).forEach(producerName -> {
            if (producerName.contains(" and "))
                Arrays.asList(producerName.split(" and ")).forEach(s -> {
                    final Producer producer = this.producerRepository.findByName(s).orElse(new Producer(s));
                    producers.add(new MovieProducer(movie, producer));
                });
            else {
                final Producer producer = this.producerRepository.findByName(producerName).orElse(new Producer(producerName));
                producers.add(new MovieProducer(movie, producer));
            }
        });

        movie.setProducers(producers);
    }

    /**
     * @param movie   Movie
     * @param columns String[]
     */
    public void extractPremiumAndIndicationsFromColumns(final Movie movie, final String[] columns) {
        final Premium premium = this.premiumRepository.findByNameAndYear(DEFAULT_NAME_OF_THE_PREMIUM, Integer.parseInt(columns[0])).orElse(new Premium(DEFAULT_NAME_OF_THE_PREMIUM, Integer.parseInt(columns[0])));

        final Indication indication = new Indication(movie, premium, (columns.length > 4 && columns[4] != null && !columns[4].isEmpty() && columns[4].trim().toLowerCase().equals("yes")) ? true : null);

        movie.setIndications(Set.of(indication));
    }

    /**
     * @param movie Movie
     */
    public void save(final Movie movie) {
        movieRepository.save(movie);
    }
}
