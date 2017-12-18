package ch.uzh.ifi.seal.soprafs16.service.Factory;

import ch.uzh.ifi.seal.soprafs16.constant.ItemType;
import ch.uzh.ifi.seal.soprafs16.model.*;
import ch.uzh.ifi.seal.soprafs16.model.cards.GameDeck;
import ch.uzh.ifi.seal.soprafs16.model.cards.PlayerDeck;
import ch.uzh.ifi.seal.soprafs16.model.cards.handCards.BulletCard;
import ch.uzh.ifi.seal.soprafs16.model.cards.handCards.HandCard;
import ch.uzh.ifi.seal.soprafs16.service.GameService;

public class DemoGame implements GameFactory {

    @Override
    public Long createGame(Long gameId) {
        try {
            Game game = gameRepo.findOne(gameId);
            //set Stationcard as Roundcard
            game.setCurrentRound(game.getRoundCardDeck().getCards().size() - 1);

            //put bulletcards into decks
            boolean creationSuccessful =placeBulletsDemo(game);

            //give some items to users
            creationSuccessful = creationSuccessful && giveItemsDemo(game);

            //place marshal in second wagon
            creationSuccessful = creationSuccessful && relocateMarshal(game, 1);

            //place 1 user in first wagon (after locomotive)
            creationSuccessful = creationSuccessful && relocatePlayer(game.getUsers().get(0), game.getWagons().get(0).getBottomLevel());

            //place 1 user in on top of 3rd wagon
            creationSuccessful = creationSuccessful && relocatePlayer(game.getUsers().get(1), game.getWagons().get(3).getTopLevel());

            gameRepo.save(game);
            gameCacherService.saveGame(game);

            if (creationSuccessful) {
                return gameId;
            } else {
                return (long) -1;
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return (long) -1;
        }
    }

    private boolean placeBulletsDemo(Game game) {
        try {
            addPlayerBulletToDeck(game.getUsers().get(0), game.getUsers().get(1));

            addPlayerBulletToDeck(game.getUsers().get(1), game.getUsers().get(0));
            addPlayerBulletToDeck(game.getUsers().get(1), game.getUsers().get(0));

            //both players got shot once by the marshal
            addMarshalBulletToDeck(game, game.getUsers().get(0));
            addMarshalBulletToDeck(game, game.getUsers().get(1));

            return true;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }

    private void addPlayerBulletToDeck(User fromUser, User toUser) {
        PlayerDeck<BulletCard> bulletsDeckFromUser = fromUser.getBulletsDeck();
        PlayerDeck<HandCard> handDeckToUser = toUser.getHandDeck();
        int id = bulletsDeckFromUser.getCards().size() - 1;

        //take a bullet from the shooter to the shot user
        BulletCard bulletCard = (BulletCard) bulletsDeckFromUser.getCards().get(id);
        bulletsDeckFromUser.getCards().remove(id);
        deckRepo.save(bulletsDeckFromUser);

        bulletCard.setDeck(handDeckToUser);
        handDeckToUser.getCards().add(bulletCard);
        cardRepo.save(bulletCard);

        relevelHandDeck(toUser);
    }

    private void addMarshalBulletToDeck(Game fromGame, User toUser) {
        GameDeck<BulletCard> bulletsDeckFrom = fromGame.getNeutralBulletsDeck();
        PlayerDeck<HandCard> handDeckToUser = toUser.getHandDeck();
        int id = bulletsDeckFrom.getCards().size() - 1;

        //take a bullet from the shooter to the shot user
        BulletCard bulletCard = (BulletCard) bulletsDeckFrom.getCards().get(id);
        bulletsDeckFrom.getCards().remove(id);
        deckRepo.save(bulletsDeckFrom);

        bulletCard.setDeck(handDeckToUser);
        handDeckToUser.getCards().add(bulletCard);
        cardRepo.save(bulletCard);

        relevelHandDeck(toUser);
    }

    //put an actioncard to the hiddendeck, so that the total number of handcard stays the same
    private void relevelHandDeck(User user) {
        HandCard handCard = (HandCard) user.getHandDeck().getCards().get(0);
        user.getHandDeck().getCards().remove(0);
        deckRepo.save(user.getHandDeck());

        handCard.setDeck(user.getHiddenDeck());
        user.getHiddenDeck().getCards().add(handCard);
        cardRepo.save(handCard);
    }

    private boolean giveItemsDemo(Game game) {
        try {
            boolean successful = true;
            successful = handOverItem(game.getUsers().get(0), ItemType.BAG, game) && successful;
            successful = handOverItem(game.getUsers().get(0), ItemType.CASE, game) && successful;

            successful = handOverItem(game.getUsers().get(1), ItemType.GEM, game) && successful;
            successful = handOverItem(game.getUsers().get(1), ItemType.GEM, game) && successful;
            successful = handOverItem(game.getUsers().get(1), ItemType.BAG, game) && successful;

            return successful;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }

    private boolean handOverItem(User user, ItemType itemType, Game game) {
        try {
            for (Wagon wagon : game.getWagons()) {
                int index = wagonLevelContainsItem(wagon.getBottomLevel(), itemType);
                if (index != -1) {
                    Item item = wagon.getBottomLevel().getItems().get(index);
                    item.setWagonLevel(null);
                    wagon.getBottomLevel().getItems().remove(index);
                    wagonLevelRepo.save(wagon.getBottomLevel());
                    itemRepo.save(item);
                    user.getItems().add(item);
                    item.setUser(user);
                    itemRepo.save(item);
                    userRepo.save(user);
                    return true;
                }
            }
            return false;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }

    private int wagonLevelContainsItem(WagonLevel wagonLevel, ItemType itemType) {
        int index = 0;
        for (Item item : wagonLevel.getItems()) {
            if (item.getItemType().equals(itemType)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    private boolean relocateMarshal(Game game, int wagonId) {
        try {
            WagonLevel wagonLevelNew = game.getWagons().get(wagonId).getBottomLevel();
            Marshal marshal = game.getMarshal();
            WagonLevel wagonLevelOld = marshal.getWagonLevel();
            wagonLevelOld.setMarshal(null);
            wagonLevelRepo.save(wagonLevelOld);
            marshal.setWagonLevel(wagonLevelNew);
            wagonLevelNew.setMarshal(marshal);
            wagonLevelRepo.save(wagonLevelNew);
            marshalRepo.save(marshal);

            return true;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }

    private boolean relocatePlayer(User user, WagonLevel wagonLevelNew) {
        try {
            WagonLevel wagonLevelOld = user.getWagonLevel();
            wagonLevelOld.getUsers().remove(user);
            wagonLevelRepo.save(wagonLevelOld);
            user.setWagonLevel(wagonLevelNew);
            wagonLevelNew.getUsers().add(user);
            wagonLevelRepo.save(wagonLevelNew);
            userRepo.save(user);

            return true;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return false;
        }
    }
}
