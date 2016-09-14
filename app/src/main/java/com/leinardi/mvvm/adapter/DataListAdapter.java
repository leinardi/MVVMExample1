package com.leinardi.mvvm.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.leinardi.mvvm.R;
import com.leinardi.mvvm.databinding.ItemDataBinding;
import com.leinardi.mvvm.model.Data;
import com.leinardi.mvvm.viewmodel.ItemDataViewModel;

import java.util.Collections;
import java.util.List;

/**
 * Created by leinardi on 27/08/16.
 */

public class DataListAdapter extends RecyclerView.Adapter<DataListAdapter.DataViewHolder> {
    private List<Data> dataList;

    public DataListAdapter() {
        dataList = Collections.emptyList();
    }

    public void setDataList(List<Data> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public DataViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.item_data,
                parent,
                false);
        return new DataViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(DataViewHolder holder, int position) {
        holder.bindData(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class DataViewHolder extends RecyclerView.ViewHolder {
        final ItemDataBinding binding;

        DataViewHolder(ItemDataBinding binding) {
            super(binding.root);
            this.binding = binding;
        }

        void bindData(Data data) {
            if (binding.getViewModel() == null) {
                binding.setViewModel(new ItemDataViewModel(itemView.getContext(), data));
            } else {
                binding.getViewModel().setData(data);
            }
        }
    }
}
