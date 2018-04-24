package com.fgapps.servicetest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by (Engenharia) Felipe on 06/02/2018.
 */

public class InitAtBoot extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Intent serviceIntent = new Intent(context, ApplicationService.class);
            context.startService(serviceIntent);
        }
    }
}
