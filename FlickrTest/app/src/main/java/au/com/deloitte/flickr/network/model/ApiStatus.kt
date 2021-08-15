package au.com.deloitte.flickr.network.model

enum class ApiStatus {
    FAIL, OK;

    companion object {

        fun matches(stat: String?) = values().find { status ->
            status.name.equals(stat, true)
        }
    }
}