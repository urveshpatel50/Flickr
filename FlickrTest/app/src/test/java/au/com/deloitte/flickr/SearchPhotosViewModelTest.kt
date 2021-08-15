package au.com.deloitte.flickr

import au.com.deloitte.flickr.network.exception.GenericException
import au.com.deloitte.flickr.network.exception.NetworkException
import au.com.deloitte.flickr.network.exception.RestException
import au.com.deloitte.flickr.network.model.SearchResponse
import au.com.deloitte.flickr.util.MockResource
import com.google.gson.Gson
import io.mockk.impl.annotations.SpyK
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchPhotosViewModelTest : TestCase() {

    @SpyK
    val searchPhotosViewModel = SearchPhotosViewModel()

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()
    private val testCoroutineScope = TestCoroutineScope(testDispatcher)


    public override fun setUp() {
        super.setUp()
        Dispatchers.setMain(testDispatcher)
    }

    public override fun tearDown() {
        Dispatchers.resetMain()
        testCoroutineScope.cleanupTestCoroutines()
        testDispatcher.cleanupTestCoroutines()
    }

    @ExperimentalCoroutinesApi
    @Throws(RestException::class, NetworkException::class, GenericException::class)
    fun `test successfully searchPhotos`() {

        val json = MockResource.readJsonFile("mock.json")
        val searchResponse = Gson().fromJson(json, SearchResponse::class.java)
        val photo = searchResponse.photos?.photo
        assertNotNull(photo)
        val mockList = searchPhotosViewModel.mapList(photo!!)

        runBlocking {
            searchPhotosViewModel.searchText = "Kxo"
            searchPhotosViewModel.searchPhotos({
                assertNotNull(it)
                assertEquals(mockList, it)
            }, {})
        }
    }

    @Test
    fun `test on mapList`() {

        val json = MockResource.readJsonFile("mock.json")
        val searchResponse = Gson().fromJson(json, SearchResponse::class.java)
        val photo = searchResponse.photos?.photo
        assertNotNull(photo)
        var mockList = searchPhotosViewModel.mapList(photo!!)
        assertNotNull(mockList)

        mockList = searchPhotosViewModel.mapList(photo = null)
        assertNotNull(mockList)
        assertTrue(mockList.isEmpty())
    }
}