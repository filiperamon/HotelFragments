package com.example.hotelfragment.form

import com.example.hotelfragment.model.data.Hotel
import com.example.hotelfragment.model.service.HotelValidator
import com.example.hotelfragment.repository.sqllite.SQLiteRepository
import java.lang.Exception

class HotelFormPresenter(
    private val view: HotelFormView,
    private val repository: SQLiteRepository
) {
    private val validator = HotelValidator()

    fun loadHotel(id: Long) {
        repository.hotelById(id) { hotel ->
            if (hotel != null) {
                view.showHotel(hotel)
            }
        }
    }

    fun saveHotel(hotel: Hotel): Boolean {
        return if (validator.validate(hotel)) {
            try {
                repository.save(hotel)
                true
            } catch (e: Exception) {
                view.errorSaveHotel()
                false
            }
        } else {
            view.errorInvalidHotel()
            false
        }
    }
}