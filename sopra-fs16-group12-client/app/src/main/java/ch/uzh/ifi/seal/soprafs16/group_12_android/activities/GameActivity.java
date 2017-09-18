package ch.uzh.ifi.seal.soprafs16.group_12_android.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import ch.uzh.ifi.seal.soprafs16.group_12_android.Globals;
import ch.uzh.ifi.seal.soprafs16.group_12_android.R;
import ch.uzh.ifi.seal.soprafs16.group_12_android.TimeoutMutex;
import ch.uzh.ifi.seal.soprafs16.group_12_android.dialogs.*;
import ch.uzh.ifi.seal.soprafs16.group_12_android.engines.Engine;
import ch.uzh.ifi.seal.soprafs16.group_12_android.fragments.GameFragment;
import ch.uzh.ifi.seal.soprafs16.group_12_android.handlers.GameHandler;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.GameDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.UserDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.service.RestService;
import ch.uzh.ifi.seal.soprafs16.group_12_android.tasks.GameEngineTask;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

/**
 * Created by rafael on 30/03/16.
 */
public class GameActivity extends FragmentActivity implements GameFragment.InteractionListener,
        ScoreboardDialogFragment.InteractionListener,
        TutorialDialogFragment.InteractionListener {

    public static final String ARG_GAME = "GameActivity.ARG_GAME";
    private static final String TAG = "GameA";
    private static final Long TASK_INTERVAL = 100L;

    private LoadingDialog loadingDialog;
    private GameEngineTask gameEngineTask;

    private ManualDialogFragment manualDialogFragment;
    private GameFragment gameFragment;

    private TimeoutMutex manualTimeoutMutex;
    private TimeoutMutex scoreboardTimeoutMutex;
    private TimeoutMutex userInventoryTimeoutMutex;
    private TimeoutMutex gameLeaveTimeoutMutex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadingDialog = new LoadingDialog(this);
        TutorialDialogFragment.newInstance().show(getSupportFragmentManager(), "tutorial dialog");
        Long gameId = getIntent().getExtras().getLong(Globals.getInstance().ARG_GAME_ID);
        Integer nUsers = ((GameDTO) getIntent().getExtras().getSerializable(GameActivity.ARG_GAME)).getUsers().size();
        manualTimeoutMutex = new TimeoutMutex(2000L);
        scoreboardTimeoutMutex = new TimeoutMutex();
        userInventoryTimeoutMutex = new TimeoutMutex();
        gameLeaveTimeoutMutex = new TimeoutMutex(TimeoutMutex.NO_TIMEOUT);
        setContentView(R.layout.activity_game);
        this.gameFragment = GameFragment.newInstance((GameDTO) getIntent().getSerializableExtra(ARG_GAME));
        this.manualDialogFragment = ManualDialogFragment.newInstance();
        final GameHandler gameHandler = GameHandler.newInstance(gameFragment);
        this.gameEngineTask = new GameEngineTask(gameFragment, gameHandler, gameId, nUsers) {
            @Override
            protected void onGameFinish(GameDTO game, ArrayList<Long> gunslingerUserIds, Long winnerId) {
                Engine.getInstance().suspend();
                if (Globals.getInstance().userId == winnerId) {
                    gameHandler.obtainMessage(GameHandler.YAHOO_SOUND).sendToTarget();
                }
                ScoreboardDialogFragment d = ScoreboardDialogFragment.newInstanceFinished(game.getUsers(), gunslingerUserIds, winnerId);
                d.show(getSupportFragmentManager(), "scoreboardDialog");
            }
        };
        Engine.getInstance().setWorkerTask(gameEngineTask);
        getSupportFragmentManager().beginTransaction().replace(R.id.game_container, gameFragment).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Engine.getInstance().start(TASK_INTERVAL);
    }

    /**
     * Checks if gameFragment is visible. If so, displays the leave game dialog, if not, displays the gameFragment.
     */
    @Override
    public void onBackPressed() {
        if (manualDialogFragment.isVisible()) {
            manualDialogFragment.dismiss();
        } else if (gameFragment.isVisible()){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("Are you sure you want to leave the game?");
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    GameActivity.this.onLeaveGame();
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
     * Click on Scoreboard button
     */
    @Override
    public void onScoreBoardButtonClick() {
        try {
            final Long timestamp = GameActivity.this.scoreboardTimeoutMutex.acquire();
            loadingDialog.show();
            RestService.getInstance(this).getGame(getIntent().getExtras().getLong(Globals.getInstance().ARG_GAME_ID)).enqueue(new Callback<GameDTO>() {
                @Override
                public void onResponse(Call<GameDTO> call, Response<GameDTO> response) {
                    try {
                        GameActivity.this.scoreboardTimeoutMutex.release(timestamp);
                    } catch (TimeoutMutex.TimeoutException e) {
                        Log.d(TAG, e.getMessage());
                    } finally {
                        loadingDialog.dismiss();
                        ScoreboardDialogFragment d = ScoreboardDialogFragment.newInstanceRunning(response.body().getUsers());
                        d.show(getSupportFragmentManager(), "scoreboardDialog");
                    }
                }

                @Override
                public void onFailure(Call<GameDTO> call, Throwable t) {
                    try {
                        loadingDialog.dismiss();
                        Log.e(TAG, t.getMessage());
                        GameActivity.this.scoreboardTimeoutMutex.release(timestamp);
                    } catch (TimeoutMutex.TimeoutException e) {
                        Log.d(TAG, e.getMessage());
                    }
                }
            });
        } catch (TimeoutMutex.AcquiredException e) {
            Log.w(TAG, e.getMessage());
        }
    }

    /**
     * Click on Manual Button
     */
    @Override
    public void onManualButtonClick() {
        try {
            manualTimeoutMutex.acquire();
            manualDialogFragment.show(getSupportFragmentManager(),"manual");
        } catch (TimeoutMutex.AcquiredException e) {
            Log.w(TAG,e.getMessage());
        }
    }

    /**
     * UI Thread
     * Click on Leave game in scoreboard dialog after game has finished
     */
    @Override
    public void onScoreboardGameLeave() {
        onLeaveGame();
    }

    /**
     * UI Thread
     *
     * @param userId: the usedId
     */
    @Override
    public void onFigurineClick(Long userId) {
        Log.d(TAG, "figurine click");
        if (!Globals.getInstance().chosenUserId.put(userId)) {
            try {
                final Long timestamp = GameActivity.this.userInventoryTimeoutMutex.acquire();
                loadingDialog.show();
                RestService.getInstance(getApplicationContext()).getUser(userId).enqueue(new Callback<UserDTO>() {
                    @Override
                    public void onResponse(Call<UserDTO> call, Response<UserDTO> response) {
                        try {
                            GameActivity.this.userInventoryTimeoutMutex.release(timestamp);
                        } catch (TimeoutMutex.TimeoutException e) {
                            Log.w(TAG, e.getMessage());
                        } finally {
                            loadingDialog.dismiss();
                            UserInventoryDialogFragment dialog = UserInventoryDialogFragment.newInstance(response.body());
                            dialog.show(getSupportFragmentManager().beginTransaction(), "userInventory");
                            Log.d(TAG, "showing " + response.body().getName() + " inventory");
                        }
                    }

                    @Override
                    public void onFailure(Call<UserDTO> call, Throwable t) {
                        try {
                            Log.e(TAG, t.getMessage());
                            GameActivity.this.userInventoryTimeoutMutex.release(timestamp);
                            loadingDialog.dismiss();
                        } catch (TimeoutMutex.TimeoutException e) {
                            Log.e(TAG, e.getMessage());
                        }
                    }
                });
            } catch (TimeoutMutex.AcquiredException e) {
                Log.w(TAG, e.getMessage());
            }
        }
    }

    /**
     * UI Thread
     *
     * @param wagonLevelId
     */
    @Override
    public void onWagonLevelClick(Long wagonLevelId) {
        Globals.getInstance().chosenWagonLevelId.put(wagonLevelId);
    }

    /**
     * UI Thread
     *
     * @param wagonLevelId
     * @param itemType
     */
    @Override
    public void onItemClick(Long wagonLevelId, Globals.ItemType itemType) {
        Globals.getInstance().chosenItemType.put(itemType);
    }

    /**
     * UI Thread
     */
    private void onLeaveGame() {
        Log.w(TAG, "leaving game");
        try {
            GameActivity.this.gameLeaveTimeoutMutex.acquire();
            Engine.getInstance().suspend();
            RestService.getInstance(this).leaveGame(getIntent().getExtras().getLong(Globals.getInstance().ARG_GAME_ID), Globals.getInstance().userToken).enqueue(new Callback<Long>() {
                @Override
                public void onResponse(Call<Long> call, Response<Long> response) {
                    Log.d(TAG, "game leave successful");
                }

                @Override
                public void onFailure(Call<Long> call, Throwable t) {
                    Log.w(TAG, "game leave unsuccessful");
                    Log.e(TAG, t.getMessage());
                }
            });
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(intent);
        } catch (TimeoutMutex.AcquiredException e) {
            Log.w(TAG, e.getMessage());
        }
    }

    /**
     * UI Thread
     */
    @Override
    public void onTutorialDialogComplete() {
        GameActivity.this.gameEngineTask.onTutorialComplete();
    }


}
