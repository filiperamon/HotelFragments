package com.example.hotelfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import model.data.Hotel
import view.HotelDetailsFragment
import view.HotelListFragment

class MainActivity : AppCompatActivity(), HotelListFragment.OnHotelClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onHotelClick(hotel: Hotel) {
        when {
            isTablet() -> {
                showDetailsFragment(hotel.id)
            }
            isSmartphone() -> {
                showDetailsActivity(hotel.id)
            }
            else -> {
                showDetailsActivity(hotel.id)
            }
        }
    }

    //private fun isTablet() = findViewById<View>(R.id.details) != null
    private fun isTablet() = resources.getBoolean(R.bool.tablet)
    private fun isSmartphone() = resources.getBoolean(R.bool.smartphone)

    private fun showDetailsActivity(hotelId: Long) {
        ActivityHotelDetails.open(this, hotelId)
    }

    private fun showDetailsFragment(hotelId: Long) {
        val fragment = HotelDetailsFragment.newInstance(hotelId)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.details, fragment, HotelDetailsFragment.TAG_DETAILS)
                .commit()
    }
}