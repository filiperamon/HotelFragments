package com.example.hotelfragment.form

import com.example.hotelfragment.model.data.Hotel

interface HotelFormView {
    fun showHotel(hotel: Hotel)
    fun errorInvalidHotel()
    fun errorSaveHotel()
}