
package com.ariawaludin.smarttourismapp.model

import org.osmdroid.util.GeoPoint

data class MapDestination(
    val name: String,
    val location: String,
    val position: GeoPoint,
    val type: String
)
