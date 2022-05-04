package com.example.sendiassignment.UI.adapters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;


import com.example.sendiassignment.databinding.DaysFilterItemBinding;

import java.util.List;

public class DaysFilterListAdapter extends RecyclerView.Adapter<DaysFilterListAdapter.CustomViewHolder> {

    private final List<String> items;
    private int lastSelectedPosition = 0;
    public DaysFilterListAdapter(List<String> items) {
        this.items = items;
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        DaysFilterItemBinding itemBinding = DaysFilterItemBinding.inflate(layoutInflater, parent, false);
        return new CustomViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        String item = getItem(position);
        holder.binding.btnRadio.setText(item);
        holder.binding.btnRadio.setChecked(lastSelectedPosition == position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public String getItem(int position) {
        return items.get(position);
    }

    public void updateSelection(int currentPosition) {
        int previousPosition = lastSelectedPosition;
        this.lastSelectedPosition = currentPosition;
        notifyItemChanged(previousPosition);
        notifyItemChanged(lastSelectedPosition);
    }

    protected static class CustomViewHolder extends RecyclerView.ViewHolder {
        private final DaysFilterItemBinding binding;

        public CustomViewHolder(DaysFilterItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.btnRadio.setEnabled(false);
        }
    }
}
