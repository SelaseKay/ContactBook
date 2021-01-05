package com.judekwashie.contactbook.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.judekwashie.contactbook.dao.ContactDao
import com.judekwashie.contactbook.data.entities.Contact
import com.judekwashie.contactbook.utils.Converters

@Database(entities = [Contact::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ContactDatabase: RoomDatabase() {

    abstract fun getDao(): ContactDao

    companion object{
        private var INSTANCE: ContactDatabase? = null

        private const val  NOTE_DATABASE_NAME = "contact_database"

        fun getDatabase(context: Context): ContactDatabase{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext, ContactDatabase::class.java, NOTE_DATABASE_NAME).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}