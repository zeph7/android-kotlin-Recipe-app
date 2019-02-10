package com.zeph7.recipefinder.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.zeph7.recipefinder.R
import kotlinx.android.synthetic.main.activity_show_link.*

class ShowLink : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_link)

        var extras = intent.extras

        if (extras != null) {
            var link = extras.get("link")

            webViewId.loadUrl(link.toString())

        }
    }
}
