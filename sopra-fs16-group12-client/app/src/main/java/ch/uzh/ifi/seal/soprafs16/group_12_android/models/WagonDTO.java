package ch.uzh.ifi.seal.soprafs16.group_12_android.models;

import java.io.Serializable;

/**
 * Created by rafael on 04/04/16.
 */

public class WagonDTO implements Serializable {

    private Long id;
    private WagonLevelDTO topLevel;
    private WagonLevelDTO bottomLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public WagonLevelDTO getTopLevel() {
        return topLevel;
    }

    public void setTopLevel(WagonLevelDTO topLevel) {
        this.topLevel = topLevel;
    }

    public WagonLevelDTO getBottomLevel() {
        return bottomLevel;
    }

    public void setBottomLevel(WagonLevelDTO bottomLevel) {
        this.bottomLevel = bottomLevel;
    }
}
