package au.com.deloitte.flickr.core

import androidx.lifecycle.LifecycleOwner

interface DataView<Data> : LifecycleOwner{

    fun onData(data:Data)

    fun onError(error:Throwable)

    fun showLoading()

    fun hideLoading()
}