package com.example.chat.ui.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class userViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public userViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is user fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}