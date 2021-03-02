package com.example.hotelfragment.di

import com.example.hotelfragment.details.HotelDetailsPresenter
import com.example.hotelfragment.details.HotelDetailsView
import com.example.hotelfragment.form.HotelFormPresenter
import com.example.hotelfragment.form.HotelFormView
import com.example.hotelfragment.list.HotelListPresenter
import com.example.hotelfragment.list.HotelListView
import com.example.hotelfragment.repository.sqllite.SQLiteRepository
import org.koin.dsl.module.module

val androidModule = module {

    //Unica Instancia
    single { this }
    single {
        SQLiteRepository(context = get())
    }

    //Multiplas Instancias
    factory { (view: HotelListView) ->
        HotelListPresenter(
            view,
            repository = get()
        )
    }
    factory { (view: HotelDetailsView) ->
        HotelDetailsPresenter(
            view
            , repository = get()
        )
    }
    factory { (view: HotelFormView) ->
        HotelFormPresenter(
            view,
            repository = get()
        )
    }
}