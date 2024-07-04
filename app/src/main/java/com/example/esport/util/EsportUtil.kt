package com.example.esport.util

import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.toolbar.tvNotificationCount

object EsportUtil {
     fun updateNotification(count: Int?, tvNotificationCount: TextView, sharedPreferences: SharedPreferencesHelper) {
        if (count == 0) tvNotificationCount.visibility = View.GONE
        else {
            var total = sharedPreferences.getNotificationTotal()
            if (count != null) {
                if (count > 1) {
                    var diff = total?.minus(count)?.let { kotlin.math.abs(it) }
                    if (diff != null) {
                        if (diff > 0) {
                            tvNotificationCount.visibility = View.VISIBLE
                            tvNotificationCount.text = diff.toString()
                        }
                    }
                } else {
                    tvNotificationCount.visibility = View.VISIBLE
                    tvNotificationCount.text = count.toString()
                }
            }
        }
    }

}