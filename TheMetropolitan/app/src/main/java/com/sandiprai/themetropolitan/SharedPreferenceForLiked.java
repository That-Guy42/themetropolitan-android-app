package com.sandiprai.themetropolitan;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SharedPreferenceForLiked {

    public static final String PREFS_NAME = "APP1";
    public static final String LIKED_ARTICLES = "Liked_Articles";

    public SharedPreferenceForLiked() {
        super();
    }

    // This four methods are used for maintaining favorites.
    public void saveFavorites(Context context, List<String> likedList) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonSaved = gson.toJson(likedList);

        editor.putString(LIKED_ARTICLES, jsonSaved);

        editor.commit();
    }

    public void addFavorite(Context context, String articleId) {
        List<String> favorites = getFavorites(context);
        if (favorites == null) {
            favorites = new ArrayList<String>();
        }
        if (!favorites.contains(articleId)) {
            favorites.add(articleId);
        }
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, String articleId) {
        ArrayList<String > favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(articleId);
            saveFavorites(context, favorites);
        }
    }

    public ArrayList<String> getFavorites(Context context) {
        SharedPreferences settings;
        List<String > favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(LIKED_ARTICLES)) {
            String jsonFavorites = settings.getString(LIKED_ARTICLES, null);
            Gson gson = new Gson();

            String[] favoriteItems = gson.fromJson(jsonFavorites, String[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<String>(favorites);
        } else
            return null;

        return (ArrayList<String>) favorites;
    }
}
