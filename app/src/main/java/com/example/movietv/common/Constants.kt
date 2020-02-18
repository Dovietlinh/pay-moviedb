package com.example.movietv.common

class Constants {
    companion object {
        const val API_KEY = "1bf39cdc4ef9196254b01f85c1c05398"
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val POSTER_BASE_URL = "https://image.tmdb.org/t/p/w342"
        const val FIRST_PAGE = 1
        const val POST_PER_PAGE = 20
        const val API_POPULAR: String = "popular"
        const val API_NOW_PLAYING: String = "now_playing"
        const val API_UPCOMING: String = "upcoming"
        const val API_TOPRATE: String = "top_rated"
        const val MOVIE_VIEW_TYPE = 1
        const val NETWORK_VIEW_TYPE = 2
        const val MOVIE_ID ="movieID"
        const val TYPE_MOVIE ="type"
        const val TITLE_CATEGORY ="title"
        const val IS_UPCOMING_DISPLAYED ="is_upcoming_displayed"
        const val DB_NAME = "movieTV_db"
        const val BASE_YOUTUBE_URL = "http://img.youtube.com/vi/"
        const val ENDPOINT_YOUTUBE_URL = "/maxresdefault.jpg"
    }
}