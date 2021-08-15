package au.com.deloitte.flickr.network

import au.com.deloitte.flickr.network.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1&api_key=96358825614a5d3b1a1c3fd87fca2b47")
    suspend fun searchPhotos(
        @Query(value = "text") searchText: String, @Query(value = "page") page:Int): SearchResponse
}