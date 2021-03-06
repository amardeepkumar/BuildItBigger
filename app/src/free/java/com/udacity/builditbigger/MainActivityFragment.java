package com.udacity.builditbigger;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.udacity.jokedisplaysupport.JokeDisplayActivity;


/**
 * Created by Amardeep Kumar on 8/27/2016.
 */
public class MainActivityFragment extends Fragment implements View.OnClickListener, JokeFetchListener {
    private InterstitialAd mInterstitialAd;
    private AdRequest mAdRequest;
    private ProgressBar mProgressBar;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        root.findViewById(R.id.btn_joke).setOnClickListener(this);

        mProgressBar = (ProgressBar) root.findViewById(R.id.progressBar);

        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id));

        requestNewInterstitial();

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        mAdView.loadAd(mAdRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                fetchJoke();
            }
        });
        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_joke:
                showAd();
                break;
        }
    }

    @Override
    public void onJokeFetched(String joke) {
        if (getActivity() != null) {
            mProgressBar.setVisibility(View.GONE);
            startActivity(JokeDisplayActivity.getJokeDisplayActivityIntent(getActivity(), joke));
        }
    }

    private void requestNewInterstitial() {
        mAdRequest = new AdRequest.Builder()
                .addTestDevice("3288260347D6377A7FDC59C188ACEA78")
                .build();
        mInterstitialAd.loadAd(mAdRequest);
    }

    private void showAd() {
        mInterstitialAd.show();
    }

    private void fetchJoke() {
        mProgressBar.setVisibility(View.VISIBLE);
        new EndpointsAsyncTask(this).execute();
    }
}
