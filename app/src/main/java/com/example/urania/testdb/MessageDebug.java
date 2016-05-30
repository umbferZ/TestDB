package com.example.urania.testdb;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Umberto Ferracci from urania on 29/05/16.
 * email:   umberto.ferracci@gmail.com
 * Package: com.example.urania.testdb
 * Project: TestDB
 * Name:    MessageDebug
 */
public class MessageDebug {
    private static boolean isDebug = true;
    private static String debuger = "urania2";

    public static void message(Context context, String message) {
        if (isDebug) {
            Toast.makeText(context, message+"\naggiornato da urania", Toast.LENGTH_LONG).show();
            Log.v(debuger, message);
        }
    }
}
