package ch.uzh.ifi.seal.soprafs16.group_12_android.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;
import ch.uzh.ifi.seal.soprafs16.group_12_android.Globals;
import ch.uzh.ifi.seal.soprafs16.group_12_android.R;
import ch.uzh.ifi.seal.soprafs16.group_12_android.TimeoutMutex;
import ch.uzh.ifi.seal.soprafs16.group_12_android.dialogs.LoadingDialog;
import ch.uzh.ifi.seal.soprafs16.group_12_android.engines.Engine;
import ch.uzh.ifi.seal.soprafs16.group_12_android.fragments.LobbyFragment;
import ch.uzh.ifi.seal.soprafs16.group_12_android.handlers.LobbyHandler;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.GameDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.UserDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.characters.Character;
import ch.uzh.ifi.seal.soprafs16.group_12_android.service.RestService;
import ch.uzh.ifi.seal.soprafs16.group_12_android.tasks.LobbyEngineTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ivan on 29.03.2016.
 */

/**
 * Starts LobbyFragment and UserListFragment.
 * Polls the server and refreshes UserListFragment.class every x seconds with the polled users.
 */
public class LobbyActivity extends FragmentActivity implements LobbyFragment.OnFragmentInteractionListener {

    private static final String TAG = "LobbyA";
    private static final Long TASK_INTERVAL = 50L;

    private LoadingDialog loadingDialog;
    private TimeoutMutex gameStartTimeoutMutex;
    private TimeoutMutex startGameTimeoutMutex;
    private TimeoutMutex leaveLobbyTimeoutMutex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"creating");
        setContentView(R.layout.activity_lobby);
        final LobbyFragment lobbyFragment = LobbyFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.lobbyContainer, lobbyFragment).commit();

        gameStartTimeoutMutex = new TimeoutMutex(20000L);
        startGameTimeoutMutex = new TimeoutMutex(10000000L);
        leaveLobbyTimeoutMutex = new TimeoutMutex(TimeoutMutex.NO_TIMEOUT);

        loadingDialog = new LoadingDialog(this);

        final LobbyHandler lobbyHandler = LobbyHandler.newInstance(lobbyFragment);
        Engine.getInstance().setWorkerTask(new LobbyEngineTask(getIntent().getExtras().getLong(Globals.getInstance().ARG_GAME_ID), lobbyHandler, getApplicationContext()) {
            @Override
            protected void OnGameRunning(GameDTO game) {
                try {
                    Engine.getInstance().suspend();
                    LobbyActivity.this.gameStartTimeoutMutex.acquire();
                    Log.i(TAG, "launching game activity");
                    Intent intent = new Intent(LobbyActivity.this, GameActivity.class);
                    intent.putExtra(GameActivity.ARG_GAME, game);
                    intent.putExtra(Globals.getInstance().ARG_GAME_ID, getIntent().getExtras().getLong(Globals.getInstance().ARG_GAME_ID));
                    startActivity(intent);
                } catch (TimeoutMutex.AcquiredException e) {
                    Log.w(TAG, e.getMessage());
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Engine.getInstance().start(TASK_INTERVAL);
    }

    /**
     * When hitting the back button displays a dialog that asks the user to
     * confirm that he wants to leave the lobby.
     */
    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("Are you sure you want to leave the lobby?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                LobbyActivity.this.OnLeaveGameClick();
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.create().show();
    }

    @Override
    public void OnCardClick(final Character character) {
        loadingDialog.show();
        RestService.getInstance(this).setCharacter(getIntent().getExtras().getLong(Globals.getInstance().ARG_GAME_ID), character, Globals.getInstance().userToken).enqueue(new Callback<UserDTO>() {
            @Override
            public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                if (response.body() != null) {
                    Log.i(TAG, "selected " + character.name());
                } else {
                    Toast.makeText(getApplicationContext(), "Character already selected.", Toast.LENGTH_LONG).show();
                    Log.w(TAG, "already selected " + character.name());
                }
                loadingDialog.dismiss();
            }

            @Override
            public void onFailure(Call<UserDTO> call, Throwable t) {
                Log.i(TAG, "server failed to select card");
                Log.e(TAG, t.getMessage());
                loadingDialog.dismiss();
            }
        });
    }

    @Override
    public void OnStartGameClick() {
        try {
            final Long timestamp = this.startGameTimeoutMutex.acquire();
            loadingDialog.show();
            Log.i(TAG, "sent request to start game");
            if (Globals.getInstance().userUsername.contains("Demo")) {
                RestService.getInstance(this).demoGame(getIntent().getExtras().getLong(Globals.getInstance().ARG_GAME_ID), Globals.getInstance().userToken).enqueue(new Callback<Long>() {

                    @Override
                    public void onResponse(Call<Long> call, Response<Long> response) {
                        try {
                            LobbyActivity.this.startGameTimeoutMutex.release(timestamp);
                        } catch (TimeoutMutex.TimeoutException e) {
                            Log.w(TAG, e.getMessage());
                        } finally {
                            loadingDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<Long> call, Throwable t) {
                        try {
                            LobbyActivity.this.startGameTimeoutMutex.release(timestamp);
                            Log.e(TAG, t.getMessage());
                        } catch (TimeoutMutex.TimeoutException e) {
                            Log.w(TAG, e.getMessage());
                        } finally {
                            loadingDialog.dismiss();
                        }
                    }
                });
            } else {
                RestService.getInstance(this).startGame(getIntent().getExtras().getLong(Globals.getInstance().ARG_GAME_ID), Globals.getInstance().userToken).enqueue(new Callback<Long>() {

                    @Override
                    public void onResponse(Call<Long> call, Response<Long> response) {
                        try {
                            LobbyActivity.this.startGameTimeoutMutex.release(timestamp);
                        } catch (TimeoutMutex.TimeoutException e) {
                            Log.w(TAG, e.getMessage());
                        } finally {
                            loadingDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<Long> call, Throwable t) {
                        try {
                            LobbyActivity.this.startGameTimeoutMutex.release(timestamp);
                            Log.e(TAG, t.getMessage());
                        } catch (TimeoutMutex.TimeoutException e) {
                            Log.w(TAG, e.getMessage());
                        } finally {
                            loadingDialog.dismiss();
                        }
                    }
                });
            }
        } catch (TimeoutMutex.AcquiredException e) {
            Log.w(TAG, e.getMessage());
        }
    }

    @Override
    public void OnLeaveGameClick() {
        try {
            leaveLobbyTimeoutMutex.acquire();
            Engine.getInstance().suspend();
            RestService.getInstance(this).leaveGame(getIntent().getExtras().getLong(Globals.getInstance().ARG_GAME_ID), Globals.getInstance().userToken).enqueue(new Callback<Long>() {
                @Override
                public void onResponse(Call<Long> call, Response<Long> response) {
                    Log.i(TAG, "successfully left game " + getIntent().getExtras().getLong(Globals.getInstance().ARG_GAME_ID));
                }

                @Override
                public void onFailure(Call<Long> call, Throwable t) {
                    Log.e(TAG, t.getMessage());
                    Log.w(TAG, "unsuccessfully left game " + getIntent().getExtras().getLong(Globals.getInstance().ARG_GAME_ID));
                }

            });
            Intent intent = new Intent(LobbyActivity.this, MenuActivity.class);
            startActivity(intent);
        } catch (TimeoutMutex.AcquiredException e) {
            e.printStackTrace();
        }
    }
}
