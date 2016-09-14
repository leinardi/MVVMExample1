package com.leinardi.mvvm.viewmodel;

import android.content.Context;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.leinardi.mvvm.R;
import com.leinardi.mvvm.adapter.SimpleSectionedRecyclerViewAdapter;
import com.leinardi.mvvm.manager.DataApiManager;
import com.leinardi.mvvm.model.Data;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by leinardi on 27/08/16.
 */

public class MainViewModel implements BaseViewModel {

    public final ObservableInt infoMessageVisibility;
    public final ObservableInt recyclerViewVisibility;
    public final ObservableField<String> infoMessage;
    private Context context;
    private DataListener dataListener;
    private DataApiManager dataApiManager;
    private Subscription subscription;

    public MainViewModel(Context context, DataListener dataListener, DataApiManager dataApiManager) {
        this.context = context;
        this.dataListener = dataListener;
        this.dataApiManager = dataApiManager;
        this.infoMessageVisibility = new ObservableInt(View.VISIBLE);
        this.recyclerViewVisibility = new ObservableInt(View.GONE);
        this.infoMessage = new ObservableField<>(context.getString(R.string.empty_list_message));
    }

    public void onCreate() {
        loadData();
    }

    @Override
    public void onDestroy() {
        cancel();
        context = null;
        dataListener = null;
    }

    private void loadData() {
        infoMessageVisibility.set(View.GONE);
        recyclerViewVisibility.set(View.GONE);

        cancel();
        subscription = dataApiManager.getNumberObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Data>>() {
                    @Override
                    public void onCompleted() {
                        Log.d(TAG, "Rx.onComplete");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Rx.onError", e);
                        dataListener.onDataListError(e.getMessage());
                    }

                    @Override
                    public void onNext(List<Data> dataList) {
                        Log.d(TAG, "Rx.onNext\n" + dataList.toString());
                        List<SimpleSectionedRecyclerViewAdapter.Section> sectionList = getSections(dataList);

                        recyclerViewVisibility.set(View.VISIBLE);
                        dataListener.onDataListChanged(dataList, sectionList);
                    }
                });


    }

    private void cancel() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @NonNull
    private List<SimpleSectionedRecyclerViewAdapter.Section> getSections(List<Data> dataList) {
        List<SimpleSectionedRecyclerViewAdapter.Section> sectionList = new ArrayList<>();
        short section = -1;
        for (int i = 0; i < dataList.size(); i++) {
            Data data = dataList.get(i);
            short dataSection = data.getSection();
            if (dataSection > section) {
                sectionList.add(new SimpleSectionedRecyclerViewAdapter.Section(i, context.getString(R.string.section_name, dataSection)));
                section = dataSection;
            }
        }
        return sectionList;
    }

    public interface DataListener {
        void onDataListChanged(List<Data> dataList, List<SimpleSectionedRecyclerViewAdapter.Section> sectionList);

        void onDataListError(String errorMessage);
    }
}
