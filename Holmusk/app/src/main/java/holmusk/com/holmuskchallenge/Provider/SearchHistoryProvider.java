package holmusk.com.holmuskchallenge.Provider;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by thearith on 29/4/16.
 */
public class SearchHistoryProvider extends SearchRecentSuggestionsProvider {

    public final static String AUTHORITY = "holmusk.com.holmuskchallenge.SearchHistoryProvider";
    public final static int MODE = DATABASE_MODE_QUERIES;

    public SearchHistoryProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }

}
