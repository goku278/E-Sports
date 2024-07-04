package com.example.esport.ui.home

import android.app.Application
import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.esport.model.BookSlotRequest
import com.example.esport.model.BookSlotRequestResponse
import com.example.esport.model.Slot
import com.example.esport.model.SlotRequest
import com.example.esport.model.SlotResponse
import com.example.esport.model.TurfList
import com.example.esport.model.Turfs
import com.example.esport.model.dao.DeleteResponse
import com.example.esport.network.ApiService
import com.example.esport.util.Constant
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class ESportsViewModel @Inject constructor(
    private val application: Application,
) : AndroidViewModel(application) {

    @Inject
    lateinit var apiService: ApiService

    private lateinit var selectedDate: String

    private val _turfList = MutableLiveData<TurfList?>()
    val turfs: MutableLiveData<TurfList?> = _turfList

    private val _slotList = MutableLiveData<Slot?>()
    val slotList: MutableLiveData<Slot?> = _slotList

    private val _cancelBookedSlot = MutableLiveData<DeleteResponse?>()
    val cancelBookedSlot: MutableLiveData<DeleteResponse?> = _cancelBookedSlot

    private val _bookSlotRequestResponse = MutableLiveData<BookSlotRequestResponse?>()
    val bookSlotRequestResponse: MutableLiveData<BookSlotRequestResponse?> =
        _bookSlotRequestResponse


    init {
        (application as MyApplication).getRetroComponent().inject(this)
    }


    fun fetchAllTurfs() {
        val call = apiService.fetchAllTurfs(Constant.apiKey)  // Correct method name here
        call.enqueue(object : Callback<TurfList> {
            override fun onResponse(call: Call<TurfList>, response: Response<TurfList>) {
                if (response.isSuccessful) {
                    val turfs = response.body()
                    turfs?.let {
                        // Log the response
                        Log.d("MainActivity", "Turfs: ${Gson().toJson(turfs)}")
                        // Post value to LiveData
                        _turfList.postValue(turfs)
                    } ?: run {
                        Log.e("MainActivity", "Failed to retrieve turfs: Response body is null")
                    }
                } else {
                    Log.e(
                        "MainActivity",
                        "Failed to retrieve turfs: ${response.errorBody()?.string()}"
                    )
                }
            }

            override fun onFailure(call: Call<TurfList>, t: Throwable) {
                Log.e("MainActivity", "Error fetching turfs", t)
            }
        })
    }

    fun cancelBookedSlot(slotResponse: SlotResponse) {
        val call = slotResponse.turfId?.let {
            apiService.cancelBooking(
                Constant.apiKey,
                slotResponse.turfId!!
            )
        }
        Log.d(
            "MainActivity",
            "Request URL: http://43.205.87.112:8080/vendor/venue/v1/cancelbooking?id=11"
        )
        Log.d("MainActivity", "API Key: ${Constant.apiKey}")

        call?.enqueue(object : Callback<DeleteResponse> {
            override fun onResponse(
                call: Call<DeleteResponse>,
                response: Response<DeleteResponse>
            ) {
                if (response.isSuccessful) {
                    val deleteResponse = response.body()
                    deleteResponse?.let {
                        // Log the response
                        Log.d("MainActivity", "Response: ${Gson().toJson(deleteResponse)}")
                        // Post value to LiveData
                        _cancelBookedSlot.postValue(deleteResponse)
                    } ?: run {
                        Log.e("MainActivity", "Failed to retrieve response: Response body is null")
                    }
                } else {
                    Toast.makeText(
                        getApplication(),
                        "${response.raw().message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e(
                        "MainActivity",
                        "Failed to retrieve response: ${response.errorBody()?.string()}"
                    )
                }
            }

            override fun onFailure(call: Call<DeleteResponse>, t: Throwable) {
                Log.e("MainActivity", "Error making request", t)
            }
        })
    }

    fun bookSlot(slotRequest: SlotRequest) {
        val slots = arrayListOf<SlotRequest>(
            slotRequest
        )

        val bookSlotRequest = BookSlotRequest(
            slots = slots,
            userFirstName = "test",
            userLastName = "user",
            userEmail = "tu1@gmail.com",
            userDialCode = "+91",
            userMobileNo = "9999999995"
        )

        val call = apiService.bookSlot(
            apiKey = "80b856a2acc361a849858e8123ccef26bef7452f11403072024160737",
            request = bookSlotRequest
        )

        call.enqueue(object : Callback<BookSlotRequestResponse> {
            override fun onResponse(
                call: Call<BookSlotRequestResponse>,
                response: Response<BookSlotRequestResponse>
            ) {
                if (response.isSuccessful) {
                    val slotsBooked = response.body()
                    slotsBooked?.turfId = slotRequest.turfId
                    // Handle successful response
                    Log.e("Booking", "Booking Success: $slotsBooked")
                    _bookSlotRequestResponse.postValue(slotsBooked)
                } else {
                    // Handle unsuccessful response
                    _bookSlotRequestResponse.postValue(response.body())
                    val errorBody = response.errorBody()?.string()
                    Log.e("Booking", "Booking failed: $errorBody")
                }
            }

            override fun onFailure(call: Call<BookSlotRequestResponse>, t: Throwable) {
                // Handle request failure
                Log.e("Booking", "Error booking slot", t)
            }
        })
    }

    fun fetchDateFromUser(context: Context, turfs: Turfs) {
        val turfId = turfs.id ?: return // Ensure turfs.id is not null

        // Show a DatePickerDialog to get user input for the date
        val calendar = Calendar.getInstance()
        val initialYear = calendar.get(Calendar.YEAR)
        val initialMonth = calendar.get(Calendar.MONTH)
        val initialDay = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context, // Use activity context
            { _, year, month, day ->
                // Format the selected date as yyyy-MM-dd
                selectedDate = String.format(Locale.US, "%04d-%02d-%02d", year, month + 1, day)

                if (selectedDate != null) {
                    fetchAllSlotBookingTimings(context, turfs)
                }
                // Fetch slots using the selected date
                // fetchSlotsByDate(turfId, selectedDate)
            },
            initialYear,
            initialMonth,
            initialDay
        )

        // Show the date picker dialog
        datePickerDialog.show()
    }

    private fun fetchAllSlotBookingTimings(context: Context, turfs: Turfs) {
        val turfId = turfs.id ?: (return) // Ensure turfs.id is not null
//        fetchDateFromUser(context, turfs)
        val call = apiService.fetchRelevantSlotsByTurfIdAndDate(
            turfId,
            selectedDate,
            Constant.apiKey
        )

        call.enqueue(object : Callback<Slot> {
            override fun onResponse(call: Call<Slot>, response: Response<Slot>) {
                if (response.isSuccessful) {
                    val slots = response.body()
                    slots?.let {
                        // Log the response
                        Log.d("MainActivity", "Slots: ${Gson().toJson(slots)}")
                        // Post value to LiveData or process the data
                        _slotList.postValue(slots)
                    } ?: run {
                        Log.e("MainActivity", "Failed to retrieve slots: Response body is null")
                    }
                } else {
                    Log.e(
                        "MainActivity",
                        "Failed to retrieve slots: ${response.errorBody()?.string()}"
                    )
                }
            }

            override fun onFailure(call: Call<Slot>, t: Throwable) {
                Log.e("MainActivity", "Error fetching slots", t)
            }
        })
    }
}