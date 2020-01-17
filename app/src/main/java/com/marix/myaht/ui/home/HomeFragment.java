package com.marix.myaht.ui.home;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.marix.myaht.R;
import com.marix.myaht.api.ConnectionServer;
import com.marix.myaht.ui.dashboard.DashboardViewModel;

import org.json.JSONArray;
import org.json.JSONException;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ViewPager lastNewsSlider;
    private ViewPager cultureNewsSlider;
    private NewsSliderPageAdapter culture;
    private NewsSliderPageAdapter adapter;
    final private String TAG = "MyAht";



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, @NonNull Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        TextView textView = root.findViewById(R.id.text_home);

        lastNewsSlider = (ViewPager) root.findViewById(R.id.lastNews);
        adapter = new NewsSliderPageAdapter(getContext(), getActivity().getSupportFragmentManager().beginTransaction());

        cultureNewsSlider = (ViewPager) root.findViewById(R.id.cultureNews);
        culture = new NewsSliderPageAdapter(getContext(), getActivity().getSupportFragmentManager().beginTransaction());

        lastNewsSlider.setAdapter(adapter);
        cultureNewsSlider.setAdapter(culture);

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);



        homeViewModel.getText().observe(this, new Observer<JSONArray>() {
            @Override
            public void onChanged(@Nullable JSONArray s) {
                Log.d(TAG, "onChanged: " + s.toString());
                adapter.updateArray(s);
                Log.d(TAG, "onCreateView: update array");
            }
        });


        if(savedInstanceState == null){
            LastNews query = new LastNews();
            query.execute();
        }

    }

    private class LastNews extends AsyncTask<Void, Void, Void> {

        private JSONArray array;
        private JSONArray culArr;

        @Override
        protected Void doInBackground(Void... voids) {
            Log.d(TAG, "doInBackground: lol");
            ConnectionServer query = new ConnectionServer() {
                @Override
                public void callBack(JSONArray result) {
                    array = result;
                }
            };
            query.sendRequest("wp-json/wp/v2/posts?per_page=3");
            ConnectionServer queryCulture = new ConnectionServer() {
                @Override
                public void callBack(JSONArray result) {
                    culArr = result;
                }
            };
            queryCulture.sendRequest("wp-json/wp/v2/posts?per_page=10&categories=9");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.updateArray(array);
            culture.updateArray(culArr);
            homeViewModel.setArray(array);
        }
    }

}