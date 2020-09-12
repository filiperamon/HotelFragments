package presenter

import model.repository.HotelRepository
import view.HotelDetailsView

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