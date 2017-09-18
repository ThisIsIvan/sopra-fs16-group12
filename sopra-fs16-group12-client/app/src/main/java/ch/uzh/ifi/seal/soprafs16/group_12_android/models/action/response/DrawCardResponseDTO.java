package ch.uzh.ifi.seal.soprafs16.group_12_android.models.action.response;

import ch.uzh.ifi.seal.soprafs16.group_12_android.models.action.ActionResponseDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;

/**
 * Created by Ivan on 19.04.2016.
 */
@JsonTypeName("drawCardResponseDTO")
public class DrawCardResponseDTO extends ActionResponseDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    public static final Long DRAW_CARD = 123123123123123L;

}
