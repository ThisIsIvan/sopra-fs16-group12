package ch.uzh.ifi.seal.soprafs16.group_12_android.models.action.response;

import ch.uzh.ifi.seal.soprafs16.group_12_android.models.action.ActionResponseDTO;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;

/**
 * Created by Ivan on 19.04.2016.
 */
@JsonTypeName("playCardResponseDTO")
public class PlayCardResponseDTO extends ActionResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long playedCardId;

    public PlayCardResponseDTO(Long playedCardId) {
        this.playedCardId = playedCardId;
    }

    public void setPlayedCardId(Long playedCardId) {
        this.playedCardId = playedCardId;
    }

    public Long getPlayedCardId() {
        return playedCardId;
    }
}
