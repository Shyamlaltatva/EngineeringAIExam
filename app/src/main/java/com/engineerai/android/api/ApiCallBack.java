package com.engineerai.android.api;


import retrofit2.Callback;

public abstract class ApiCallBack<T> implements Callback<T> {
    public abstract void onRefresh();
}