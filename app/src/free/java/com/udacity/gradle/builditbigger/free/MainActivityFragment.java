package com.udacity.gradle.builditbigger.free;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.udacity.gradle.builditbigger.ClickListener;
import com.udacity.gradle.builditbigger.EndpointsAsyncTask;
import com.udacity.gradle.builditbigger.MainActivity;
import com.udacity.gradle.builditbigger.R;
import com.udacity.gradle.builditbigger.SimpleIdlingResource;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vsp.shop.com.jokeandroidlibrary.JokeActivity;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements ClickListener{

    private static final int DELAY_MILLIS = 3000;


    public MainActivityFragment() {
    }

    @BindView(R.id.progressBar)
    ProgressBar progressBar;


    @Nullable
    private SimpleIdlingResource mIdlingResource;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, root);
        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        MobileAds.initialize(getActivity(), "ca-app-pub-9223965500529551~1957083696");
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        // Get the IdlingResource instance
        mIdlingResource = ((MainActivity)getActivity()).getIdlingResource();

        return root;
    }

    @OnClick(R.id.tell_joke_btn)
    public void tellJoke() {
        System.out.println("here");
        progressBar.setVisibility(View.VISIBLE);
        if (mIdlingResource != null) {
            mIdlingResource.setIdleState(false);
        }
        new EndpointsAsyncTask().execute(new Pair<Context, ClickListener>(getActivity(), this));
    }


    @Override
    public void tellJokeBtnListner(String joke) {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                    if (mIdlingResource != null) {
                        mIdlingResource.setIdleState(true);
                    }
            }
        }, DELAY_MILLIS);
        progressBar.setVisibility(View.GONE);
        Intent intent = new Intent(getActivity(), JokeActivity.class);
        intent.putExtra("JOKE", joke);
        startActivity(intent);

    }

}
