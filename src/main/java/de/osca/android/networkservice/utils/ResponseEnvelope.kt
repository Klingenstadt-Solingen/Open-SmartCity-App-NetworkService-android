package de.osca.android.networkservice.utils

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ResponseEnvelope<T>(
    @SerializedName("results", alternate = ["result"])
    val data: T? = null,
    var message: String? = null,
)
