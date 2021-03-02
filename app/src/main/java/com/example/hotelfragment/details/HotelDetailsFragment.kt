package com.example.hotelfragment.details

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.ShareActionProvider
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import com.example.hotelfragment.R
import kotlinx.android.synthetic.main.fragment_hotel_details.*
import com.example.hotelfragment.model.data.Hotel
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class HotelDetailsFragment : Fragment(), HotelDetailsView {
    private val presenter: HotelDetailsPresenter by inject { parametersOf(this) }
    private var hotel: Hotel? = null
    private var shareActionProvider: ShareActionProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.hotel_detils, menu)
        var shareItem = menu?.findItem(R.id.action_share)
        shareActionProvider = MenuItemCompat.getActionProvider(shareItem) as? ShareActionProvider
        setShareIntent()
    }

    private fun setShareIntent() {
        val text = getString(R.string.share_text, hotel?.name, hotel?.rating)
        shareActionProvider?.setShareIntent(Intent(Intent.ACTION_SEND).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT)
            type = "text/plain"
            putExtra(Intent.EXTRA_TEXT, text)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_hotel_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadHotelDetails(arguments?.getLong(EXTRA_HOTEL_ID, -1) ?: -1)
    }

    override fun showHotelDetails(hotel: Hotel) {
        this.hotel = hotel
        txtName.text = hotel.name
        txtAddress.text = hotel.address
        rtbRatting.rating = hotel.rating
    }

    override fun errorHotelDetails() {
        txtName.text = getString(R.string.error_hotel_not_found)
        txtAddress.visibility = View.GONE
        rtbRatting.visibility = View.GONE
    }

    companion object {
        const val TAG_DETAILS = "tagDetalhe"
        private const val EXTRA_HOTEL_ID = "hotelId"

        fun newInstance(id: Long) = HotelDetailsFragment().apply {
            arguments = Bundle().apply {
                putLong(EXTRA_HOTEL_ID, id)
            }
        }
    }
}