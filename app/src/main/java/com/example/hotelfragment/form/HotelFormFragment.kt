package com.example.hotelfragment.form

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.hotelfragment.R
import kotlinx.android.synthetic.main.fragment_hotel_form.*
import com.example.hotelfragment.model.data.Hotel
import com.example.hotelfragment.repository.sqllite.SQLiteRepository
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class HotelFormFragment : DialogFragment(), HotelFormView {
    private val presenter: HotelFormPresenter by inject { parametersOf(this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_hotel_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val hotelId = arguments?.getLong(EXTRA_HOTEL_ID, 0) ?: 0
        presenter.loadHotel(hotelId)
        edtAddress.setOnEditorActionListener { _, i, _ ->
            handleKeyboardEvent(i)
        }
        dialog?.setTitle(R.string.action_new_hotel)
        dialog?.window?.setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
        )
    }

    override fun showHotel(hotel: Hotel) {
        edtName.setText(hotel.name)
        edtAddress.setText(hotel.address)
        rtbRating.rating = hotel.rating
    }

    override fun errorInvalidHotel() {
        Toast.makeText(requireContext(), R.string.error_hotel_not_found, Toast.LENGTH_SHORT).show()
    }

    override fun errorSaveHotel() {
        Toast.makeText(requireContext(), R.string.error_invalid_hotel, Toast.LENGTH_SHORT).show()
    }

    private fun handleKeyboardEvent(actionId: Int): Boolean {
        if (EditorInfo.IME_ACTION_DONE == actionId){
            val hotel = saveHotel()
            if (hotel != null){
                if (activity is OnHotelSavedListener){
                    val listener = activity as OnHotelSavedListener
                    listener.onHotelSaved(hotel)
                }
                //Fecha dialog
                dialog?.dismiss()
                return true
            }
        }
        return false
    }

    private fun saveHotel(): Hotel? {
        val hotel = Hotel()
        val hotelId = arguments?.getLong(EXTRA_HOTEL_ID, 0) ?: 0
        hotel.id = hotelId
        hotel.name = edtName.text.toString()
        hotel.address = edtAddress.text.toString()
        hotel.rating = rtbRating.rating

        return if (presenter.saveHotel(hotel)) {
            hotel
        } else {
            null
        }
    }

    fun open(fm: FragmentManager){
        if (fm.findFragmentByTag(DIALOG_TAG) == null){
            show(fm, DIALOG_TAG)
        }
    }

    interface OnHotelSavedListener {
        fun onHotelSaved(hotel: Hotel)
    }

    companion object {
        private const val EXTRA_HOTEL_ID = "hotelId"
        private const val DIALOG_TAG = "editDialog"

        fun newInstance(hotelId: Long = 0) = HotelFormFragment().apply {
            arguments = Bundle().apply {
                putLong(EXTRA_HOTEL_ID, hotelId)
            }
        }
    }
}