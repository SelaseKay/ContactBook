package com.judekwashie.contactbook.di

import android.content.Context
import androidx.room.Room
import com.judekwashie.contactbook.dao.ContactDao
import com.judekwashie.contactbook.database.ContactDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
class DatabaseModule {


    @Provides
    fun provideContactDao(contactDatabase: ContactDatabase): ContactDao {
        return contactDatabase.getDao()
    }

    @Provides
    @Singleton
    fun provideContactDatabase(@ApplicationContext appContext: Context): ContactDatabase {
        return Room.databaseBuilder(appContext, ContactDatabase::class.java,
            DATABASE_NAME
        )
            .build()
    }

    companion object {
        private const val DATABASE_NAME = "contact_database"
    }

}