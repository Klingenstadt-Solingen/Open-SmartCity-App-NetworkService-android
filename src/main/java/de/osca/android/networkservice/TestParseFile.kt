package de.osca.android.networkservice

import com.google.gson.annotations.SerializedName

data class TestParseFile(
    @SerializedName("__type")
    val type: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)