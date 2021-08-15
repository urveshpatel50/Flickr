package au.com.deloitte.flickr.core

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import au.com.deloitte.flickr.network.exception.GenericException
import au.com.deloitte.flickr.network.repository.ApiRepository

abstract class DataViewModel<Data> : ViewModel() {

    val liveData = MutableLiveData<Data>()
    val error = MutableLiveData<Throwable>()
    val loading = MutableLiveData<Boolean>()

    @VisibleForTesting(otherwise = VisibleForTesting.PROTECTED)
    var repository = ApiRepository()


    open fun onData(data: Data) {
        hideLoading()
        data?.let { liveData.postValue(it) } ?: onError(GenericException())
    }

    open fun onError(throwable: Throwable) {
        hideLoading()
        error.postValue(throwable)
    }

    open fun showLoading() {
        loading.postValue(true)
    }

    open fun hideLoading() {
        loading.postValue(false)
    }
}