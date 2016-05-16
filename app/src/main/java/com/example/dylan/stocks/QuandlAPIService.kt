package com.example.dylan.stocks

import android.util.Log
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import java.net.URL

interface QuandlAPIHandler {
    fun handleAPIResponse(retval: String?, statusCode: String)
}

/**
 * Created by Dylan Richardson on 5/11/2016.
 *
 * Singleton that queries Quindl's market API for specific queries and returns
 * data formatted specifically for this application
 */
class QuandlAPIService private constructor() {

    private val API_KEY: String         = "S4bn6HVxzKZFjL239Xu8"
    private val APPENDED_KEY: String    = "?api_key=" + API_KEY
    private val WIKI_DATABASE: String   = "https://www.quandl.com/api/v3/datasets/WIKI/"

    private val TAG: String             = "QuandlAPIService"

    init {
        // Do any setup required here
    }

    private object mQuindlAPIService { val INSTANCE = QuandlAPIService() }

    // Companion object to call methods in static context (in this case,
    // only the instance of QuandlAPIService is needed in static context to
    // access the singleton object
    companion object {
        val instance: QuandlAPIService by lazy { mQuindlAPIService.INSTANCE }
    }

    /**
     * @param tradeSymbol   The symbol for which stock one desires an End-of-Day quote
     *
     * @param handler       The class that implements the QuandlAPIHandler interface; this class
     *                      will actually process the data fetched by the QuandlAPI Service
     */
    fun asyncEODQuoteRequest(tradeSymbol: String, handler: QuandlAPIHandler): Unit {
        async() {
            val result = URL(WIKI_DATABASE + tradeSymbol + ".json" + APPENDED_KEY).readText()
            
            uiThread {
                handler.handleAPIResponse(result, "OK");
            }
        }
    }
}