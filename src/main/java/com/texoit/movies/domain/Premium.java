package com.texoit.movies.domain;

import com.texoit.movies.domain.generic.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.time.Year;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"name", "year"}),
})
public class Premium extends AbstractEntity {

    /**
     *
     */
    @NotNull
    @Column(nullable = false)
    private String name;

    /**
     *
     */
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
    
    /**
     * @param id   Long
     * @param name String
     * @param year LocalDate
     */
    public Premium(final Long id, final @NotNull String name, final @NotNull int year) {
        super(id);
        this.name = name;
        this.year = year;
    }
}
