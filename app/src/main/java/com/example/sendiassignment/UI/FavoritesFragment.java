package com.example.sendiassignment.UI;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.sendiassignment.R;
import com.example.sendiassignment.UI.adapters.RecyclerLayoutClickListener;
import com.example.sendiassignment.UI.adapters.RepositoryListAdapter;
import com.example.sendiassignment.data.DataManager;
import com.example.sendiassignment.data.local.RepoRepository;
import com.example.sendiassignment.data.model.ItemModel;
import com.example.sendiassignment.databinding.FragmentFavoritesBinding;
import com.example.sendiassignment.utils.Constants;
import com.example.sendiassignment.utils.CreatedTime;

import java.util.ArrayList;
import java.util.List;


public class FavoritesFragment extends Fragment implements RecyclerLayoutClickListener, SearchView.OnQueryTextListener {

    private FragmentFavoritesBinding binding;
    private MainViewModel viewModel;
    private RepositoryListAdapter repositoryListAdapter;

    public static FavoritesFragment getInstance() {
        return new FavoritesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        startPostponedEnterTransition();
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        initViewModel(getContext());
        initView();
    }

    private void initView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        repositoryListAdapter = new RepositoryListAdapter(getContext(), this, true);
        binding.recyclerView.setAdapter(repositoryListAdapter);
        viewModel.getAllFavorites().observe(this, favItems ->{
            repositoryListAdapter.setItems(favItems, true);
        });
    }

    private void initViewModel(Context context) {
        DataManager dataManager = DataManager.getInstance(context);
        RepoRepository repoRepository = new RepoRepository(getActivity().getApplication());
        MainViewModelFactory factory = new MainViewModelFactory(dataManager, repoRepository);
        viewModel = new ViewModelProvider(requireActivity(), factory).get(MainViewModel.class);
        viewModel.loadNextPageRepos(CreatedTime.DAY, true);
        binding.mainToolbar.repoSearchView.setOnQueryTextListener(FavoritesFragment.this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void redirectToDetailScreen(View imageView, View titleView, ItemModel itemModel) {
        List<Pair<View, String>> pairs = new ArrayList<>();
        pairs.add(new Pair(imageView, getString(R.string.transition_image)));
        pairs.add(new Pair(titleView, getString(R.string.transition_title)));
        Pair[] pairArr = new Pair[pairs.size()];
        pairArr = pairs.toArray(pairArr);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), pairArr);
        Intent intent = new Intent(getContext(), RepoDetailActivity.class);
        intent.putExtra(Constants.ITEM_KEY, itemModel);
        ActivityCompat.startActivity(requireContext(), intent, options.toBundle());
    }

    @Override
    public void changeFavoriteStatus(ItemModel item, boolean add) {

    }

    @Override
    public void deleteFavorite(ItemModel item) {
        viewModel.removeFavorite(item);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        repositoryListAdapter.filterBySearch(query);
        return false;
    }
}