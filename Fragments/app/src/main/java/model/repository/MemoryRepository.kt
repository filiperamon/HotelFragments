package model.repository

import model.data.Hotel

object MemoryRepository : HotelRepository {
    private var nextId = 1L
    private var hotelList = mutableListOf<Hotel>()

    init {
        save(Hotel(0, "Gran Marquise", "Av. Beira Mar", 5.0f))
        save(Hotel(0, "New Beach Hotel", "Av. Boa Viagem", 4.5f))
        save(Hotel(0, "Recife Hotel", "Av. Boa Viagem", 4.0f))
        save(Hotel(0, "Marina Park", "Av. Beira Mar", 3.0f))
        save(Hotel(0, "Hotel Cool", "Rua dos Navegantes", 3.5f))
        save(Hotel(0, "Ecenza", "Av. Beira Mar", 5.0f))
    }

    override fun save(hotel: Hotel) {
        if (hotel.id == 0L) {
            hotel.id = nextId++
            hotelList.add(hotel)
        } else {
            val index = hotelList.indexOfFirst {
                it.id == hotel.id
            }

            if (index > -1) {
                hotelList[index] = hotel
            } else {
                hotelList.add(hotel)
            }
        }
    }

    override fun remove(vararg hotel: Hotel) {
        hotelList.removeAll(hotel)
    }

    override fun hotelById(id: Long, callback: (Hotel?) -> Unit) {
        callback(hotelList.find {
            it.id == id
        })
    }

    override fun search(term: String, callback: (List<Hotel>) -> Unit) {
        callback(
            if (term.isEmpty()) hotelList
            else hotelList.filter {
                it.name.toUpperCase().contains(term.toUpperCase())
            }
        )
    }
}