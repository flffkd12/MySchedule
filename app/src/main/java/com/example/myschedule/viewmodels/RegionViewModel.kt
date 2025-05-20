package com.example.myschedule.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myschedule.data.Location
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException

// Level 3: 동/읍/면 이름
typealias ThirdLevelRegion = Map<String, Location>

// Level 2: 구/군 이름
typealias SecondLevelRegion = Map<String, ThirdLevelRegion>

// Level 1: 시/도 이름
typealias FirstLevelRegion = Map<String, SecondLevelRegion>

typealias Region = Map<String, FirstLevelRegion>

class RegionViewModel(application: Application) : AndroidViewModel(application) {

    private val _regionData = MutableStateFlow<Region?>(null)
    private val regionData: StateFlow<Region?> = _regionData.asStateFlow()

    init {
        loadRegionData()
    }

    private fun loadRegionData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val jsonString = getApplication<Application>().assets
                    .open("region.json")
                    .bufferedReader()
                    .use { it.readText() }

                val type = object : TypeToken<Region>() {}.type
                _regionData.value = Gson().fromJson(jsonString, type)
            } catch (e: IllegalArgumentException) {
                Log.e("RegionViewModel", "$e")
                Log.d(
                    "RegionViewModel",
                    "Verify if the structure of region.json file matches with TypeToken."
                )
            } catch (e: JsonSyntaxException) {
                Log.e("RegionViewModel", "$e")
                Log.d("RegionViewModel", "Verify if region.json file has right json syntax.")
            } catch (e: IOException) {
                Log.e("RegionViewModel", "$e")
                Log.d("RegionViewModel", "Verify if region.json file is accessible.")
            }
        }
    }

    fun getFirstLevelRegions(): List<String> {
        return regionData.value?.get("대한민국")?.keys?.toList() ?: emptyList()
    }

    fun getSecondLevelRegions(firstLevelRegion: String): List<String> {
        return regionData.value?.get("대한민국")?.get(firstLevelRegion)?.keys?.toList() ?: emptyList()
    }

    fun getThirdLevelRegions(firstLevelRegion: String, secondLevelRegion: String): List<String> {
        return regionData.value?.get("대한민국")?.get(firstLevelRegion)
            ?.get(secondLevelRegion)?.keys?.toList() ?: emptyList()
    }

    fun getRegionLocation(
        firstLevelRegion: String,
        secondLevelRegion: String,
        thirdLevelRegion: String
    ): Location? {
        return regionData.value?.get("대한민국")?.get(firstLevelRegion)?.get(secondLevelRegion)
            ?.get(thirdLevelRegion)
    }
}
