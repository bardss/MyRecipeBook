package com.jakubaniola.myrecipebook.utils

class UrlUtil {

    fun prepareUrlToOpenInBrowser(url: String): String {
        return if (url.isNotEmpty() &&
            (!url.contains("https://") || !url.contains("https://"))
        ) {
            "http://$url"
        } else url
    }
}