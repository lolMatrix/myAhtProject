package com.marix.myaht.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONArray;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<JSONArray> mText;
    private final String TAG = "MyAht";

    public HomeViewModel() {
        mText = new MutableLiveData<>();
    }

    public void setArray(JSONArray a){
        mText.setValue(a);
    }

    public LiveData<JSONArray> getText() {
        return mText;
    }
}