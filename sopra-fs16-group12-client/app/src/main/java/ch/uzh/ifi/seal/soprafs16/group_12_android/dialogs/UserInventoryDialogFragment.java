package ch.uzh.ifi.seal.soprafs16.group_12_android.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import ch.uzh.ifi.seal.soprafs16.group_12_android.R;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.ItemDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.models.UserDTO;
import ch.uzh.ifi.seal.soprafs16.group_12_android.views.CardView;

/**
 * Created by rafael on 17/04/16.
 */
public class UserInventoryDialogFragment extends DialogFragment {

    private static final String ARG_USER = "UserIntentoryDialogFragment.ARG_USER";

    public static UserInventoryDialogFragment newInstance(UserDTO user) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        UserInventoryDialogFragment dialog = new UserInventoryDialogFragment();
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_user_inventory, null);

        UserDTO user = (UserDTO)getArguments().getSerializable(ARG_USER);

        Integer nBags = 0;
        Integer nGems = 0;
        Integer nCases = 0;
        Integer networth = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(v);

        for(ItemDTO item : user.getItems()){
            switch (item.getItemType()){
                case BAG:nBags++;
                    break;
                case GEM:
                    nGems++;
                    break;
                case CASE:
                    nCases++;
                    break;
            }
            networth += item.getValue();
        }

        TextView nCards = (TextView) v.findViewById(R.id.dialog_player_inventory_n_cards);


        TextView networthView = (TextView) v.findViewById(R.id.dialog_player_inventory_networth);
        FrameLayout bulletCardContainer = (FrameLayout) v.findViewById(R.id.dialog_player_inventory_n_bullets_container);

        TextView nBagsView = (TextView) v.findViewById(R.id.dialog_player_inventory_n_bags);
        nBagsView.setText(nBags.toString());

        TextView nGemsView = (TextView) v.findViewById(R.id.dialog_player_inventory_n_gems);
        nGemsView.setText(nGems.toString());

        TextView nCasesView = (TextView) v.findViewById(R.id.dialog_player_inventory_n_cases);
        nCasesView.setText(nCases.toString());

        nCards.setText(String.valueOf(user.getHandDeck().getCards().size()));

        CardView characterCard = new CardView(getContext(), user.getCharacter().characterCard()) {
            @Override
            public void onClick(Long cardId) {
                // DO NOTHING
            }
        }.refresh();
        characterCard.setClickable(false);
        characterCard.setAdjustViewBounds(true);
        FrameLayout characterContainer = (FrameLayout) v.findViewById(R.id.dialog_player_inventory_character_container);
        characterContainer.addView(characterCard);

        networthView.setText("$ " + networth.toString());

        Integer nBullets = user.getBulletsDeck().getCards().get(user.getBulletsDeck().getCards().size()-1).getBulletCounter();
        CardView bulletCard = new CardView(getContext(), user.getCharacter().bulletCard(nBullets)) {
            @Override
            public void onClick(Long cardId) {
                // DO NOTHING
            }
        }.refresh();
        bulletCard.setClickable(false);
        bulletCard.setAdjustViewBounds(true);
        bulletCardContainer.addView(bulletCard);

        builder.setCancelable(true);

        return builder.create();
    }
}
