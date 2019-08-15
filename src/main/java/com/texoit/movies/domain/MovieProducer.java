package com.texoit.movies.domain;

import com.texoit.movies.domain.generic.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"movie_id", "producer_id"}),
})
public class MovieProducer extends AbstractEntity {

    /**
     *
     */
    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    /**
     *
     */
    @NotNull
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "producer_id")
    private Producer producer;

    /**
     *
     */
    public MovieProducer() {
    }

    /**
     * @param id Long
     */
    public MovieProducer(Long id) {
        super(id);
    }

    /**
     * @param movie    {Movie}
     * @param producer {Producer}
     */
    public MovieProducer(final @NotNull Movie movie, final @NotNull Producer producer) {
        this.movie = movie;
        this.producer = producer;
    }

}
