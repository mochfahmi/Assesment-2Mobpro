package org.d3if3134.assesment2mobpro.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if3134.assesment2mobpro.model.Ban


@Database(entities = [Ban::class], version = 1, exportSchema = false)
abstract class BanDb : RoomDatabase() {

    abstract val dao: BanDao

    companion object {

        @Volatile
        private var INSTANCE: BanDb? = null

        fun getInstance(context: Context): BanDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        BanDb::class.java,
                        "ban.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}