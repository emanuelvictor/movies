package com.texoit.movies.domain.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id",
        scope = Producer.class,
        resolver = EntityIdResolver.class)
public class Producer extends AbstractEntity {

    /**
     *
     */
    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    /**
     *
     */
    @EqualsAndHashCode.Exclude
    @OneToMany(targetEntity = MovieProducer.class, mappedBy = "producer", fetch = FetchType.EAGER)
    private Set<MovieProducer> movies;

    /**
     *
     */
    public Producer() {
    }

    /**
     *
     */
    public Producer(long id) {
        super(id);
    }

    /**
     * @param name {String}
     */
    public Producer(@NotNull String name) {
        this.name = name;
    }
}
