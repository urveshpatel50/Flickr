package au.com.deloitte.flickr.network.repository

import au.com.deloitte.flickr.network.Factory
import au.com.deloitte.flickr.network.exception.GenericException
import au.com.deloitte.flickr.network.exception.NetworkException
import au.com.deloitte.flickr.network.exception.RestException
import au.com.deloitte.flickr.network.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import java.lang.Exception
import kotlin.coroutines.cancellation.CancellationException

class ApiRepository {

    @Throws(RestException::class, NetworkException::class, GenericException::class, CancellationException::class)
    suspend fun searchPhotos(search: String, page: Int): Result<SearchResponse>? {
        try {
            return withContext(Dispatchers.IO) {

                val response = Factory.service().searchPhotos(search, page)

                if (ApiStatus.matches(response.stat) == ApiStatus.OK) {
                    Result(response)

                } else {
                    throw RestException(
                            response.stat,
                            response.code,
                            response.message
                    )
                }
            }

        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> throw NetworkException()

                is CancellationException ->
                    throw CancellationException() //Avoid it as it's a job cancellation

                else -> {
                    throw GenericException()
                }
            }
        }
    }
}