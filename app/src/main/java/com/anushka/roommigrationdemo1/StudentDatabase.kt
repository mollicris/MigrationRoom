package com.anushka.roommigrationdemo1

import android.content.Context
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.DeleteColumn
import androidx.room.RenameColumn
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [Student::class],
    version = 7,
    autoMigrations = [

        AutoMigration(from = 6,to = 7, spec = StudentDatabase.Migration6to7::class)]
)
abstract class StudentDatabase : RoomDatabase() {


    abstract val subscriberDAO : StudentDAO

    @RenameColumn(tableName = "student_info", fromColumnName = "student_course", toColumnName = "subject_name")
    class Migration5to6 : AutoMigrationSpec

    @DeleteColumn(tableName = "student_info", columnName = "student_grade")
    class Migration6to7 : AutoMigrationSpec

    companion object{

       /* val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE student_info ADD COLUMN student_phone TEXT NOT NULL DEFAULT ''")
            }
        }*/

        @Volatile
      private var INSTANCE : StudentDatabase? = null
        fun getInstance(context: Context):StudentDatabase {

            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        StudentDatabase::class.java,
                        "student_data_database"
                    ).build()
                    INSTANCE = instance

              /*      instance = Room.databaseBuilder(
                        context.applicationContext,
                        StudentDatabase::class.java,
                        "student_data_database"
                    ).addMigrations(MIGRATION_4_5).build()
                    INSTANCE = instance*/

                }
                return instance
            }
        }
    }
}








