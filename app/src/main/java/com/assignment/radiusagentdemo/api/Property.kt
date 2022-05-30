package com.assignment.radiusagentdemo.api

import com.squareup.moshi.Json

data class ResponseData(
    @field:Json(name = "facilities") val facilities: List<FacilityProperty>? = null,
    @field:Json(name = "exclusions") val exclusions: List<List<ExclusionProperty>>? = null
)

data class FacilityProperty(
    @field:Json(name = "facility_id")val facility_id: String? = null,
    @field:Json(name = "name")val name: String? = null,
    @field:Json(name = "options")val options: List<OptionsProperty>? = null
)

data class OptionsProperty(
    @field:Json(name = "name") val name: String?= null,
    @field:Json(name = "icon") val icon: String?=null,
    @field:Json(name = "id") val id: String?=null

)
data class ExclusionProperty(
    @field:Json(name = "facility_id") val facility_id: String? = null,
    @field:Json(name = "options_id") val options_id: String? = null
)
