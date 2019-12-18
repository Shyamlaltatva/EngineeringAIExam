package com.engineerai.android.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.engineerai.android.R;
import com.engineerai.android.api.RestAPIClient;
import com.engineerai.android.model.responsemodel.APIResponse;
import com.engineerai.android.ui.adapter.UserLisrAdapter;
import com.engineerai.android.util.EndLessRecyclerViewScrollListener;
import com.engineerai.android.util.Util;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvUser;
    private LinearLayoutManager linearLayoutManager=null;

    private int pageNo=0;
    private APIResponse apiResponse=null;

    private ArrayList<APIResponse.Data.Users> usersArrayList=new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private RelativeLayout relLoadingMore;
    private UserLisrAdapter userLisrAdapter;
    private EndLessRecyclerViewScrollListener rvScrollListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setBodyUI();
    }

    private void setBodyUI() {

        initView();

        callGetUserListAPI();

        swipeRefreshListener();

        setRecyclerViewScrollListener();


    }

    private void setRecyclerViewScrollListener() {

        rvScrollListener= new EndLessRecyclerViewScrollListener(getLayoutManger()) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {

                if(pageNo==0)
                {
                    removeLoadMore();
                }else
                {
                    if(Util.checkInternetConnection(getApplicationContext()))
                    {
                        callGetUserListAPI();

                    }else {
                        Toast.makeText(MainActivity.this, "No Internet connection available", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
    }

    private void swipeRefreshListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                pageNo=1;
                if(usersArrayList!=null && usersArrayList.size()>0)
                {
                    usersArrayList.clear();
                }
                callGetUserListAPI();
            }
        });
    }

    private void callGetUserListAPI() {

        if(pageNo>1)
        {
            setFooterVisiablity(View.VISIBLE);
        }else
        {
            Util.showProgress(this,false);
        }

        if(Util.checkInternetConnection(getApplicationContext()))
        {

            Call<APIResponse> apiCall= RestAPIClient.getRestApiMethods().getList(pageNo,10);
            apiCall.enqueue(new Callback<APIResponse>() {
                @Override
                public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {

                    Util.cancelProgress();

                    apiResponse= response.body();
                    removeLoadMore();
                    setUserAdapter();

                }

                @Override
                public void onFailure(Call<APIResponse> call, Throwable t) {
                    Util.cancelProgress();
                    removeLoadMore();

                }
            });

        }else {
            Toast.makeText(MainActivity.this, "No Internet connection available", Toast.LENGTH_SHORT).show();
        }
    }

    private void setUserAdapter() {

        if(apiResponse!=null && apiResponse.getData().getUsers()!=null)
        {
            pageNo++;
            usersArrayList.addAll(apiResponse.getData().getUsers());
            if(userLisrAdapter==null)
            {
                //First time list is empty to set the adapter
                rvUser.setLayoutManager(getLayoutManger());

                userLisrAdapter=new UserLisrAdapter(this,usersArrayList);
                rvUser.setAdapter(userLisrAdapter);
                rvUser.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
                rvUser.addOnScrollListener(rvScrollListener);

            }else
            {
                //Otherview add the list to adapter
                userLisrAdapter.setData(usersArrayList);
                userLisrAdapter.notifyDataSetChanged();
            }


        }else
        {
            pageNo=0;
        }

    }

    private void setFooterVisiablity(int visiablity) {
        if(relLoadingMore!=null)
        {
            relLoadingMore.setVisibility(visiablity);
        }
    }

    private void initView() {

        rvUser= findViewById(R.id.rvUser);
        swipeRefreshLayout= findViewById(R.id.swipeLayout);
        relLoadingMore= findViewById(R.id.relLoadingMore);

    }

    private void removeLoadMore()
    {
        if(relLoadingMore!=null)
            setFooterVisiablity(View.GONE);
    }


    private LinearLayoutManager getLayoutManger(){
        if(linearLayoutManager==null)
        {
            linearLayoutManager=new LinearLayoutManager(this);
        }
        return linearLayoutManager;
    }

}
