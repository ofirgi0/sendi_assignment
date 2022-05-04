package com.example.sendiassignment.UI.adapters;

import android.view.View;

import com.example.sendiassignment.data.model.ItemModel;
import com.example.sendiassignment.utils.CreatedTime;

public interface RecyclerLayoutClickListener {

    void redirectToDetailScreen(View imageView,
                                View titleView,
                                ItemModel itemModel);

    void changeFavoriteStatus(ItemModel item, boolean add);

    void deleteFavorite(ItemModel item);

}
