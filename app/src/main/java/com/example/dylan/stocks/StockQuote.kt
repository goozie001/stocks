package com.example.dylan.stocks

import org.json.JSONArray
import org.json.JSONObject

/**
 * Created by Dylan Richardson on 5/22/2016.
 *
 * A model that contains any information relevant to a single stock quote
 */

class StockQuote constructor(quoteJSON: JSONObject) {

    val symVal: String
    val data: Double

    companion object {
        val SYMBOL = "sym"
        val DATA = "data"
    }

    init
    {
        symVal = quoteJSON.getString(SYMBOL)
        val dataArray = quoteJSON.getJSONArray(DATA)[0]
        val dataArrayLength = (dataArray as JSONArray).length()
        data = dataArray[dataArrayLength - 2] as Double
    }
}