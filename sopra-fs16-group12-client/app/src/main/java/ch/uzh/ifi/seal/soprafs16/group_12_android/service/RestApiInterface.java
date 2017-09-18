package ch.uzh.ifi.seal.soprafs16.group_12_android.service;

import ch.uzh.ifi.seal.soprafs16.group_12_android.models.GameDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.UserAuthenticationDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.UserDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.action.ActionResponseDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.characters.Character;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface RestApiInterface {

    /**
     * Returns selected user based on userId
     * @param userId: id of requested user
     */
    @GET("/users/{userId}")
    Call<UserDTO> getUser(@Path("userId") Long userId);

    /**
     * Register a new userDTO on the server.
     * @param userDTO : UserDTO obj to save on the server
     */
    @POST("/users")
    Call<UserAuthenticationDTO> createUser(@Body UserDTO userDTO);

    /**
     * Get game with specified ID.
     * @param gameId: the game ID
     */
    @GET("/games/{gameId}")
    Call<GameDTO> getGame(@Path("gameId") Long gameId);

    /**
     * Create a lobby.
     * @param gameDTO: the game with a given name
     * @param token: The UserToken
     */
    @POST("/games")
    Call<Long> createGame(@Body GameDTO gameDTO, @Query("token") String token);

    /**
     * Returns all lobbies, which are Game object with GameStatus set to PENDING.
     */
    @GET("/games?status=PENDING")
    Call<List<GameDTO>> getLobbies();

    /**
     * Starts the specified game.
     * @param id: the game ID
     * @param token: the owner's token
     */
    @POST("/games/{gameId}/start")
    Call<Long> startGame(@Path("gameId") Long id, @Query("token") String token);

    /**
     * Joins a game lobby.
     * @param id: the game ID
     * @param token: the owner's token
     */
    @POST("/games/{gameId}/users")
    Call<Long> joinGame(@Path("gameId") Long id, @Query("token") String token);

    /**
     *
     * @param gameId
     * @param token
     * @param character
     */
    @PUT("/games/{gameId}/users/")
    Call<UserDTO> setCharacter(@Path("gameId") Long gameId, @Body Character character, @Query("token") String token);

    /**
     * Deletes the user from the game.
     * @param gameId: the game ID
     * @param token: the token of the user leaving the game
     */
    @DELETE("/games/{gameId}/users")
    Call<Long> leaveGame(@Path("gameId") Long gameId, @Query("token") String token);

    /**
     *
     * @param gameId: the game's Id
     * @param actionResponseDTO: the action response
     * @param token: the user's token
     * @return
     */
    @POST("/games/{gameId}/actions")
    Call<Long> sendAction(@Path("gameId") Long gameId, @Body ActionResponseDTO actionResponseDTO, @Query("token") String token);

    /**
     *
     * @param gameId: the games's Id
     * @param token: the user's token
     * @return
     */
    @POST("/games/{gameId}/startDemo")
    Call<Long> demoGame(@Path("gameId") Long gameId, @Query("token") String token);
}
