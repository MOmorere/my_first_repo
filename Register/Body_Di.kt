package com.example.moivesbox.Ui.Register

import com.example.moivesbox.Modlse.Register.Body_Users
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object Body_Di {
    @Provides
    @Singleton
    fun provide_Body()=Body_Users()
}