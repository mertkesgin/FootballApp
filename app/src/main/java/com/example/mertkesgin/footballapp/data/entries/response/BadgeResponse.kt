package com.example.mertkesgin.footballapp.data.entries.response

import com.example.mertkesgin.footballapp.data.entries.Badge
import com.google.gson.annotations.SerializedName

class BadgeResponse (
    @SerializedName("teams")
    val badgeList:List<Badge>
) {
}