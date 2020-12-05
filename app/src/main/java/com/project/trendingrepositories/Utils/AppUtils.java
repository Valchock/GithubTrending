package com.project.trendingrepositories.Utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

public class AppUtils {

    private AppUtils() {
    }

    private static AppUtils instance = null;

    public static AppUtils getInstance() {
        if (instance == null) {
            synchronized (AppUtils.class) {
                if (instance == null) {
                    instance = new AppUtils();
                }
            }
        }
        return instance;
    }

    public String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("repository.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public String stringNotNull(String text) {
        if (text != null && !text.isEmpty())
            return text;
        else
            return "N/A";
    }
}
