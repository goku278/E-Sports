package com.example.esport.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.esport.R
import com.example.esport.model.Response
import com.example.esport.util.SharedPreferencesHelper
import kotlinx.android.synthetic.main.activity_esports.rlToolbar
import kotlinx.android.synthetic.main.toolbar.toolbar_back
import kotlinx.android.synthetic.main.toolbar.toolbar_notification_icon
import kotlinx.android.synthetic.main.toolbar.toolbar_title
import kotlinx.android.synthetic.main.toolbar.tvNotificationCount
import kotlinx.android.synthetic.main.toolbar.view.toolbar_title


class ESportsActivity : AppCompatActivity() {
    private lateinit var viewModel: ESportsViewModel
    private lateinit var sharedPreferences: SharedPreferencesHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_esports)
        viewModel = ViewModelProvider(this)[ESportsViewModel::class.java]
        setScreenName()
        getAllTurfs()
        setClicks()
        checkNotification()
    }

    override fun onResume() {
        super.onResume()
        checkNotification()
    }

    override fun onStart() {
        super.onStart()
        checkNotification()
    }

    private fun checkNotification() {
        sharedPreferences = SharedPreferencesHelper(this)
        var count = sharedPreferences.getNotificationCount()
        updateNotification(count)
    }

    private fun updateNotification(count: Int?) {
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

    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(Intent(this, ESportsActivity::class.java))
    }

    private fun setClicks() {
        toolbar_back?.setOnClickListener {
            startActivity(Intent(this, ESportsActivity::class.java))
        }
        toolbar_notification_icon?.setOnClickListener {
            replaceFragment(
                NotificationFragment(
                    viewModel,
                    this,
                    toolbar_back,
                    toolbar_title,
                    tvNotificationCount
                )
            )
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun setScreenName() {
        rlToolbar.toolbar_title.text = "E-Sports"
    }

    private fun getAllTurfs() {
        viewModel.turfs.observe(
            this,
            Observer { turfs ->
                turfs?.response?.let { callTurfsFragment(turfs.response) }
            }
        )
        viewModel.fetchAllTurfs()
    }

    private fun callTurfsFragment(turfs: List<Response>) {
        // Replace RecyclerView with TurfFragment
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragmentContainer,
                TurfFragment(
                    turfs,
                    this,
                    viewModel,
                    toolbar_back,
                    toolbar_title
                )
            )
            .commit()
    }
}