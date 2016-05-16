package com.example.dylan.stocks

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.TextView

import org.jetbrains.anko.*


/**
 * Created by Dylan Richardson on 5/11/2016.
 *
 * Integrates components of the application and serves as the main activity.
 */
class MainActivity : Activity(), QuandlAPIHandler {

    val ID_TEXTVIEW = 1
    val mAPIService = QuandlAPIService.instance

    private val TAG = "MainActivity"

    lateinit var myText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        verticalLayout {
            lparams(width=matchParent, height=matchParent)
            myText = textView {
                lparams(width=matchParent, height=wrapContent)
                id = ID_TEXTVIEW
                verticalGravity = Gravity.CENTER_VERTICAL
                gravity = Gravity.CENTER
                text = "TEST TEST TEST"
            }
        }

        Log.d(TAG, "Okay, it works as exxpected")

        mAPIService.asyncEODQuoteRequest("TSLA", this)
    }

    override fun handleAPIResponse(retval: String?, statusCode: String) {
        if (retval != null)
            myText.text = retval
        else
            myText.text = "PROBLEM"
    }
}