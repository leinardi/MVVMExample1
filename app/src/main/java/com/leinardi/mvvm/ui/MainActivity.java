package com.leinardi.mvvm.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;

import com.leinardi.mvvm.R;
import com.leinardi.mvvm.adapter.DataListAdapter;
import com.leinardi.mvvm.adapter.SimpleSectionedRecyclerViewAdapter;
import com.leinardi.mvvm.databinding.MainActivityBinding;
import com.leinardi.mvvm.manager.DataApiManager;
import com.leinardi.mvvm.model.Data;
import com.leinardi.mvvm.ui.dialog.ErrorAlertDialogFragment;
import com.leinardi.mvvm.viewmodel.MainViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements MainViewModel.DataListener {
    private MainActivityBinding binding;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new MainViewModel(this, this, new DataApiManager());
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        binding.setViewModel(viewModel);

        setupViews();

        viewModel.onCreate();
    }

    private void setupViews() {
        binding.reposRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.reposRecyclerView.setHasFixedSize(true);
        SimpleSectionedRecyclerViewAdapter sectionedAdapter = new
                SimpleSectionedRecyclerViewAdapter(this, R.layout.section, R.id.section_text, new DataListAdapter());
        binding.reposRecyclerView.setAdapter(sectionedAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel.onDestroy();
    }

    @Override
    public void onDataListChanged(List<Data> dataList, List<SimpleSectionedRecyclerViewAdapter.Section> sectionList) {
        SimpleSectionedRecyclerViewAdapter sectionAdapter =
                (SimpleSectionedRecyclerViewAdapter) binding.reposRecyclerView.getAdapter();
        ((DataListAdapter) sectionAdapter.getBaseAdapter()).setDataList(dataList);
        sectionAdapter.setSections(sectionList);
    }

    @Override
    public void onDataListError(String errorMessage) {
        showErrorDialogFragment(errorMessage);
    }

    private void showErrorDialogFragment(String message) {
        DialogFragment newFragment = ErrorAlertDialogFragment.newInstance(
                getString(R.string.error_alert_dialog_title), message);
        newFragment.show(getSupportFragmentManager(), ErrorAlertDialogFragment.class.getSimpleName());
    }

}
