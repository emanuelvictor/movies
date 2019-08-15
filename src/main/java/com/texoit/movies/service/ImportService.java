package com.texoit.movies.service;

import com.texoit.movies.domain.*;
import com.texoit.movies.repository.IMovieRespository;
import com.texoit.movies.repository.IProducerRespository;
import com.texoit.movies.repository.IStudioRespository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class ImportService implements ApplicationListener<ApplicationReadyEvent> {


    private static String CSVDIVISOR = ";";

    private static final Logger LOGGER = LoggerFactory.getLogger(ImportService.class);

    public static String getCSVDIVISOR() {
        return CSVDIVISOR;
    }

    private final IMovieRespository movieRepository;
    private final IStudioRespository studioRepository;
    private final IProducerRespository producerRepository;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {

        BufferedReader br = null;

        try {
            br = this.getBufferReaderWithoutFirstLine();

            String linha;

            while ((linha = br.readLine()) != null) {
                final String[] colunas = linha.split(CSVDIVISOR);

                final Movie movie = new Movie();
                movie.setTitle(colunas[1]);

                final Set<MovieStudio> studios = new HashSet<>();

                Arrays.asList(colunas[2].split(",")).forEach(studioName -> {
                    final Studio studio = this.studioRepository.findByName(studioName).orElse(new Studio(studioName));
                    studios.add(new MovieStudio(movie, studio));
                });
                
                movie.setStudios(studios);

                final Set<MovieProducer> producers = new HashSet<>();

                Arrays.asList(colunas[3].split(",")).forEach(producerName -> {
                    final Producer producer = this.producerRepository.findByName(producerName).orElse(new Producer(producerName));
                    producers.add(new MovieProducer(movie, producer));
                });

                movie.setProducers(producers);

                movieRepository.save(movie);

            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        LOGGER.info(movieRepository.findAll().size() + " movies inserteds");
        LOGGER.info(studioRepository.findAll().size() + " studios inserteds");
        LOGGER.info(producerRepository.findAll().size() + " producers inserteds");
//        System.out.println(studioRepository.findAll().size());
    }

    /**
     * @return
     * @throws IOException
     */
    private BufferedReader getBufferReaderWithoutFirstLine() throws IOException {
        final var br = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("static/movielist.csv"))));
        // Pula primeira linha
        br.readLine();
        return br;
    }
}
