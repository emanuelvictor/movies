package com.texoit.movies.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"movie_id", "studio_id"}),
})
public class MovieStudio extends AbstractEntity {

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
    @JoinColumn(name = "studio_id")
    private Studio studio;

    /**
     *
     */
    public MovieStudio() {
    }

    /**
     * @param id Long
     */
    public MovieStudio(Long id) {
        super(id);
    }

    /**
     * @param movie  {Movie}
     * @param studio {Studio}
     */
    public MovieStudio(final @NotNull Movie movie, final @NotNull Studio studio) {
        this.movie = movie;
        this.studio = studio;
    }
}
