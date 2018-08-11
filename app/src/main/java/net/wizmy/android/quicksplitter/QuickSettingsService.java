package net.wizmy.android.quicksplitter;

import android.content.Intent;
import android.graphics.drawable.Icon;
import android.service.quicksettings.Tile;
import android.service.quicksettings.TileService;
import android.widget.Toast;

public class QuickSettingsService extends TileService {

    /* set true after we know how to get current split-screen mode */
    private static final boolean SUPPORT_TILE_TOGGLING = false;

    @Override
    public void onTileAdded() {
        super.onTileAdded();
    }

    @Override
    public void onTileRemoved() {
        super.onTileRemoved();
    }

    @Override
    public void onStartListening() {
        super.onStartListening();

        updateTile(getSplitScreenModeEnabled());
    }

    @Override
    public void onStopListening() {
        super.onStopListening();
    }

    @Override
    public void onClick() {
        super.onClick();

        // don't work in lock screen
        if (isLocked()) {
            return;
        }

        if (!Utils.isAccessibilityPermissionGranted(getApplicationContext())) {
            unlockAndRun(() -> showDialog(Utils.getPermissionRequestDialog(getApplicationContext())));
        } else {
            Tile tile = getQsTile();
            final boolean enable = tile.getState() == Tile.STATE_INACTIVE;
            updateTile(enable);

            // Close notification panel
            Intent closeIntent = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            sendBroadcast(closeIntent);

            // Important:
            // Showing toast let SplitScreenService retrieve AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED event
            Toast.makeText(getApplicationContext(), R.string.toast_message, Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTile(final boolean active) {
        Icon newIcon;
        String newLabel;
        int newState;

        if (SUPPORT_TILE_TOGGLING) {
            if (active) {
                newIcon = Icon.createWithResource(getApplicationContext(),
                        R.drawable.ic_split_white_24dp);
                newLabel = getString(R.string.tile_label);
                newState = Tile.STATE_ACTIVE;
            } else {
                newIcon = Icon.createWithResource(getApplicationContext(),
                        R.drawable.ic_split_white_24dp);
                newLabel = getString(R.string.tile_label);
                newState = Tile.STATE_INACTIVE;
            }
        } else {
            newIcon = Icon.createWithResource(getApplicationContext(),
                    R.drawable.ic_split_white_24dp);
            newLabel = getString(R.string.tile_label);
            newState = Tile.STATE_INACTIVE;
        }

        Tile tile = getQsTile();
        tile.setIcon(newIcon);
        tile.setLabel(newLabel);
        tile.setState(newState);

        tile.updateTile();
    }

    private boolean getSplitScreenModeEnabled() {
        if (SUPPORT_TILE_TOGGLING) {
            return false; // TODO: get current split-screen mode
        } else {
            return false;
        }
    }
}
