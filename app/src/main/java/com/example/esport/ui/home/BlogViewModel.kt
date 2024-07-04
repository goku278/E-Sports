/*~
package com.example.esport.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.esport.network.ApiService
import com.example.esport.ui.home.MyApplication
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class BlogViewModel @Inject constructor(
    private val application: Application,
) : AndroidViewModel(application) {

    @Inject
    lateinit var apiService: ApiService

    private val _images = MutableLiveData<Images>()
    val images: LiveData<Images> = _images

    private val _videos = MutableLiveData<VideoData>()
    val videos: LiveData<VideoData> = _videos

    init {
        (application as MyApplication).getRetroComponent().inject(this)
    }

    fun fetchCuratedImages() {
        val call = apiService.getCuratedImages()
        call.enqueue(object : Callback<Images> {
            override fun onResponse(call: Call<Images>, response: Response<Images>) {
                if (response.isSuccessful) {
                    val images = response.body()
                    Log.d("MainActivity", "Images: $images")
                    _images.postValue(images!!)
                } else {
                    Log.e("MainActivity", "Failed to retrieve images: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<Images>, t: Throwable) {
                Log.e("MainActivity", "Error fetching images", t)
            }
        })
    }

    fun getVideos() {
        val call = apiService.getVideos("nature",1)
        call.enqueue(object : Callback<VideoData> {
            override fun onResponse(call: Call<VideoData>, response: Response<VideoData>) {
                if (response.isSuccessful) {
                    val videos = response.body()
                    Log.d("MainActivity", "Images: $videos")
                    _videos.postValue(videos!!)
                } else {
                    Log.e("MainActivity", "Failed to retrieve images: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<VideoData>, t: Throwable) {
                Log.e("MainActivity", "Error fetching images", t)
            }
        })
    }

}*/
