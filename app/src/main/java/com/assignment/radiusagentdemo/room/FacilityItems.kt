package com.assignment.radiusagentdemo.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "facility_items")
data class FacilityItems(

    @ColumnInfo(name = "facility_id")
    var facilityId: String,

    @ColumnInfo(name = "facility_name")
    var facilityName: String,

    @ColumnInfo(name = "options_name")
    var optionsName: String,

    @ColumnInfo(name = "options_icon")
    var optionsIcon: String,

    @ColumnInfo(name = "options_id")
    var optionsId: String

) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}


