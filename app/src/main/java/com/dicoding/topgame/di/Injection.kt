package com.dicoding.topgame.di

object Injection {
    fun provideRepository(): com.dicoding.topgame.data.Repository {
        return com.dicoding.topgame.data.Repository.getInstance()
    }
}