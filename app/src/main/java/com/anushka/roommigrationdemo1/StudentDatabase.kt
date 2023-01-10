package com.anushka.roommigrationdemo1

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [Student::class],
    version = 5

)
abstract class StudentDatabase : RoomDatabase() {


    abstract val subscriberDAO : StudentDAO

    companion object{

        val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE student_info ADD COLUMN student_phone TEXT NOT NULL DEFAULT ''")
            }
        }

        @Volatile
      private var INSTANCE : StudentDatabase? = null
        fun getInstance(context: Context):StudentDatabase {

            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                   /* instance = Room.databaseBuilder(
                        context.applicationContext,
                        StudentDatabase::class.java,
                        "student_data_database"
                    ).build()
                    INSTANCE = instance*/

                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        StudentDatabase::class.java,
                        "student_data_database"
                    ).addMigrations(MIGRATION_4_5).build()
                    INSTANCE = instance

                }
                return instance
            }
        }
    }
}








