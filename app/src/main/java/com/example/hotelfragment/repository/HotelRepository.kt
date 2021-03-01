package com.example.hotelfragment.repository

import com.example.hotelfragment.model.data.Hotel

interface HotelRepository {
    fun save(hotel: Hotel)
    fun remove(vararg hotel: Hotel)
    fun hotelById(id: Long, callback: (Hotel?) -> Unit)
    fun search(term: String, callback: (List<Hotel>) -> Unit)
}