package com.example.hotelfragment.details

import com.example.hotelfragment.repository.HotelRepository

class HotelDetailsPresenter(
    private val view: HotelDetailsView,
    private val repository: HotelRepository
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