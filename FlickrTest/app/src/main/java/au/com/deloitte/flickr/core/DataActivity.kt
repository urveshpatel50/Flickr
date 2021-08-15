package au.com.deloitte.flickr.core

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import au.com.deloitte.flickr.R
import au.com.deloitte.flickr.network.exception.GenericException
import au.com.deloitte.flickr.network.exception.NetworkException
import com.google.android.material.snackbar.Snackbar

abstract class DataActivity<Data, ViewModel : DataViewModel<Data>, Binding : ViewDataBinding> :
    AppCompatActivity(), DataView<Data> {

    protected lateinit var viewModel: ViewModel

    lateinit var binding: Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this,
            layoutId()
        )
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        registerObserver()
    }

    private fun registerObserver() {
        viewModel.liveData.observe(this, this::onData)
        viewModel.error.observe(this, this::onError)
        viewModel.loading.observe(this, this::loading)
    }

    override fun onData(data: Data) {

    }

    override fun onError(error: Throwable) {

        when (error) {
            is GenericException -> showSnackbar(R.string.generic_error_message)

            is NetworkException -> showSnackbar(R.string.network_error_message)
        }
    }

    fun showSnackbar(@StringRes message: Int) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }

    fun showSnackbar(message: String) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }

    private fun loading(loading: Boolean) {
        if (loading) {
            showLoading()

        } else {
            hideLoading()
        }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    @LayoutRes
    protected abstract fun layoutId(): Int

    protected inline fun <reified VM : ViewModel> viewModel() =
        viewModels<VM>()
}