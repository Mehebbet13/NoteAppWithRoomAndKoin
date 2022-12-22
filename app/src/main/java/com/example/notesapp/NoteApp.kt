package com.example.notesapp

import android.app.Application
import com.example.notesapp.data.datasource.di.noteDatabaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NoteApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(noteDatabaseModule)
            androidContext(this@NoteApp)
        }
    }
}
