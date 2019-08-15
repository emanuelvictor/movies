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
public class MovieStudio extends AbstractEntity {

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
    @JoinColumn(name = "studio_id")
    private Studio studio;

    /**
     *
     */
    public MovieStudio() {
    }

    /**
     *
     * @param movie {Movie}
     * @param studio {Studio}
     */
    public MovieStudio(final Movie movie, final Studio studio) {
        this.movie = movie;
        this.studio = studio;
    }
}
