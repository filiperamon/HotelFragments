package presenter

import model.data.Hotel
import model.repository.HotelRepository
import view.HotelListView
import javax.security.auth.callback.Callback

class HotelListPresenter(
    private val view: HotelListView,
    private val repository: HotelRepository
) {
    private var lastTerm = ""
    private var inDeleteMode = false
    private var selectedItems = mutableListOf<Hotel>()

    fun searchHotels(term: String) {
        repository.search(term) {
            view.showHotels(it)
        }
    }

    fun showHotelDetails(hotel: Hotel){
        view.showHotelDetails(hotel)
    }

    fun selectHotel(hotel: Hotel) {
        if(inDeleteMode) {
            toggleHotelSelected(hotel)
            if (selectedItems.size == 0){
                view.hideDeleteMode()
            } else {
                view.updateSelectionCountText(selectedItems.size)
                view.showSelectedHotels(selectedItems)
            }
        } else {
            view.showHotelDetails(hotel)
        }
    }

    private fun toggleHotelSelected(hotel: Hotel) {
        val existing = selectedItems.find { it.id == hotel.id }

        if (existing == null) {
            selectedItems.add(hotel)
        } else {
            selectedItems.removeAll { it.id == hotel.id }
        }
    }

    fun showDeleteMode(){
        inDeleteMode = true
        view.showDeleteMode()
    }

    fun hideDeleteMode() {
        inDeleteMode = false
        selectedItems.clear()
        view.hideDeleteMode()
    }

    fun refresh(){
        searchHotels(lastTerm)
    }

    fun deleteSelected(callback: (List<Hotel>) -> Unit){
        repository.remove(*selectedItems.toTypedArray())
        refresh()
        callback(selectedItems)
        hideDeleteMode()
    }

}