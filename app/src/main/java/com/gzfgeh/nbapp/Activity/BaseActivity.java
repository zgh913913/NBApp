package com.gzfgeh.nbapp.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.Menu;
import android.view.MenuItem;

import com.gzfgeh.nbapp.Common.Contants;
import com.gzfgeh.nbapp.Component.ActivityComponent;
import com.gzfgeh.nbapp.Component.ActivityComponentFactory;
import com.gzfgeh.nbapp.R;
import com.gzfgeh.nbapp.Utils.ShareUtils;
import com.gzfgeh.swipeback.SwipeBackActivity;
import com.zhy.autolayout.AutoLayoutActivity;

public class BaseActivity extends SwipeBackActivity {
    private ActivityComponent activityComponent;
    private boolean isNight = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setDayOrNightTheme();
        super.onCreate(savedInstanceState);
    }

    private void setDayOrNightTheme() {
        if (ShareUtils.getValue(Contants.NIGHT_THEME_MODE, false)){
            getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            isNight = true;
        }else{
            getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            isNight = false;
        }
    }

    public ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = ActivityComponentFactory.create(this);
        }
        return activityComponent;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshDayOrNightTheme();
    }

    private void refreshDayOrNightTheme() {
        int uiMode = getResources().getConfiguration().uiMode;
        int dayNightUiMode = uiMode & Configuration.UI_MODE_NIGHT_MASK;

        if (dayNightUiMode == Configuration.UI_MODE_NIGHT_NO) {
            if (isNight){
                recreate();
            }
        } else if (dayNightUiMode == Configuration.UI_MODE_NIGHT_YES) {
            if (!isNight){
                recreate();
            }
        } else {

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    finish();
                }
                break;

            case R.id.action_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }


}
