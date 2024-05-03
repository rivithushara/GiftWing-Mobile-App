package com.myapp.giftwing.injector

import android.content.Context
import androidx.room.Room
import com.myapp.giftwing.data.local.RoomDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /** A function to provide a single Context's instance of the application throughout app */
    @Provides
    @Singleton
    fun provideContextInstance(@ApplicationContext cxt: Context) = cxt

    /** Provide Coroutine scope */
    @Singleton
    @Provides
    fun provideCoroutineScope() = CoroutineScope(SupervisorJob())

    /** A function to provide a single instance of the local database throughout app */
    @Provides
    @Singleton
    fun provideRoomInstance(
        @ApplicationContext context: Context,
        populateDataCallback: RoomDb.PopulateDataClass,
    ) = Room
        .databaseBuilder(
            context.applicationContext,
            RoomDb::class.java,
            "GiftWingDatabase",
        )
        .fallbackToDestructiveMigration()
        .addCallback(populateDataCallback)
        .build()

    /** A function to provide a single instance of the local database Dao throughout app */
    @Provides
    @Singleton
    fun provideDaoInstance(db: RoomDb) = db.getDao()

}