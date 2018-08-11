package net.wizmy.android.quicksplitter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.accessibility.AccessibilityManager;

final public class Utils {

    static boolean isAccessibilityPermissionGranted(final Context context) {
        final AccessibilityManager accessibilityManager =
                (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);
        return accessibilityManager != null && accessibilityManager.isEnabled();
    }

    static AlertDialog getPermissionRequestDialog(final Context context) {
        return new AlertDialog.Builder(context)
                .setTitle(R.string.permission_request_title)
                .setMessage(R.string.permission_request_message)
                .setPositiveButton(android.R.string.ok,
                        (dialog, which) -> context.startActivity(getAccessibilitySettingsIntent()))
                .setNegativeButton(android.R.string.cancel, null)
                .create();
    }

    private static Intent getAccessibilitySettingsIntent() {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        return intent;
    }

}
