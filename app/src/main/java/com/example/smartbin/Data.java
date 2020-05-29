package com.example.smartbin;

import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("ID")
    private  String ID;


    @SerializedName("PERCENT_WET")
    private  String PERCENT_WET;


    @SerializedName("PERCENT_METALLIC")
    private  String PERCENT_METALLIC;

    public String getPERCENT_WET() {
        return PERCENT_WET;
    }

    public String getPERCENT_METALLIC() {
        return PERCENT_METALLIC;
    }

    public String getPERCENT_DRY() {
        return PERCENT_DRY;
    }

    @SerializedName("LATITUDE")
    private String latitude;

    @SerializedName("LONGITUDE")
    private String longitude;

    @SerializedName("PERCENT_DRY")
    private String PERCENT_DRY;

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }


    public String getID() {
        return ID;
    }
}
