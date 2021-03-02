package com.example.hotelfragment.details

import com.example.hotelfragment.repository.HotelRepository
import com.example.hotelfragment.repository.sqllite.SQLiteRepository

class HotelDetailsPresenter(
    private val view: HotelDetailsView,
    private val repository: SQLiteRepository
) {
    fun loadHotelDetails(id: Long) {
        repository.hotelById(id){
            if(it != null){
                view.showHotelDetails(it)
            } else {
                view.errorHotelDetails()
            }
        }
    }
}