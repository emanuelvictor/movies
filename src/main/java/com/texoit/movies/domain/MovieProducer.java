package com.texoit.movies.domain;

import com.texoit.movies.domain.generic.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class MovieProducer extends AbstractEntity {

    /**
     *
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    /**
     *
     */
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "producer_id")
    private Producer producer;

    /**
     *
     */
    public MovieProducer() {
    }

    /**
     *
     * @param movie {Movie}
     * @param producer {Producer}
     */
    public MovieProducer(final Movie movie, final Producer producer) {
        this.movie = movie;
        this.producer = producer;
    }

}
