package com.skyappz.namma.webservice;

/**
 * Created by Surendar.V on 10-05-2017.
 */

public interface WebServiceListener {

    public void onSuccess(int requestCode, int responseCode, Object response);

    public void onFailure(int requestCode, int responseCode, Object response);

    public void isNetworkAvailable(boolean flag);

    public void onProgressStart();

    public void onProgressEnd();

}
