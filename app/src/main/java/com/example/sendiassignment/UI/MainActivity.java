package com.example.sendiassignment.UI;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.sendiassignment.R;
import com.example.sendiassignment.data.DataManager;
import com.example.sendiassignment.data.model.ItemModel;
import com.example.sendiassignment.data.model.RepoModel;
import com.example.sendiassignment.databinding.ActivityMainBinding;
import com.example.sendiassignment.utils.Constants;
import com.example.sendiassignment.utils.CreatedTime;
import com.example.sendiassignment.utils.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static Map<String, String> map = new HashMap<>();
    private MutableLiveData<List<ItemModel>> repos = new MutableLiveData<>();
    private ActivityMainBinding binding;
    private boolean isFavCurrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        if (!Util.isNetworkAvailable(this)){
            binding.ivNoInternet.setVisibility(View.VISIBLE);
            binding.goToFavorites.setVisibility(View.GONE);
        }
        else{
            binding.goToFavorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isFavCurrent){
                        AnimatorSet anim = (AnimatorSet) AnimatorInflater.loadAnimator(MainActivity.this, R.animator.flipping);
                        anim.setTarget(binding.goToFavorites);
                        anim.setDuration(1500);
                        anim.start();
                        anim.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                binding.goToFavorites.setImageResource(R.drawable.ic_heart);
                            }
                        });


                        isFavCurrent = false;
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_fragment, MainRepoFragment.getInstance())
                                .commit();
                    }
                    else{
                        AnimatorSet anim = (AnimatorSet) AnimatorInflater.loadAnimator(MainActivity.this, R.animator.flipping);
                        anim.setTarget(binding.goToFavorites);
                        anim.setDuration(1000);
                        anim.start();
                        anim.addListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                binding.goToFavorites.setImageResource(R.drawable.ic_back);
                            }
                        });
                        isFavCurrent = true;
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.content_fragment, FavoritesFragment.getInstance())
                                .commit();
                    }

                }
            });
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_fragment, MainRepoFragment.getInstance())
                    .commit();
        }



    }
}