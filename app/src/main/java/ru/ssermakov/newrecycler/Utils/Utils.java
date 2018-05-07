package ru.ssermakov.newrecycler.Utils;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class Utils {

    public static int getScreenHeight(Context context) {

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.y;
    }


    public static int getValueToSubtract(int densityDpi) {

        int value = 0;

        switch (densityDpi) {
            case 640:
                value = 128;
                break;
            case 560:
                value = 112;
                break;
            case 480:
                value = 96;
                break;
            case 420:
                value = 84;
                break;
            case 400:
                value = 80;
                break;
            case 360:
                value = 72;
                break;
            case 340:
                value = 68;
                break;
            case 320:
                value = 64;
                break;
            case 300:
                value = 60;
                break;
            case 280:
                value = 56;
                break;
            case 260:
                value = 52;
                break;
            case 240:
                value = 48;
                break;
            case 160:
                value = 32;
                break;
            case 120:
                value = 24;
                break;

        }
        return value;
    }
}
