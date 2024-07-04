package com.example.esport.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.esport.R
import com.example.esport.adapter.AvailableSlotsAdapter
import com.example.esport.adapter.CategoriesAdapter
import com.example.esport.adapter.TurfsAdapter
import com.example.esport.model.Response
import com.example.esport.model.SlotRequest
import com.example.esport.model.SlotResponse
import com.example.esport.model.Turfs
import com.example.esport.util.SharedPreferencesHelper

class TurfFragment(
    private val turfs: List<Response>,
    private val context: Context,
    private val viewModel: ESportsViewModel,
    private val toolBarBack: ImageView,
    private val toolBarTextView: TextView,
) : Fragment(), TurfsListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TurfsAdapter // Make sure to have your adapter defined
    private lateinit var view2: View
    private lateinit var sharedPreferences: SharedPreferencesHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_turf, container, false)
        initializeRecyclerView(view, "true", null, null, -1)
        toolBarBack.visibility = View.VISIBLE
        view2 = view
        return view
    }

    private fun initializeRecyclerView(
        view: View,
        isTurfsAdapter: String?,
        turfs2: List<Turfs>?,
        slotResponse: List<SlotResponse>?,
        turfId: Int?
    ) {
        val recyclerView: RecyclerView = view.findViewById(R.id.rvTurf)
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        if (isTurfsAdapter.equals("true", true)) {
            val adapter = TurfsAdapter(context, turfs, this)
            recyclerView.adapter = adapter
        } else if (isTurfsAdapter.equals("false", true)) {
            val adapter = turfs2?.let { CategoriesAdapter(context, it, this) }
            recyclerView.adapter = adapter
        } else {
            val adapter = slotResponse?.let { AvailableSlotsAdapter(context, it, this, turfId) }
            recyclerView.adapter = adapter
        }
    }

    override fun onSlotResponseClicked(slotResponse: SlotResponse) {
        super.onSlotResponseClicked(slotResponse)
        val slotRequest = SlotRequest(
            turfId = slotResponse.turfId,
            startTime = slotResponse.startTime,
            endTime = slotResponse.endTime
        )
        viewModel.bookSlotRequestResponse.observe(
            this,
        ) { bookingSlot ->
            sharedPreferences = SharedPreferencesHelper(context)
            if (bookingSlot?.code == 200) {
                Toast.makeText(context, "${bookingSlot.message}", Toast.LENGTH_SHORT).show()
                slotResponse.message = bookingSlot.message
                var count = sharedPreferences.getNotificationCount()?.plus(1)
                sharedPreferences.saveNotificationCount(count)
                refreshActivity()
            } else if (bookingSlot?.code == 400) {
                Toast.makeText(context, "${bookingSlot.error}", Toast.LENGTH_SHORT).show()
                slotResponse.message = bookingSlot.error
            }
            sharedPreferences.saveSlotResponse(slotResponse)
        }
        viewModel.bookSlot(slotRequest)
    }

    private fun refreshActivity() {
        startActivity(Intent(context, ESportsActivity::class.java))
    }

    override fun onTurfClicked(response: Response?) {
        var turfList = mutableListOf<Turfs>()
        for (i in response!!.categories) {
            for (i1 in i.turfs) {
                turfList.add(i1)
            }
        }
        toolBarTextView?.text = response.name
        initializeRecyclerView(view2, "false", turfList, null, -1)
    }

    override fun onCategoriesClicked(turfs: Turfs) {
        super.onCategoriesClicked(turfs)
        viewModel.slotList.observe(
            this
        ) { slotResponse ->
            initializeRecyclerView(view2, "", null, slotResponse?.response, turfs.id)
        }
        viewModel.fetchAllSlotBookingTimings(turfs)
    }
}