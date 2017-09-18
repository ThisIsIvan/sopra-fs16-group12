package ch.uzh.ifi.seal.soprafs16.group_12_android.models.action.request;

import ch.uzh.ifi.seal.soprafs16.group_12_android.models.action.ActionRequestDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 19.04.2016.
 */

@JsonTypeName("collectItemRequestDTO")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CollectItemRequestDTO extends ActionRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Long> collectableItemIds;

    private Boolean hasCase;

    private Boolean hasGem;

    private Boolean hasBag;

    public CollectItemRequestDTO()
    {
        this.collectableItemIds = new ArrayList<Long>();
    }

    public Boolean getHasCase() {
        return hasCase;
    }

    public void setHasCase(Boolean hasCase) {
        this.hasCase = hasCase;
    }

    public Boolean getHasGem() {
        return hasGem;
    }

    public void setHasGem(Boolean hasGem) {
        this.hasGem = hasGem;
    }

    public Boolean getHasBag() {
        return hasBag;
    }

    public void setHasBag(Boolean hasBag) {
        this.hasBag = hasBag;
    }
}
