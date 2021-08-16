package au.com.deloitte.flickr

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.viewModelScope
import au.com.deloitte.flickr.core.DataViewModel
import au.com.deloitte.flickr.network.model.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchPhotosViewModel : DataViewModel<ArrayList<Photo>>() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var searchText = ""
    var nextPage = 1
    var currentPage = 0
    private var searchJob: Job? = null

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun mapList(photo: List<PhotoMetaData>?): List<Photo> =
        photo?.map {
            Photo(
                it.id,
                "https://farm${it.farm}.static.flickr.com/${it.server}/${it.id}_${it.secret}.jpg",
                it.title
            )
        } ?: emptyList()

    fun search(searchText: String) {

        if (searchText != this.searchText) {
            this.searchText = searchText
            nextPage = 1
            currentPage = 0
            searchPhotos()
        }
    }

    fun searchPhotos() {
        showLoading()
        searchPhotos(this::onData, this::onError)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun searchPhotos(success: (ArrayList<Photo>) -> Unit, error: (Throwable) -> Unit) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            var photosList = emptyList<Photo>()

            try {

                if (searchText.isNotBlank()) {
                    val result = repository.searchPhotos(searchText, nextPage)
                    currentPage = result?.data?.photos?.page ?: 0

                    nextPage = currentPage + 1
                    photosList = mapList(result?.data?.photos?.photo)

                } else {
                    nextPage = 1
                    currentPage = 0
                }

                success(ArrayList(photosList))

            } catch (ex: Exception) {
                error(ex)
            }
        }
    }
}
