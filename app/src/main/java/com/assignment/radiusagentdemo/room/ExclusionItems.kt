package com.assignment.radiusagentdemo.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exclusion_items")
data class ExclusionItems(
    @ColumnInfo(name = "facility_id")
    var facilityId: String,

    @ColumnInfo(name = "options_id")
    var optionsId: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
}