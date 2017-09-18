package ch.uzh.ifi.seal.soprafs16.group_12_android.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;
import ch.uzh.ifi.seal.soprafs16.group_12_android.Globals;
import ch.uzh.ifi.seal.soprafs16.group_12_android.R;
import ch.uzh.ifi.seal.soprafs16.group_12_android.TimeoutMutex;
import ch.uzh.ifi.seal.soprafs16.group_12_android.dialogs.*;
import ch.uzh.ifi.seal.soprafs16.group_12_android.engines.Engine;
import ch.uzh.ifi.seal.soprafs16.group_12_android.fragments.MenuFragment;
import ch.uzh.ifi.seal.soprafs16.group_12_android.handlers.MenuHandler;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.GameDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.UserAuthenticationDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.UserDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.service.RestService;
import ch.uzh.ifi.seal.soprafs16.group_12_android.tasks.MenuEngineTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 30.03.2016.
 */

/**
 * The starting activity which allows the user to
 * log in,
 * view the manual,
 * view all existing lobbies and join a lobby of his choosing,
 * and create a lobby with a name of his choosing.
 */
public class MenuActivity extends FragmentActivity implements MenuFragment.InteractionListener,
        RegisterDialogFragment.InteractionListener,
        CreateGameDialogFragment.InteractionListener,
        JoinLobbyDialogFragment.InteractionListener {

    private static final String TAG = "MenuA";
    private static final Long TASK_INTERVAL = 100L;

    private MenuFragment menuFragment;
    private ManualDialogFragment manualDialogFragment;

    private TimeoutMutex manualTimeoutMutex;
    private TimeoutMutex loginButtonTimeoutMutex;
    private TimeoutMutex joinButtonTimeoutMutex;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        manualTimeoutMutex = new TimeoutMutex(2000L);
        loginButtonTimeoutMutex = new TimeoutMutex(2000L);
        joinButtonTimeoutMutex = new TimeoutMutex();
        loadingDialog = LoadingDialog.newInstance(this);
        this.menuFragment = MenuFragment.newInstance();
        this.manualDialogFragment = ManualDialogFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_menu_container, menuFragment).commit();
        MenuHandler menuHandler = MenuHandler.newInstance(menuFragment);
        Engine.getInstance().setWorkerTask(new MenuEngineTask(menuHandler));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Engine.getInstance().start(TASK_INTERVAL);
    }

    @Override
    public void onBackPressed() {
        if (manualDialogFragment.isVisible()) {
            manualDialogFragment.dismiss();
        } else if (menuFragment.isVisible()) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Are you sure you want to leave the game?");
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Engine.getInstance().suspend();
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            dialog.create().show();
        }
    }

    /**
     * User has clicked the register button in Menu activity
     */
    @Override
    public void onRegisterButtonClick() {
        try {
            loginButtonTimeoutMutex.acquire();
            RegisterDialogFragment.newInstance().show(getSupportFragmentManager(), "registerDialog");
        } catch (TimeoutMutex.AcquiredException e) {
            Log.w(TAG, e.getMessage());
        }
    }

    /**
     * User clicked register in the Register Dialog
     *
     * @param username: the name the user wants to register
     */
    @Override
    public void onRegisterDialogPositiveClick(String username) {
        loadingDialog.show();
        final Uri usernameUri = Uri.parse(username);
        UserDTO myUser = new UserDTO();
        myUser.setName(username);
        myUser.setUsername(username);
        RestService.getInstance(getApplicationContext()).createUser(myUser).enqueue(new Callback<UserAuthenticationDTO>() {
            @Override
            public void onResponse(Call<UserAuthenticationDTO> call, Response<UserAuthenticationDTO> response) {
                if (response.code() == 201) {
                    Globals.getInstance().userUsername = usernameUri.toString();
                    Globals.getInstance().userToken = response.body().getUserToken();
                    Globals.getInstance().userId = response.body().getUserId();
                    Globals.getInstance().isLoggedIn = true;
                    Log.d(TAG, "created User" + usernameUri.toString());
                } else {
                    MessageDialogFragment.newInstance("Username Taken!").show(getSupportFragmentManager(), "username rejected message");
                }
                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(Call<UserAuthenticationDTO> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                Toast.makeText(getApplicationContext(), getString(R.string.communication_error), Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
            }
        });
    }

    /**
     * When clicking the Logout button, the user gets logged out
     */
    @Override
    public void onLogoutButtonClick() {
        Globals.getInstance().userId = null;
        Globals.getInstance().userToken = null;
        Globals.getInstance().userUsername = null;
        Globals.getInstance().isLoggedIn = false;

        MessageDialogFragment.newInstance("Logged out").show(getSupportFragmentManager(), "logout message");
    }

    /**
     * When clicking the Manual button, the manual dialog appears
     */
    @Override
    public void onManualButtonClick() {
        try {
            manualTimeoutMutex.acquire();
            manualDialogFragment.show(getSupportFragmentManager(), "manual dialog");
        } catch (TimeoutMutex.AcquiredException e) {
            Log.w(TAG,e.getMessage());
        }
    }

    /**
     * When clicking the "Create Lobby"-button" a dialog appears that prompts you to set a lobbyname.
     */
    @Override
    public void onCreateGameButtonClick() {
        if (Globals.getInstance().isLoggedIn) {
            CreateGameDialogFragment.newInstance().show(getSupportFragmentManager(), "userNameDialog");
        } else {
            MessageDialogFragment.newInstance("Not logged in").show(getSupportFragmentManager(), "not logged in message");
        }
    }

    /**
     * If a valid lobby name was entered in the CreateGameDialogFragment.class a LobbyActivity will be initiated.
     * Otherwise the CreateGameDialogFragment will be shown again and the user is asked to enter a valid name.
     *
     * @param name: the game name
     */
    @Override
    public void onCreateGameRegister(String name) {
        loadingDialog.show();
        Uri gameNameUri = Uri.parse(name);
        GameDTO game = new GameDTO();
        game.setName(gameNameUri.toString());
        game.setOwner(Globals.getInstance().userToken);

        RestService.getInstance(getApplicationContext()).createGame(game, Globals.getInstance().userToken).enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                if (response.body() != null) {
                    Engine.getInstance().suspend();
                    Log.d(TAG, "created game " + response.body().toString());
                    Intent intent = new Intent(MenuActivity.this, LobbyActivity.class);
                    intent.putExtra(Globals.getInstance().ARG_GAME_ID, response.body());
                    startActivity(intent);
                } else {
                    Log.d(TAG, "Game unsuccessfully created, spielId is null");
                }
                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Long> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                Toast.makeText(getApplicationContext(), getString(R.string.communication_error), Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
            }
        });
    }

    /**
     * When clicking on the "Join Lobby"-button a dialog appears that shows the user a list of all
     * existing lobbies.
     */
    @Override
    public void onJoinGameButtonClick() {
        if (Globals.getInstance().isLoggedIn) {
            try {
                final Long timestamp = joinButtonTimeoutMutex.acquire();
                loadingDialog.show();
                RestService.getInstance(getApplicationContext()).getLobbies().enqueue(new Callback<List<GameDTO>>() {
                    @Override
                    public void onResponse(Call<List<GameDTO>> call, Response<List<GameDTO>> response) {
                        try {
                            joinButtonTimeoutMutex.release(timestamp);
                            if (response.body().size() == 0) {
                                MessageDialogFragment.newInstance("No lobbies online").show(getSupportFragmentManager(), "no lobbies message");
                                Log.d(TAG, "no lobbies online");
                            } else {
                                JoinLobbyDialogFragment.newInstance((ArrayList<GameDTO>) response.body()).show(getSupportFragmentManager(), "lobbyDialog");
                            }
                        } catch (TimeoutMutex.TimeoutException e) {
                            Log.w(TAG, e.getMessage());
                        } finally {
                            loadingDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<GameDTO>> call, Throwable t) {
                        try {
                            joinButtonTimeoutMutex.release(timestamp);
                            Log.e(TAG, t.getMessage());
                            Toast.makeText(getApplicationContext(), getString(R.string.communication_error), Toast.LENGTH_LONG).show();
                        } catch (TimeoutMutex.TimeoutException e) {
                            Log.w(TAG, e.getMessage());
                        } finally {
                            loadingDialog.dismiss();
                        }
                    }
                });
            } catch (TimeoutMutex.AcquiredException e) {
                Log.w(TAG, e.getMessage());
            }
        } else {
            MessageDialogFragment.newInstance("Not logged in").show(getSupportFragmentManager(), "login message");
            Log.d(TAG, "not logged in");
        }
    }

    /**
     * When clicking the positive button on the JoinLobbyDialogFragment the user joins the selected lobby,
     * if the lobby is not full.
     *
     * @param gameId: the chose lobby
     */
    @Override
    public void onJoinLobbyDialogJoinClick(final Long gameId) {
        Log.d(TAG, "selected game " + gameId.toString());
        loadingDialog.show();
        RestService.getInstance(getApplicationContext()).getGame(gameId).enqueue(new Callback<GameDTO>() {
            @Override
            public void onResponse(Call<GameDTO> call, Response<GameDTO> response) {
                if (response.body().getUsers().size() < 4) {
                    Log.d(TAG, "joining game");
                    RestService.getInstance(getApplicationContext()).joinGame(gameId, Globals.getInstance().userToken).enqueue(new Callback<Long>() {
                        @Override
                        public void onResponse(Call<Long> call, Response<Long> response) {
                            Log.d(TAG, "game joined");
                            Intent intent = new Intent(MenuActivity.this, LobbyActivity.class);
                            intent.putExtra(Globals.getInstance().ARG_GAME_ID, gameId);
                            startActivity(intent);
                            loadingDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<Long> call, Throwable t) {
                            Log.e(TAG, t.getMessage());
                            Toast.makeText(getApplicationContext(), getString(R.string.communication_error), Toast.LENGTH_SHORT).show();
                            loadingDialog.dismiss();
                        }
                    });
                } else {
                    MessageDialogFragment.newInstance("Lobby is full").show(getSupportFragmentManager(), "lobby full message");
                    Log.w(TAG, "game full");
                }
            }

            @Override
            public void onFailure(Call<GameDTO> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                loadingDialog.dismiss();
            }
        });
    }
}
