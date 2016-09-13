package com.example.dylan.stocks

import android.util.Log
import org.jetbrains.anko.async
import org.jetbrains.anko.uiThread
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

interface QuandlAPIHandler {
    fun handleAPIResponse(quotes: ArrayList<StockQuote>)
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

    private object mQuandlAPIService { val INSTANCE = QuandlAPIService() }

    // Companion object to call methods in static context (in this case,
    // only the instance of QuandlAPIService is needed in static context to
    // access the singleton object
    companion object {
        val instance: QuandlAPIService by lazy { mQuandlAPIService.INSTANCE }
    }

    /**
     * @param tradeSymbol   The symbol for which stock one desires an End-of-Day quote
     *
     * @param handler       The class that implements the QuandlAPIHandler interface; this class
     *                      will actually process the data fetched by the QuandlAPI Service
     */
    fun asyncEODQuoteRequest(handler: QuandlAPIHandler, tradeSymbols: Array<String>): Unit {
        val quotes: ArrayList<StockQuote> = ArrayList(tradeSymbols.size)
        async() {
            val date = getLatestMarketDateForEOD()
            for (ts in tradeSymbols) {
                val queryString = "$WIKI_DATABASE$ts.json$APPENDED_KEY&$date" // 215.21
                Log.d(TAG, queryString)
                val response = JSONObject(URL(queryString).readText())
                val dataset = response.getJSONObject("dataset")
                val quoteData = dataset.getJSONArray("data")[0] as JSONArray
                val name = dataset.getString("name")
                quotes.add(StockQuote(ts, name, quoteData))
            }
            uiThread {
                handler.handleAPIResponse(quotes);
            }
        }
    }

    /**
     * Utility function that returns the correct date for the most recent EOD stock quotes in the
     * format of yyyy-MM-DD
     */
    private fun getLatestMarketDateForEOD(length: Int = 7): String {
        val ESTCal = GregorianCalendar(TimeZone.getTimeZone("America/New_York"))
        val marketDay = ESTCal.get(Calendar.DAY_OF_WEEK)

        // Handle weekends
        if (marketDay == Calendar.SATURDAY)
        {
            ESTCal.add(Calendar.DATE, -1)
        }
        else if (marketDay == Calendar.SUNDAY)
        {
            ESTCal.add(Calendar.DATE, -2)
        }
        else
        {
            val marketHr = ESTCal.get(Calendar.HOUR_OF_DAY)
            // Before market closes for the day, we must get yesterday's EOD quote
            if (marketHr < 17)
            {
                if (marketDay == Calendar.MONDAY)
                    ESTCal.add(Calendar.DATE, -3)
                else
                    ESTCal.add(Calendar.DATE, -1)
            }
        }


        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale("US"))
        return String.format("start_date=%s&end_date=%s",
                sdf.format(ESTCal.time),
                sdf.format(prevCal.time))
    }
}