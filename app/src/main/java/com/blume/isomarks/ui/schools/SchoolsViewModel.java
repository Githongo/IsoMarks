package com.blume.isomarks.ui.schools;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SchoolsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SchoolsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is schools fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}