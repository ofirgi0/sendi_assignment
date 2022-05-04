package com.example.sendiassignment.UI.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sendiassignment.R;
import com.example.sendiassignment.data.DataManager;
import com.example.sendiassignment.data.model.ItemModel;
import com.example.sendiassignment.databinding.RepoListItemBinding;
import com.example.sendiassignment.utils.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RepositoryListAdapter extends RecyclerView.Adapter<RepositoryListAdapter.CustomViewHolder>{

    private final Context context;
    private final List<ItemModel> items;
    private ArrayList<ItemModel> allItems;
    private final RecyclerLayoutClickListener listener;
    private final List<ItemModel> favorites = new ArrayList<>();
    private final boolean hideFavBtn;


    public RepositoryListAdapter(Context context, RecyclerLayoutClickListener listener, boolean hideFavBtn) {
        this.context = context;
        this.listener = listener;
        this.items = new ArrayList<>();
        this.hideFavBtn = hideFavBtn;

    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        RepoListItemBinding itemBinding = RepoListItemBinding.inflate(layoutInflater, parent, false);
        CustomViewHolder customItemViewHolder = new CustomViewHolder(itemBinding);
        itemBinding.cardView.setOnClickListener(v -> customItemViewHolder.onLayoutButtonClick());

        itemBinding.btnFavorite.setOnClickListener(view -> {
            int currentPosition = customItemViewHolder.getAdapterPosition();
            items.get(currentPosition).setFavorite(!items.get(currentPosition).isFavorite());
            listener.changeFavoriteStatus(items.get(currentPosition), items.get(currentPosition).isFavorite());
            /*if (hideFavBtn){
                itemBinding.btnFavorite.setVisibility(View.GONE);
                itemBinding.btnDeleteFav.setVisibility(View.VISIBLE);
            }
            else{
                int heartColorResource = items.get(currentPosition).isFavorite()? R.color.heart_red : R.color.main_black;
                itemBinding.btnFavorite.setColorFilter(ContextCompat.getColor(context, heartColorResource), android.graphics.PorterDuff.Mode.SRC_IN);
            }*/
        });

        return customItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.bindTo(position);
    }

    public void setItems(List<ItemModel> items, boolean clear) {
        if (clear) this.items.clear();
        this.items.addAll(items);
        allItems = new ArrayList<>();
        allItems.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public ItemModel getItem(int position) {
        return items.get(position);
    }

    public void updateFavs(List<ItemModel> favs){
        favorites.clear();
        for (int x = 0; x < favs.size(); x++){
            String cid = favs.get(x).getId();
            for (int i = 0; i < items.size(); i++){
                if (items.get(i).getId().equals(cid)){
                    items.get(i).setFavorite(true);
                }
            }
        }

        favorites.addAll(favs);
        notifyDataSetChanged();
    }
    public void filterBySearch(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        items.clear();
        if (charText.length() == 0) {
            items.addAll(allItems);
        } else {
            for (ItemModel im : allItems) {
                if (im.getName().toLowerCase(Locale.getDefault()).contains(charText)) {
                    items.add(im);
                }
            }
        }
        notifyDataSetChanged();
    }



    protected class CustomViewHolder extends RecyclerView.ViewHolder {

        private final RepoListItemBinding binding;

        public CustomViewHolder(RepoListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }



        public void bindTo(int position) {
            Picasso.get().load(items.get(position).getOwner().getAvatar_url())
                    .placeholder(R.drawable.ic_placeholder)
                    .into(binding.cvProfileImg);

            binding.tvTitle.setText(items.get(position).getName());
            binding.tvTime.setText(String.format(context.getString(R.string.item_date),
                    Util.getDate(items.get(position).getCreatedAt()),
                    Util.getTime(items.get(position).getCreatedAt())));

            binding.tvDesc.setText(items.get(position).getDescription());

            binding.tvStarCount.setText(String.valueOf(items.get(position).getStar_count()));
            binding.cvProfileImg.setTransitionName(binding.cvProfileImg.getTransitionName() + items.get(position).getId());

            if (hideFavBtn){
                binding.btnFavorite.setVisibility(View.GONE);
                binding.btnDeleteFav.setVisibility(View.VISIBLE);
                binding.btnDeleteFav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.deleteFavorite(items.get(position));
                        items.remove(position);
                        notifyDataSetChanged();
                    }
                });
            }
            else{
                boolean isFavorited = items.get(position).isFavorite();
                items.get(position).setFavorite(isFavorited);
                int heartColorResource = isFavorited ? R.color.heart_red : R.color.main_black;
                binding.btnFavorite.setColorFilter(ContextCompat.getColor(context, heartColorResource), android.graphics.PorterDuff.Mode.SRC_IN);
            }


        }



        private void onLayoutButtonClick() {
            listener.redirectToDetailScreen(binding.cvProfileImg, binding.tvTitle, getItem(getLayoutPosition()));
            Toast.makeText(context, "onLayoutButtonClick", Toast.LENGTH_LONG).show();

        }

    }
}
