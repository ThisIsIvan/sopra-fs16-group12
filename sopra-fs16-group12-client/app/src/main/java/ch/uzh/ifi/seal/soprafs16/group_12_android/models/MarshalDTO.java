package ch.uzh.ifi.seal.soprafs16.group_12_android.models;

import android.support.annotation.Nullable;

import java.io.Serializable;


/**
 * Created by Ivan on 07.04.2016.
 */

public class MarshalDTO implements Serializable{

    @Nullable
    private  Long id;

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(@Nullable Long id) {
        this.id = id;
    }

}
