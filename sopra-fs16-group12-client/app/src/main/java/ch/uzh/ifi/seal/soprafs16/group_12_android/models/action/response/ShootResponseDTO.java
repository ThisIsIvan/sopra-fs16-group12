package ch.uzh.ifi.seal.soprafs16.group_12_android.models.action.response;

import ch.uzh.ifi.seal.soprafs16.group_12_android.models.action.ActionResponseDTO;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;

/**
 * Created by Ivan on 19.04.2016.
 */
@JsonTypeName("shootResponseDTO")
public class ShootResponseDTO extends ActionResponseDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    private Long victimId;

    public ShootResponseDTO(Long victimId) {
        this.victimId = victimId;
    }

    public Long getVictimId() {
        return victimId;
    }

    public void setVictimId(Long victimId) {
        this.victimId = victimId;
    }
}
