package com.texoit.movies.domain.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 *
 */
@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class AbstractEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1016827183036472876L;

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    /**
     *
     */
    protected AbstractEntity(Long id) {
        this.id = id;
    }
}
