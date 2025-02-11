/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package com.android.systemui;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Process;

import com.android.internal.messages.nano.SystemMessageProto.SystemMessage;

public class SysuiRestartReceiver extends BroadcastReceiver {

    public static String ACTION = "com.android.systemui.action.RESTART";
    public static String ACTION_THEME = "com.android.systemui.action.RESTART_THEME";

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean runAction = ACTION.equals(intent.getAction());
        boolean runThemeAction = ACTION_THEME.equals(intent.getAction());

        if (runAction) {
            String pkg = intent.getData().toString().substring(10);
            NotificationManager.from(context).cancel(pkg, SystemMessage.NOTE_PLUGIN);
        }
        
        if (runAction || runThemeAction) {
            Process.killProcess(Process.myPid());
        }
    }
}
