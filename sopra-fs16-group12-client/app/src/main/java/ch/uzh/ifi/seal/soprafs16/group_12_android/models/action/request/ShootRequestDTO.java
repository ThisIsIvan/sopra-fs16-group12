package ch.uzh.ifi.seal.soprafs16.group_12_android.models.action.request;


import ch.uzh.ifi.seal.soprafs16.group_12_android.models.action.ActionRequestDTO;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Ivan on 19.04.2016.
 */
@JsonTypeName("shootRequestDTO")
public class ShootRequestDTO extends ActionRequestDTO implements Serializable{

    private static final long serialVersionUID = 1L;

    private List<Long> shootableUserIds;

    public ShootRequestDTO()
    {
        this.shootableUserIds = new ArrayList<Long>();
    }

    public List<Long> getShootableUserIds() {
        return shootableUserIds;
    }

    public void setShootableUserIds(List<Long> shootableUserIds) {
        this.shootableUserIds = shootableUserIds;
    }
}
