package com.example.sendiassignment.UI;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;
import androidx.transition.TransitionInflater;

import com.example.sendiassignment.R;
import com.example.sendiassignment.UI.adapters.DaysFilterListAdapter;
import com.example.sendiassignment.UI.adapters.RecyclerItemClickListener;
import com.example.sendiassignment.UI.adapters.RecyclerLayoutClickListener;
import com.example.sendiassignment.UI.adapters.RecyclerViewPaginator;
import com.example.sendiassignment.UI.adapters.RepositoryListAdapter;
import com.example.sendiassignment.data.DataManager;
import com.example.sendiassignment.data.local.FavRepoDatabase;
import com.example.sendiassignment.data.local.RepoRepository;
import com.example.sendiassignment.data.model.ItemModel;
import com.example.sendiassignment.databinding.FragmentMainRepoBinding;
import com.example.sendiassignment.utils.AnimUtils;
import com.example.sendiassignment.utils.Constants;
import com.example.sendiassignment.utils.CreatedTime;
import com.example.sendiassignment.utils.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Observer;

import io.reactivex.Observable;


public class MainRepoFragment extends Fragment implements RecyclerLayoutClickListener, android.widget.SearchView.OnQueryTextListener {
    private MainViewModel viewModel;
    private FragmentMainRepoBinding binding;

    public static MainRepoFragment getInstance() {
        return new MainRepoFragment();
    }

    private DaysFilterListAdapter daysFilterListAdapter;
    private RepositoryListAdapter repositoryListAdapter;
    private CreatedTime currentCreatedTime = CreatedTime.DAY;
    private boolean isFirstPage = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMainRepoBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        initViewModel(getContext());
        initView(getContext());
    }

    private void initView(Context context) {
        daysFilterListAdapter = new DaysFilterListAdapter(Arrays.asList(Util.getCreatedDateNames()));
        binding.daysFilterList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        binding.mainToolbar.repoSearchView.setOnQueryTextListener(MainRepoFragment.this);
        binding.daysFilterList.addOnItemTouchListener(new RecyclerItemClickListener(context, (parentView, childView, position) -> {
            binding.mainToolbar.repoSearchView.setQuery("", false);
            binding.mainToolbar.repoSearchView.clearFocus();
            daysFilterListAdapter.updateSelection(position);
            switch (position) {
                case 0: currentCreatedTime = CreatedTime.DAY;break;
                case 1: currentCreatedTime = CreatedTime.WEEK;break;
                case 2: currentCreatedTime = CreatedTime.MONTH;break;
            }
            isFirstPage = true;
            viewModel.loadNextPageRepos(currentCreatedTime, true);
        }));

        displayLoader();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        repositoryListAdapter = new RepositoryListAdapter(getContext(), this, false);
        binding.recyclerView.setAdapter(repositoryListAdapter);
        binding.recyclerView.addOnScrollListener(new RecyclerViewPaginator(binding.recyclerView) {
            @Override
            public boolean isLastPage() {
                return viewModel.isLastPage();
            }

            @Override
            public void loadMore() {
                isFirstPage = false;
                viewModel.loadNextPageRepos(currentCreatedTime, false);

            }
        });



        viewModel.getRepos().observe(this, itemModels -> {
            if (isFirstPage) {
                animateView(itemModels);
            } else {
                displayDataView(itemModels, false);
            }
            viewModel.getAllFavorites().observe(this, favItemModels -> {
                repositoryListAdapter.updateFavs(favItemModels);

            });
        });


    }

    private void initViewModel(Context context) {
        DataManager dataManager = DataManager.getInstance(context);
        RepoRepository repoRepository = new RepoRepository(getActivity().getApplication());
        MainViewModelFactory factory = new MainViewModelFactory(dataManager, repoRepository);
        viewModel = new ViewModelProvider(requireActivity(), factory).get(MainViewModel.class);
        viewModel.loadNextPageRepos(CreatedTime.DAY, true);
    }

    private void animateView(List<ItemModel> repositories) {
        hideLoader();
        AnimUtils.slideView(binding.filterLayout, binding.daysFilterList, daysFilterListAdapter);
        displayDataView(repositories, true);
        binding.recyclerView.scheduleLayoutAnimation();
    }

    private void displayDataView(List<ItemModel> repositories, boolean clear) {
//        binding.viewEmpty.emptyContainer.setVisibility(View.GONE);
        repositoryListAdapter.setItems(repositories, clear);
    }

    private void displayLoader() {
        binding.viewLoader.rootView.setVisibility(View.VISIBLE);
    }

    private void hideLoader() {
        binding.viewLoader.rootView.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        hideLoader();
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
        if (add) {
            viewModel.addFavorite(item);
        } else {
            viewModel.removeFavorite(item);
        }
    }

    @Override
    public void deleteFavorite(ItemModel item) {

    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        repositoryListAdapter.filterBySearch(query);
        return false;
    }
}

