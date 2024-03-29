package com.texoit.movies.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
public class Studio extends AbstractEntity {

    /**
     *
     */
    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    /**
     *
     */
    public Studio() {
    }

    /**
     *
     */
    public Studio(long id) {
        super(id);
    }

    /**
     * @param name {String}
     */
    public Studio(@NotNull String name) {
        this.name = name;
    }
}
