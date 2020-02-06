package com.blume.isomarks.ui.home;

import java.util.Calendar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class homeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public homeViewModel() {
        mText = new MutableLiveData<>();
        //mText.setValue("Hello Admin!");
        getGreeting();
    }

    public LiveData<String> getText() {
        return mText;
    }

    private void getGreeting(){
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay < 12){
            mText.setValue("Good Morning Admin");
        }
        else if(timeOfDay <16){
            mText.setValue("Good Afternoon Admin");
        }
        else {
            mText.setValue("Good Evening Admin");
        }
    }
}
