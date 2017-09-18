package ch.uzh.ifi.seal.soprafs16.model.cards.handCards;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

import ch.uzh.ifi.seal.soprafs16.constant.ItemType;
import ch.uzh.ifi.seal.soprafs16.model.Game;
import ch.uzh.ifi.seal.soprafs16.model.User;
import ch.uzh.ifi.seal.soprafs16.model.action.ActionRequestDTO;
import ch.uzh.ifi.seal.soprafs16.model.action.actionRequest.PunchRequestDTO;
import ch.uzh.ifi.seal.soprafs16.model.characters.Belle;

@Entity
@JsonTypeName("punchCard")
public class PunchCard extends ActionCard implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
}
