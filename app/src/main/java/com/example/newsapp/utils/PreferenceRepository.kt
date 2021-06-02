package com.example.newsapp.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val Context.dataStore by preferencesDataStore(name = Constants.APP_PREFERENCE_DATASTORE)

class PreferenceRepository @Inject constructor(
    @ApplicationContext context: Context
) {

    private var _datastore = context.dataStore

//    private val dataStore = context.createDataStore(
//        name = "app_preference"
//    )

    private val nightModeKey = booleanPreferencesKey(name = Constants.NIGHT_MODE_KEY)
    private val onBoardingKey = booleanPreferencesKey(name = Constants.ON_BOARDED_KEY)

    suspend fun setNightMode(nightMode: Boolean) = _datastore.edit {
        it[nightModeKey] = nightMode
    }

    val isNightMode: Flow<Boolean?>
        get() = _datastore.data.map {
            it[nightModeKey]
        }

    suspend fun setOnBoard(isOnboarded: Boolean) = _datastore.edit {
        it[onBoardingKey] = isOnboarded
    }

    val isOnboarded
        get() = _datastore.data.map {
            it[onBoardingKey] ?: false
        }
    // we haven't used setter... becoz we need setter to be suspend fun


//    fun getNightMode() = _datastore.data.map {
//        it[nightModeKey]
//    }
}