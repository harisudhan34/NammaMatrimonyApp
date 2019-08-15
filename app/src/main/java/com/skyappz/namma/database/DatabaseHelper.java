package com.skyappz.namma.database;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends ViewModel {

    public MutableLiveData<ArrayList<States>> states = new MutableLiveData<ArrayList<States>>();

    public Activity activity;

    public DatabaseHelper() {

    }

    public LiveData<ArrayList<States>> getStates() {
        return states;
    }

    public void setStates(ArrayList<States> states) {
        this.states.postValue(states);
    }

    public void loadStates() {
        class GetTasks extends AsyncTask<Void, Void, List<States>> {

            @Override
            protected List<States> doInBackground(Void... voids) {
                List<States> taskList = DatabaseClient
                        .getInstance(activity)
                        .getAppDatabase()
                        .taskDao()
                        .getAll();
                return taskList;
            }

            @Override
            protected void onPostExecute(List<States> tasks) {
                super.onPostExecute(tasks);
                states.postValue((ArrayList<States>) tasks);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Activity getActivity() {
        return activity;
    }

}
