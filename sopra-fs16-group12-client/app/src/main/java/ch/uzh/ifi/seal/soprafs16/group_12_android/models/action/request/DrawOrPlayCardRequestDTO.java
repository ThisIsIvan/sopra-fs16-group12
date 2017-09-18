package ch.uzh.ifi.seal.soprafs16.group_12_android.models.action.request;

import ch.uzh.ifi.seal.soprafs16.group_12_android.models.action.ActionRequestDTO;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 27.04.2016.
 */
@JsonTypeName("drawOrPlayCardRequestDTO")
public class DrawOrPlayCardRequestDTO extends ActionRequestDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    private List<Long> playableCardsId;

    public DrawOrPlayCardRequestDTO()
    {
        this.playableCardsId = new ArrayList<Long>();
    }

    public List<Long> getPlayableCardsId() {
        return playableCardsId;
    }

    public void setPlayableCardsId(List<Long> playableCardsId) {
        this.playableCardsId = playableCardsId;
    }
}
