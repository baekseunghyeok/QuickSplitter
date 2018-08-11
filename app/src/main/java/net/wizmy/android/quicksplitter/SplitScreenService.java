package net.wizmy.android.quicksplitter;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

public class SplitScreenService extends AccessibilityService {

    private static final String TAG = SplitScreenService.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        switch (event.getEventType()) {
            case AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED:
                if (BuildConfig.DEBUG) {
                    Log.d(TAG, "onAccessibilityEvent: " +
                            "class=" + event.getClassName() +
                            ", package=" + event.getPackageName());
                }
                if (getPackageName().equals(event.getPackageName())) {
                    // Toggle split-screen mode
                    performGlobalAction(GLOBAL_ACTION_TOGGLE_SPLIT_SCREEN);
                }
                break;
        }
    }

    @Override
    public void onInterrupt() {
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
