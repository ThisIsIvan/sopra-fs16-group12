package ch.uzh.ifi.seal.soprafs16.model.characters;


import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;

import javax.persistence.Entity;

@Entity
@JsonTypeName("ghost")
public class Ghost extends Character implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
}
