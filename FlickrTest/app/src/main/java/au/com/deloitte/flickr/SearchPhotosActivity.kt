package au.com.deloitte.flickr

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.GridLayoutManager
import au.com.deloitte.flickr.core.DataActivity
import au.com.deloitte.flickr.databinding.FlickrGalleryActivityBinding
import au.com.deloitte.flickr.network.exception.RestException
import au.com.deloitte.flickr.network.model.Photo
import au.com.deloitte.flickr.widget.PaginationRecyclerView
import com.google.android.material.snackbar.Snackbar

class SearchPhotosActivity :
    DataActivity<ArrayList<Photo>, SearchPhotosViewModel, FlickrGalleryActivityBinding>() {

    private lateinit var progressBar:ProgressBar

    private val photosAdapter = PhotosAdapter()

    override fun layoutId() = R.layout.flickr_gallery_activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = viewModel<SearchPhotosViewModel>().value

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setHomeButtonEnabled(false)

        val searchView = binding.toolbar.findViewById(R.id.search) as SearchView

        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.search(newText)
                return false
            }
        })

        binding.recyclerView.adapter = photosAdapter
        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)

        binding.recyclerView.paginationListener =
            object : PaginationRecyclerView.OnPaginationListener {

                override fun onNextPageLoad() {
                    viewModel.searchPhotos()
                }
            }
    }

    override fun onData(data: ArrayList<Photo>) {
        super.onData(data)

        with(photosAdapter) {

            if (viewModel.nextPage == 1) {
                photos.clear()
            }

            photos.addAll(data)
            notifyDataSetChanged()
        }
    }


    override fun onError(error: Throwable) {

        if (error is RestException) {
            error.msg?.let { showSnackbar(it) } //displaying error message from the api. Please refer

        } else {
            super.onError(error)
        }
    }

    override fun showLoading() {
        binding.progress.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        binding.progress.visibility = View.GONE
    }
}