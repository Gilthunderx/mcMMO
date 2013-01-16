package com.gmail.nossr50.datatypes.popups;

import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.GenericPopup;
import org.getspout.spoutapi.gui.InGameHUD;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.gmail.nossr50.mcMMO;
import com.gmail.nossr50.datatypes.HudType;
import com.gmail.nossr50.datatypes.PlayerProfile;
import com.gmail.nossr50.datatypes.SpoutHud;
import com.gmail.nossr50.datatypes.buttons.McmmoButton;
import com.gmail.nossr50.datatypes.buttons.McmmoButton.Slot;
import com.gmail.nossr50.locale.LocaleLoader;

public class Menu extends GenericPopup {
    private McmmoButton hudButton;
    private McmmoButton escapeButton;
    private GenericLabel titleLabel = new GenericLabel();
    private GenericLabel escapeLabel = new GenericLabel();
    private static int centerX = 427 / 2;
    private static int centerY = 240 / 2;

    public Menu(final SpoutPlayer spoutPlayer, final PlayerProfile playerProfile) {
        //240, 427 are the bottom right
        titleLabel.setText(LocaleLoader.getString("Spout.Menu.Title"));
        titleLabel.setWidth(100);
        titleLabel.setHeight(100);
        titleLabel.setX(centerX - 35);
        titleLabel.setY((centerY / 2) - 20);

        escapeLabel.setText(LocaleLoader.getString("Spout.Menu.Exit"));
        escapeLabel.setWidth(100);
        escapeLabel.setHeight(100);
        escapeLabel.setX(titleLabel.getX() - 15);
        escapeLabel.setY(titleLabel.getY() + 10);

        hudButton = new McmmoButton(LocaleLoader.getString("Spout.Menu.HudButton.1", new Object[] {playerProfile.getHudType().toString()}), LocaleLoader.getString("Spout.Menu.HudButton.2"));
        hudButton.setWidth(120);
        hudButton.setHeight(20);
        hudButton.setX(centerX - (hudButton.getWidth() / 2));
        hudButton.setY(centerY / 2);
        hudButton.connect(new Slot() {
            @Override
            public void activate() {
                HudType nextHudType = playerProfile.getHudType().getNext();
                SpoutHud spoutHud = playerProfile.getSpoutHud();

                playerProfile.setHudType(nextHudType);
                spoutHud.initializeXpBar();
                spoutHud.updateXpBar();
                hudButton.setText("HUD Type: " + nextHudType.toString());
                hudButton.setDirty(true);
            }
        });

        escapeButton = new McmmoButton(LocaleLoader.getString("Spout.Menu.ExitButton"), null);
        escapeButton.setWidth(60);
        escapeButton.setHeight(20);
        escapeButton.setX(centerX - (escapeButton.getWidth() / 2));
        escapeButton.setY((centerY / 2) + (escapeButton.getHeight() * 2) + 5);
        escapeButton.connect(new Slot() {
            @Override
            public void activate() {
                spoutPlayer.getMainScreen().closePopup();
            }
        });

        attachWidget(mcMMO.p, hudButton);
        attachWidget(mcMMO.p, titleLabel);
        attachWidget(mcMMO.p, escapeLabel);
        attachWidget(mcMMO.p, escapeButton);

        InGameHUD inGameHud = spoutPlayer.getMainScreen();

        inGameHud.attachPopupScreen(this);
        inGameHud.setDirty(true);
    }
}
