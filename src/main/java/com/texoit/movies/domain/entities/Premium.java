package com.texoit.movies.domain.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "year"}),
})
public class Premium {

    /**
     *
     */
    @NotNull
    @Column(nullable = false)
    private String name;

    /**
     *
     */
    @Id
    @NotNull
    @Column(nullable = false)
    private Integer year;

    /**
     *
     */
    public Premium() {
    }

    /**
     * @param name String
     * @param year LocalDate
     */
    public Premium(final @NotNull String name, final @NotNull int year) {
        this.name = name;
        this.year = year;
    }

}
