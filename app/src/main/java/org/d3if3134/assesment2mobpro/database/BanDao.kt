package org.d3if3134.assesment2mobpro.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.d3if3134.assesment2mobpro.model.Ban


@Dao
interface BanDao {

    @Insert
    suspend fun insert(ban: Ban)

    @Update
    suspend fun update(ban: Ban)

    @Query("SELECT * FROM ban ORDER BY ukuran ASC")
    fun getBan(): Flow<List<Ban>>

    @Query("SELECT * FROM ban WHERE id = :id")
    suspend fun getBanById(id: Long): Ban?

    @Query("DELETE FROM ban WHERE id = :id")
    suspend fun deleteById(id: Long)
}