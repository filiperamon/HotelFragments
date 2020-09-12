package view

import model.data.Hotel

interface HotelListView {
    fun showHotels(hotels: List<Hotel>)
    fun showHotelDetails(hotel: Hotel)

    fun showDeleteMode() //Habilida modo de exclusao
    fun hideDeleteMode() //Desabilida modo de exclusao
    fun showSelectedHotels(hotels: List<Hotel>) //Marcara os hoteis selecionados para exclusao
    fun updateSelectionCountText(count: Int) //Atualiza titulo quantidade de itens
}