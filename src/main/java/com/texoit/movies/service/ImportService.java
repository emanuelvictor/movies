package com.texoit.movies.service;

import com.texoit.movies.domain.*;
import com.texoit.movies.repository.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ImportService implements ApplicationListener<ApplicationReadyEvent> {

    private static String COLUMN_DIVISOR = ";";
    private static String INNER_DIVISOR = ";";

    private static String DEFAULT_NAME_OF_THE_PREMIUM = "Premio zoado";

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportService.class);

    private final IMovieRepository movieRepository;
    private final IStudioRepository studioRepository;
    private final IProducerRepository producerRepository;
    private final IPremiumRepository premiumRepository;
    private final IIndicationRepository indicationRepository;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {

        BufferedReader bufferedReader = null;

        try {
            bufferedReader = this.getBufferReaderWithoutFirstLine();

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                final String[] columns = line.split(COLUMN_DIVISOR);

                // Set title of the movie
                final Movie movie = new Movie(columns[1]);

                // Extract studios from the columns
                extractStudiosFromColumns(movie, columns);

                // Extract producers from the columns
                extractProducersFromColumns(movie, columns);

                extractPremiumAndIndicationsFromColumns(movie, columns);

                movieRepository.save(movie);

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

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
    private void extractStudiosFromColumns(final Movie movie, final String[] columns) {
        final Set<MovieStudio> studios = new HashSet<>();

        Arrays.asList(columns[2].split(INNER_DIVISOR)).forEach(studioName -> {
            final Studio studio = this.studioRepository.findByName(studioName).orElse(new Studio(studioName));
            studios.add(new MovieStudio(movie, studio));
        });

        movie.setStudios(studios);
    }

    /**
     * @param movie   Movie
     * @param columns String[]
     */
    private void extractProducersFromColumns(final Movie movie, final String[] columns) {
        final Set<MovieProducer> producers = new HashSet<>();

        Arrays.asList(columns[3].split(INNER_DIVISOR)).forEach(producerName -> {
            final Producer producer = this.producerRepository.findByName(producerName).orElse(new Producer(producerName));
            producers.add(new MovieProducer(movie, producer));
        });

        movie.setProducers(producers);
    }

    /**
     * @param movie   Movie
     * @param columns String[]
     */
    private void extractPremiumAndIndicationsFromColumns(final Movie movie, final String[] columns) {
        final Premium premium = this.premiumRepository.findByNameAndYear(DEFAULT_NAME_OF_THE_PREMIUM, Integer.parseInt(columns[0])).orElse(new Premium(DEFAULT_NAME_OF_THE_PREMIUM, Integer.parseInt(columns[0])));

        final Indication indication = new Indication(movie, premium, (columns.length > 4 && columns[4] != null && !columns[4].isEmpty() && columns[4].trim().toLowerCase().equals("yes")) ? true : null);

        movie.setIndications(Set.of(indication));
    }

    /**
     * @return BufferedReader
     * @throws IOException {}
     */
    private BufferedReader getBufferReaderWithoutFirstLine() throws IOException {
        final var br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("static/movielist.csv"))));
        // Pula primeira linha
        br.readLine();
        return br;
    }
}
