package ch.uzh.ifi.seal.soprafs16.group_12_android.models;

import java.io.Serializable;

/**
 * Created by rafael on 25/04/16.
 */
public class UserAuthenticationDTO implements Serializable{

    private String userToken;
    private Long userId;

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
