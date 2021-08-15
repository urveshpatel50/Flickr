package au.com.deloitte.flickr.util

import java.io.*

object MockResource {

    @Throws(IOException::class)
    fun readJsonFile(filename: String): String {
        val rawPath = "../app/src/main/assets/"
        val br = BufferedReader(InputStreamReader(FileInputStream(rawPath + filename)))
        val sb = StringBuilder()
        var line = br.readLine()
        while (line != null) {
            sb.append(line)
            line = br.readLine()
        }
        return sb.toString()
    }
}