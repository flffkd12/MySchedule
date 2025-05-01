package com.example.myschedule.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.example.myschedule.data.Location
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import java.io.IOException

class RegionViewModel(application: Application) : AndroidViewModel(application) {

    private var regionData: Map<String, Map<String, Map<String, Location>>>? = null

    init {
        loadRegionData()
    }

    private fun loadRegionData() {
        try {
            val jsonString = getApplication<Application>().assets
                .open("region.json")
                .bufferedReader()
                .use { it.readText() }

            val type =
                object : TypeToken<Map<String, Map<String, Map<String, Location>>>>() {}.type
            regionData = Gson().fromJson(jsonString, type)
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

    fun getFirstLevelRegions(): List<String> {
        return regionData?.keys?.toList() ?: emptyList()
    }

    fun getSecondLevelRegions(firstLevelRegion: String): List<String> {
        return regionData?.get(firstLevelRegion)?.keys?.toList() ?: emptyList()
    }

    fun getThirdLevelRegions(firstLevelRegion: String, secondLevelRegion: String): List<String> {
        return regionData?.get(firstLevelRegion)?.get(secondLevelRegion)?.keys?.toList()
            ?: emptyList()
    }

    fun getRegionLocation(
        firstLevelRegion: String,
        secondLevelRegion: String,
        thirdLevelRegion: String
    ): Location? {
        return regionData?.get(firstLevelRegion)?.get(secondLevelRegion)?.get(thirdLevelRegion)
    }
}