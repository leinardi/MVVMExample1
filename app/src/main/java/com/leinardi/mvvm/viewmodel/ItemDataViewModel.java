package com.leinardi.mvvm.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.support.annotation.DrawableRes;
import android.support.v7.widget.AppCompatTextView;

import com.leinardi.mvvm.R;
import com.leinardi.mvvm.model.Data;

/**
 * Created by leinardi on 27/08/16.
 */

public class ItemDataViewModel extends BaseObservable implements BaseViewModel {
    private Context context;
    private Data data;

    public ItemDataViewModel(Context context, Data data) {
        this.context = context;
        this.data = data;
    }


    public String getItemName() {
        return context.getString(R.string.item_name, data.getItem());
    }


    public boolean isChecked() {
        return data.isChecked();
    }

    @BindingAdapter("isChecked")
    public static void setCheckedImage(AppCompatTextView textView, boolean isChecked) {
        @DrawableRes int check = isChecked ? R.drawable.ic_data_checked : R.drawable.ic_data_not_checked;
        textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, check, 0);
    }

    @Override
    public void onDestroy() {
        context = null;
    }

    public void setData(Data data) {
        this.data = data;
        notifyChange();
    }
}
