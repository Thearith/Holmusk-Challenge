package holmusk.com.holmuskchallenge.Activities.SearchActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.SearchRecentSuggestions;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import holmusk.com.holmuskchallenge.Activities.MainActivity.MainActivity;
import holmusk.com.holmuskchallenge.Models.Food;
import holmusk.com.holmuskchallenge.R;
import holmusk.com.holmuskchallenge.Resources.Fragments.FoodFragment;
import holmusk.com.holmuskchallenge.Resources.HttpClient.ApiConstants;
import holmusk.com.holmuskchallenge.Resources.HttpClient.HolmuskService;
import holmusk.com.holmuskchallenge.Resources.HttpClient.JSONParser;
import holmusk.com.holmuskchallenge.Storage.Databases.DatabaseWrapper;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class SearchActivity extends AppCompatActivity implements FoodFragment.FoodFragmentCallback,
        SearchView.OnQueryTextListener  {

    // constants

    private static final String TAG             = SearchActivity.class.getSimpleName();
    private static final String LOADING_THREAD  = TAG + " loading_thread";
    private static final int LOADING_INTERVAL   = 200;

    public static final int SEARCH_RESULT       = 1;


    // variables

    private DatabaseWrapper wrapper;

    FragmentManager fragmentManager;
    List<Fragment> activeCenterFragments = new ArrayList<>();

    private SweetAlertDialog loadingDialog;
    private SearchView searchView;
    private TextView noResultsText;

    private SweetAlertDialog noInternetDialog;

    // loading timer
    private HandlerThread loadingThread;
    private Handler loadingHandler;
    private Runnable loadingRunnable;
    private int countDownTimer = 0;
    private boolean isLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initializeStorage();
        setUpActionBar();
        initializeDialogs();
        setUpWidgets();
        initializeLoadingThread();

        handleIntent(getIntent());
    }

    @Override
    public void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyLoadingThread();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivityForResult(intent, SEARCH_RESULT);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if(id == R.id.action_search) {
            onSearchRequested();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            handleSearchQuery(query);
        }
    }



    /*
    * Implemented methods
    * */

    @Override
    public void addFood(Food food) {
        wrapper.addFoodToday(food);
        showAddFoodToast(food.getName());
    }

    @Override
    public void removeFood(Food food) {
        //DO NOTHING
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchView.clearFocus();
        ConnectivityManager connManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connManager.getActiveNetworkInfo();

        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            showNoInternetDialog();
            return true;
        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }


    /*
    * Action bar methods
    * */

    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    /*
    * Storage methods
    * */

    private void initializeStorage() {
        initializeDatabase();
    }

    private void initializeDatabase() {
        wrapper = DatabaseWrapper.getInstance(this);
    }


    /*
    * Search methods
    * */

    private void handleSearchQuery(String query) {
        showLoadingDialog();
        doSearchRequest(query);
    }

    private void doSearchRequest(String query) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BASE_URL)
                .build();

        HolmuskService service = retrofit.create(HolmuskService.class);
        Call<ResponseBody> result = service.getListofFood(query);
        result.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responseBody = response.body().string();
                    ArrayList<Food> foods = JSONParser.convertToFood(responseBody);
                    addFoodResultsToView(foods);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    /*
    * Widget methods
    * */

    private void setUpWidgets() {
        initializeNoResultsText();
        setUpFragmentManager();
    }

    private void initializeNoResultsText() {
        noResultsText = (TextView) findViewById(R.id.noResultsText);
    }

    private void setNoResultsTextVisibility(int visibility) {
        noResultsText.setVisibility(visibility);
    }

    private void setUpFragmentManager() {
        fragmentManager = getSupportFragmentManager();
    }

    private void addFoodResultsToView(ArrayList<Food> foods) {
        clearAllFragments();
        setUpNewFragments(foods);

        int visibility = foods.isEmpty() ? View.VISIBLE : View.GONE;
        setNoResultsTextVisibility(visibility);
    }

    private void clearAllFragments() {
        if (activeCenterFragments.size() > 0) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            for (Fragment activeFragment : activeCenterFragments) {
                transaction.remove(activeFragment);
            }
            activeCenterFragments.clear();
            transaction.commit();
        }
    }

    private void setUpNewFragments(ArrayList<Food> foods) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        for(Food food : foods) {
            FoodFragment foodFragment = FoodFragment.newInstance(food, FoodFragment.HAD_ADD_BTN_VAL);
            transaction.add(R.id.foodsContainer, foodFragment);
            activeCenterFragments.add(foodFragment);
        }

        transaction.commit();
        dismissLoadingDialog();
    }

    private void showAddFoodToast(String foodName) {
        Toast.makeText(this, foodName + " added!", Toast.LENGTH_SHORT).show();
    }


    /*
    * dialog
    * */

    private void initializeDialogs() {
        initializeLoadingDialog();
    }

    private void initializeLoadingDialog() {
        loadingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        loadingDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        loadingDialog.setTitleText("Loading");
        loadingDialog.setCancelable(false);
    }

    private void showLoadingDialog() {
        if (loadingDialog == null) {
            initializeLoadingDialog();
        }

        loadingDialog.show();
        startLoadingThread();
    }

    private void dismissLoadingDialog() {
        loadingDialog.dismiss();
        stopLoadingThread();
    }

    private void setLoadingDialogColor(int color) {
        loadingDialog.getProgressHelper().setBarColor(color);
    }

    private void setLoadingDialogColorInThread() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                int[] loadingColors = getResources().getIntArray(R.array.loading_colors);
                setLoadingDialogColor(loadingColors[countDownTimer]);
            }
        });
    }

    private void initializeNoInternetDialog() {
        String title = getResources().getString(R.string.no_internet_dialog_title);
        String description = getResources().getString(R.string.no_internet_dialog_description);

        noInternetDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        noInternetDialog
                .setTitleText(title)
                .setContentText(description)
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
    }

    private void showNoInternetDialog() {
        if(noInternetDialog == null) {
            initializeNoInternetDialog();
        }

        noInternetDialog.show();
    }


    /*
    * Loading threads
    * */

    private void initializeLoadingThread() {
        initializeLoadingHandler();
        initializeLoadingRunnable();
    }

    private void initializeLoadingHandler() {
        loadingThread = new HandlerThread(LOADING_THREAD);
        loadingThread.start();
        loadingHandler = new Handler(loadingThread.getLooper());
    }

    private void initializeLoadingRunnable() {
        loadingRunnable = new Runnable() {
            @Override
            public void run() {
                if(isLoading) {
                    countDownTimer = (countDownTimer + 1) % 7;
                    setLoadingDialogColorInThread();
                    loadingHandler.postDelayed(loadingRunnable, LOADING_INTERVAL);
                }
            }
        };
    }

    private void startLoadingThread() {
        isLoading = true;
        loadingHandler.postDelayed(loadingRunnable, LOADING_INTERVAL);
    }

    private void stopLoadingThread() {
        countDownTimer = 0;
        isLoading = false;
        loadingHandler.removeCallbacks(loadingRunnable);
    }

    private void destroyLoadingThread() {
        loadingThread.quit();
        loadingHandler = null;
        loadingRunnable = null;
    }
}
