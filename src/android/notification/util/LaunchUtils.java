package de.appplant.cordova.plugin.notification.util;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import static android.content.Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;
import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import java.util.Random;

public final class LaunchUtils {

   private static int getIntentFlags() {
        // don't use pendingIntent.FLAG_MUTABLE or FLAG_IMMUTABLE
        // use numeric value instead to be able to compile api < 31
        int FLAG_MUTABLE = 1<<25;
        int FLAG_IMMUTABLE = 1<<26;

        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if ( android.os.Build.VERSION.SDK_INT >= 34 ) {
          flags |= FLAG_IMMUTABLE;
        } else if (  android.os.Build.VERSION.SDK_INT >= 31 ) {
          flags |= FLAG_MUTABLE;
        }
        return flags;
  }

    public static PendingIntent getBroadcastPendingIntent(Context context,
                                                          Intent intent,
                                                          int notificationId) {
        return  PendingIntent.getBroadcast(context, notificationId, intent, getIntentFlags());
    }

    public static PendingIntent getActivityPendingIntent(Context context,
                                                         Intent intent,
                                                         int notificationId) {
        return  PendingIntent.getActivity(context, notificationId, intent, getIntentFlags());
    }

    /***
     * Launch main intent from package.
     */
    public static void launchApp(Context context) {
        String pkgName  = context.getPackageName();

        Intent intent = context
            .getPackageManager()
            .getLaunchIntentForPackage(pkgName);

        if (intent == null)
            return;

        intent.addFlags(
            FLAG_ACTIVITY_REORDER_TO_FRONT
                | FLAG_ACTIVITY_SINGLE_TOP
                | FLAG_ACTIVITY_NEW_TASK
        );

        context.startActivity(intent);
    }
}
