package com.judekwashie.contactbook.di

import com.judekwashie.contactbook.RecyclerItemClickListener
import com.judekwashie.contactbook.ui.contactlist.ContactListFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped

@InstallIn(ActivityComponent::class)
@Module
class FragmentModule {

    @ActivityScoped
    @Provides
    fun provideRecyclerItemClickListener(): RecyclerItemClickListener{
        return ContactListFragment()
    }

}