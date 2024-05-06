package org.d3if3134.assesment2mobpro.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ban")
data class Ban(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val merk: String,
    val jenis: String,
    val ukuran: String
)
