package com.example.sendiassignment.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "fav_repo_db")
public class ItemModel implements Parcelable {
    @SerializedName("id")
    @Expose
    @PrimaryKey
    @NonNull
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("html_url")
    @Expose
    private String html_url;
    @SerializedName("clone_url")
    @Expose
    private String clone_url;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("owner")
    @Expose
    private OwnerModel owner;
    @SerializedName("stargazers_count")
    @Expose
    private int star_count;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("forks")
    @Expose
    private int forks;

    private boolean isFavorite;



    public ItemModel(String id, String name, String html_url, String clone_url, String description, String language, OwnerModel owners, int star_count, String createdAt, int forks, boolean isFavorite ) {
        this.id = id;
        this.name = name;
        this.html_url = html_url;
        this.clone_url = clone_url;
        this.description = description;
        this.language = language;
        this.owner = owners;
        this.star_count = star_count;
        this.forks = forks;
        this.isFavorite = isFavorite;
        this.createdAt = createdAt;
    }

    public ItemModel() {
    }

    protected ItemModel(Parcel in) {
        id = in.readString();
        name = in.readString();
        owner = in.readParcelable(OwnerModel.class.getClassLoader());
        html_url = in.readString();
        description = in.readString();
        createdAt = in.readString();
        star_count = in.readInt();
        language = in.readString();
        forks = in.readInt();
        clone_url = in.readString();
        isFavorite = in.readByte() != 0;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int getStar_count() {
        return star_count;
    }


    public String getName() {
        return name;
    }

    public String getHtml_url() {
        return html_url;
    }


    public String getClone_url() {
        return clone_url;
    }


    public String getDescription() {
        return description;
    }

    public String getLanguage() {
        return language;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public void setClone_url(String clone_url) {
        this.clone_url = clone_url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setStar_count(int star_count) {
        this.star_count = star_count;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public int getForks() {
        return forks;
    }

    public void setForks(int forks) {
        this.forks = forks;
    }

    public OwnerModel getOwner() {
        return owner;
    }

    public void setOwner(OwnerModel owner) {
        this.owner = owner;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(this.id);
        parcel.writeString(this.name);
        parcel.writeParcelable(this.owner, flags);
        parcel.writeString(this.html_url);
        parcel.writeString(this.description);
        parcel.writeString(this.createdAt);
        parcel.writeInt(this.star_count);
        parcel.writeString(this.language);
        parcel.writeInt(this.forks);
        parcel.writeString(this.clone_url);
        parcel.writeByte((byte) (isFavorite? 1 : 0));
    }

    public static final Creator<ItemModel> CREATOR = new Creator<ItemModel>() {
        @Override
        public ItemModel createFromParcel(Parcel in) {
            return new ItemModel(in);
        }

        @Override
        public ItemModel[] newArray(int size) {
            return new ItemModel[size];
        }
    };

}

