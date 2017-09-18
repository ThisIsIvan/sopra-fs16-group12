package ch.uzh.ifi.seal.soprafs16.group_12_android.views;

import android.content.Context;
import android.widget.LinearLayout;
import ch.uzh.ifi.seal.soprafs16.group_12_android.Globals;
import ch.uzh.ifi.seal.soprafs16.group_12_android.R;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.ItemDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.UserDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.WagonDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by rafael on 01/05/16.
 */
public abstract class TrainView extends LinearLayout {

    private List<WagonView> wagonViews;
    private HashMap<Long, WagonLevelView> wagonLevelMap; // wagonLevelId -> wagonLevelView
    private HashMap<Long, FigurineView> figurineMap; // userId -> figurineView


    public TrainView(Context context, List<WagonDTO> wagons) {
        super(context);
        wagonViews = new ArrayList<>();
        wagonLevelMap = new HashMap<>();
        figurineMap = new HashMap<>();
        Boolean isLocomotive = true;

        for (final WagonDTO wagonDTO : wagons) {
            WagonLevelView topFloor = new WagonLevelView(context) {
                @Override
                public void onWagonLevelClick() {
                    TrainView.this.onWagonLevelClick(wagonDTO.getTopLevel().getId());
                }

                @Override
                public void onItemClick(Globals.ItemType itemType) {
                    TrainView.this.onItemClick(wagonDTO.getTopLevel().getId(), itemType);
                }
            };
            wagonLevelMap.put(wagonDTO.getTopLevel().getId(), topFloor);

            WagonLevelView bottomFloor = new WagonLevelView(context) {
                @Override
                public void onWagonLevelClick() {
                    TrainView.this.onWagonLevelClick(wagonDTO.getBottomLevel().getId());
                }

                @Override
                public void onItemClick(Globals.ItemType itemType) {
                    TrainView.this.onItemClick(wagonDTO.getBottomLevel().getId(), itemType);
                }
            };
            wagonLevelMap.put(wagonDTO.getBottomLevel().getId(), bottomFloor);

            WagonView wagon = new WagonView(context, isLocomotive, topFloor, bottomFloor);
            wagonViews.add(wagon);
            isLocomotive = false;

            this.addView(wagon);
        }

    }

    /**
     * Removes all saved figurine and item models from the wagon level model
     *
     * NON UI Thread
     * @param wagonLevelId: the id of the wagon level
     */
    public void removeAll(Long wagonLevelId) {
        wagonLevelMap.get(wagonLevelId).removeAll();
    }

    /**
     * Places a marshal model inside the wagon level model
     *
     * NON UI Thread
     * @param wagonLevelId: the id of the wagon level
     */
    public void placeMarshall(Long wagonLevelId) {
        FigurineView figurineView = new FigurineView(getContext(), -1L, R.drawable.figurine_marshal,R.drawable.figurine_marshal,R.drawable.figurine_marshal) {
            @Override
            public void onClick(Long userId) {
                // DO NOTHING
            }
        };
        wagonLevelMap.get(wagonLevelId).place(figurineView);
    }

    /**
     * Places a figurine model inside the wagon level model
     *
     * NON UI Thread
     * @param wagonLevelId: the wagon level's id
     * @param user: the userDTO
     */
    public void place(Long wagonLevelId, UserDTO user) {
        FigurineView figurineView = new FigurineView(getContext(), user.getId(), user.getCharacter().figurineSprite(), user.getCharacter().figurineCrosshairSprite(), user.getCharacter().figurinePunchSprite()) {
            @Override
            public void onClick(Long userId) {
                onFigurineClick(userId);

            }
        };
        this.figurineMap.put(user.getId(), figurineView);
        wagonLevelMap.get(wagonLevelId).place(figurineView);
    }

    public abstract void onFigurineClick(Long userId);
    public abstract void onWagonLevelClick(Long wagonLevelId);
    public abstract void onItemClick(Long wagonLevelId, Globals.ItemType itemType);

    /**
     * Places an item model inside the wagon level model
     *
     * NON UI Thread
     * @param wagonLevelId: the wagon level's id
     * @param item: the item DTO
     */
    @Deprecated
    public void place(Long wagonLevelId, ItemDTO item) {
        wagonLevelMap.get(wagonLevelId).place(item);
    }

    /**
     * Places an item model and it's number of items inside the wagon level model
     *
     * NON UI Thread
     * @param wagonLevelId: the wagon level's id
     * @param itemType: the item's type
     * @param nItems: the number of items
     */
    public void place(Long wagonLevelId, Globals.ItemType itemType, Integer nItems){
        wagonLevelMap.get(wagonLevelId).place(itemType,nItems);
    }

    /**
     * Starts highlighting the wagon level views
     *
     * UI Thread
     * @param wagonLevelIds: the wagon levels' id's
     */
    public void startHighlightWagonLevels(Long... wagonLevelIds) {
        for (Long wagonLevelId : wagonLevelIds) {
            wagonLevelMap.get(wagonLevelId).startHighlightWagonLevel();
        }
    }

    /**
     * Starts highlighting the figurines for shooting
     *
     * UI Thread
     * @param userIds: the users' ids
     */
    public void startShootHighlightFigurines(Long... userIds) {
        for (Long userId : userIds) {
            figurineMap.get(userId).startShootHighlight();
        }
    }

    /**
     * Starts highlighting the figurines for punching
     *
     * UI Thread
     * @param userIds: the users' ids
     */
    public void startPunchHighlightFigurines(Long... userIds) {
        for (Long userId : userIds) {
            figurineMap.get(userId).startPunchHighlight();
        }
    }

    /**
     * Stops highlighting the wagon levels
     *
     * UI Thread
     * @param wagonLevelIds: the wagon levels' ids
     */
    public void stopHighlightWagonLevels(Long... wagonLevelIds) {
        for (Long wagonLevelId : wagonLevelIds) {
            wagonLevelMap.get(wagonLevelId).stopHighlightWagonLevel();
        }
    }

    /**
     * Starts highlighting the item views inside the wagon level
     *
     * UI Thread
     * @param wagonLevelId: the waogn level's id
     */
    public void startHighlightItems(Long wagonLevelId) {
        wagonLevelMap.get(wagonLevelId).startHighlightItems();
    }

    /**
     * Stops highlighting the item views inside the wagon level
     *
     * UI Thread
     * @param wagonLevelId: the wagon level's id
     */
    public void stopHighlightItems(Long wagonLevelId) {
        wagonLevelMap.get(wagonLevelId).stopHighlightItems();
    }

    /**
     * Stops highlighting the figurines
     *
     * UI Thread
     * @param userIds: the users' ids
     */
    public void stopHighlightFigurines(Long... userIds) {
        for (Long userId : userIds) {
            figurineMap.get(userId).stopHighlight();
        }
    }

    /**
     * Refreshes all views with their updated models
     *
     * UI Thread
     */
    public void refresh() {
        for(WagonView view : wagonViews){
            view.refresh();
        }
    }
}
