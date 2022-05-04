package com.example.sendiassignment.UI;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sendiassignment.R;
import com.example.sendiassignment.data.model.ItemModel;
import com.example.sendiassignment.databinding.ActivityRepoDetailBinding;
import com.example.sendiassignment.utils.Constants;
import com.example.sendiassignment.utils.Util;
import com.squareup.picasso.Picasso;


public class RepoDetailActivity extends AppCompatActivity {

    private ActivityRepoDetailBinding binding;
    private ItemModel item;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRepoDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initView();
    }

    private void initView() {
        item = getIntent().getParcelableExtra(Constants.ITEM_KEY);

        Picasso.get().load(item.getOwner().getAvatar_url())
                .placeholder(R.drawable.ic_placeholder)
                .into(binding.itemProfileImg);

        binding.itemTitle.setText(item.getName());
        binding.itemLanguage.setText(item.getLanguage());
        binding.tvTime.setText(item.getCreatedAt());
        binding.itemForks.setText(String.format(getString(R.string.forks_title),
                String.valueOf(item.getForks())));

        binding.btnVisit.setOnClickListener(view -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.getHtml_url()));
            startActivity(browserIntent);
        });


    }
}
