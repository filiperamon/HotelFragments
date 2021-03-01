package com.example.hotelfragment.details

import com.example.hotelfragment.model.data.Hotel

interface HotelDetailsView {
    fun showHotelDetails(hotel: Hotel)
    fun errorHotelDetails()
}