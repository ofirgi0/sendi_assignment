package com.example.sendiassignment.data.local;

import androidx.room.TypeConverter;

import com.example.sendiassignment.data.model.OwnerModel;
import com.google.gson.Gson;

public class DBTypeConverters {
    @TypeConverter
    public String ownerModelToString(OwnerModel oModel){
        return new Gson().toJson(oModel);
    }
    @TypeConverter
    public OwnerModel stringToOwnerModel(String oModelString){
        return new Gson().fromJson(oModelString, OwnerModel.class);
    }
}
