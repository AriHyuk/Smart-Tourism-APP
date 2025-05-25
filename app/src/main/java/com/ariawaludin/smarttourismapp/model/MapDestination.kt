package com.ariawaludin.smarttourismapp.model

import com.google.android.gms.maps.model.LatLng

data class MapDestination(
    val name: String,
    val location: String,
    val position: LatLng,
    val type: String
)
