package com.texoit.movies.domain.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = Movie.class,
        resolver = EntityIdResolver.class)
public class Movie extends AbstractEntity {

    /**
     *
     */
    @NotNull
    @Column(nullable = false, unique = true)
    private String title;

    /**
     *
     */
    @EqualsAndHashCode.Exclude
    @OneToMany(targetEntity = MovieStudio.class, mappedBy = "movie", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MovieStudio> studios;

    /**
     *
     */
    @EqualsAndHashCode.Exclude
    @OneToMany(targetEntity = MovieProducer.class, mappedBy = "movie", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MovieProducer> producers;

    /**
     *
     */
    @EqualsAndHashCode.Exclude
    @OneToMany(targetEntity = Indication.class, mappedBy = "movie", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Indication> indications;

    /**
     *
     */
    public Movie() {
    }

    /**
     * @param title String
     */
    public Movie(@NotNull String title) {
        this.title = title;
    }
}
