package com.texoit.movies.domain;

import com.texoit.movies.domain.generic.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)
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
