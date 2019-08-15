package com.texoit.movies.domain;

import com.texoit.movies.domain.generic.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

//@Data
//@Entity
//@EqualsAndHashCode(callSuper = true)
//public class Indicated extends AbstractEntity {
//
//    /**
//     *
//     */
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "movie_id")
//    private Movie movie;
//
//    /**
//     *
//     */
//    @ManyToOne(optional = false)
//    @JoinColumn(name = "producer_id")
//    private Premium producer;
//
//}
