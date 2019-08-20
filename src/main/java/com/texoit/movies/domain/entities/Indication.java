package com.texoit.movies.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"movie_id", "premium_id"})
})
public class Indication extends AbstractEntity {

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
    @JoinColumn(name = "premium_id")
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    private Premium premium;

    /**
     * Só pode haver um para cada prêmio
     */
    @Column
    private Boolean winner;

    /**
     *
     */
    public Indication() {
    }

    /**
     * @param id {Long}
     */
    public Indication(final Long id) {
        super(id);
    }

    /**
     * @param movie   Movie
     * @param premium Premium
     * @param winner  boolean
     */
    public Indication(@NotNull final Movie movie, @NotNull final Premium premium, final Boolean winner) {
        this.movie = movie;
        this.premium = premium;
        this.winner = winner;
    }

    /**
     * @param id      Long
     * @param movie   Movie
     * @param premium Premium
     * @param winner  boolean
     */
    public Indication(final Long id, @NotNull final Movie movie, @NotNull final Premium premium, final Boolean winner) {
        super(id);
        this.movie = movie;
        this.premium = premium;
        this.winner = winner;
    }
}
