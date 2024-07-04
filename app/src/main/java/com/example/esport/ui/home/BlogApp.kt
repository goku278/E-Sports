package com.example.esport.ui.home

import androidx.appcompat.app.AppCompatActivity

class BlogApp : AppCompatActivity() {
   /* @Inject
    lateinit var apiService: ApiService
    private lateinit var viewModel: BlogViewModel
    private lateinit var photos: List<Photos>
    private lateinit var videoFiles: List<VideoFiles>
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog_app)
        viewModel = ViewModelProvider(this)[BlogViewModel::class.java]
        sharedPreferencesHelper = SharedPreferencesHelper(applicationContext)
        checkInternet()
    }

    private fun checkInternet() {
        val isInternetAvailable = NetworkUtil.isInternetAvailable(applicationContext)
        if (isInternetAvailable) {
            // Internet is available
            callApi()
        } else {
            // Internet is not available
            // Load locally saved data from the shared preferences
            photos = sharedPreferencesHelper.getPhotos("image")!!
            videoFiles = sharedPreferencesHelper.getVideoFiles("video")!!
            setImageAdapter()
            setVideoAdapter()
        }

    }

    private fun callApi() {
        getImages()
        getVideos()
    }

    private fun getVideos() {
        viewModel.videos.observe(this, Observer { videos ->
            if (videos != null) {
                var k = 0
                for (video in videos.videos) {
                    videoFiles = video.videoFiles
                    for (videoPictures in video.videoPictures) {
                        if (k < video.videoFiles.size) {
                            videoFiles[k++].thumbNails = videoPictures.picture
                        }
                        else if (k == video.videoFiles.size) break
                    }
                    sharedPreferencesHelper.saveVideoFiles(videoFiles, "video")
                    // call adapter
                    setVideoAdapter()
                }
            }
        })
        viewModel.getVideos()
    }

    private fun setVideoAdapter() {
        val recyclerView: RecyclerView = findViewById(R.id.rvVideo)
        recyclerView.layoutManager = LinearLayoutManager(this)

        var link = ""

        for (i in videoFiles) {
            if (i.quality!!.equals("hd", true)) link = i.link!!
        }

        for (i in videoFiles) {
            if (i.quality!!.equals("uhd", true) || i.quality!!.equals("sd", true)) {
                i.link = link
            }
        }

        val adapter = VideoAdapter(videoFiles, application)
        recyclerView.adapter = adapter
    }

    private fun getImages() {
        viewModel.images.observe(this, Observer { images ->
            // Update UI with the fetched images
            if (images != null) {
                for (photo in images.photos) {
                    Log.d("MainActivity", "Photo ID: ${photo.id}, URL: ${photo.url}")
                    // Here you can update your UI with photo details
                    photos = images.photos
                    sharedPreferencesHelper.savePhotos(images.photos, "image")
                    // call adapter
                    setImageAdapter()
                }
            } else {
                Log.e("MainActivity", "Failed to load images")
            }
        })
        viewModel.fetchCuratedImages()
    }

    private fun setImageAdapter() {
        val recyclerView: RecyclerView = findViewById(R.id.rvHorizontalImage)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val adapter = ImagesAdapter(photos as ArrayList<Photos>)
        recyclerView.adapter = adapter
    }*/
}