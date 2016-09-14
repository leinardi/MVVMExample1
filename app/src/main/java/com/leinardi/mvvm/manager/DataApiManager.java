package com.leinardi.mvvm.manager;

import com.google.gson.Gson;
import com.leinardi.mvvm.BuildConfig;
import com.leinardi.mvvm.model.Data;
import com.leinardi.mvvm.model.Numbers;
import com.leinardi.mvvm.util.DataComparator;
import com.leinardi.mvvm.util.DataUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import rx.Observable;
import rx.functions.Func0;

public class DataApiManager {
    private static final String URL_QUERY = "/raw/MRwGkwHp";

    public Observable<List<Data>> getNumberObservable() {
        return Observable.defer(new Func0<Observable<List<Data>>>() {
            @Override
            public Observable<List<Data>> call() {
                try {
                    return Observable.just(getDataList());
                } catch (IOException e) {
                    return Observable.error(e);
                }
            }
        });
    }

    private List<Data> getDataList() throws IOException {
        URL url;
        HttpURLConnection urlConnection = null;
        List<Data> dataList = null;
        try {
            url = new URL(BuildConfig.API_URL + URL_QUERY);

            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

            Gson gson = new Gson();
            Numbers numbers = gson.fromJson(inputStreamReader, Numbers.class);

            dataList = new ArrayList<>();

            for (Short s : numbers.getNumbers()) {
                dataList.add(DataUtils.toData(s));
            }

            Collections.sort(dataList, new DataComparator());

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return dataList;
    }
}
