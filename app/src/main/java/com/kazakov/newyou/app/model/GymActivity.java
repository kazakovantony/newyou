package com.kazakov.newyou.app.model;

import com.google.gson.annotations.SerializedName;

public enum GymActivity {
    @SerializedName("abs") ABS,
    @SerializedName("situps")SIT_UPS,
    @SerializedName("pullups")PULL_UPS,
    @SerializedName("pushups")PUSH_UPS,
    @SerializedName("pause")PAUSE,
    @SerializedName("other")OTHER
}
