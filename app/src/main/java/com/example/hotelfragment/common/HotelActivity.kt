package com.example.hotelfragment.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import com.example.hotelfragment.details.HotelDetailsActivity
import com.example.hotelfragment.R
import kotlinx.android.synthetic.main.activity_main.*
import com.example.hotelfragment.model.data.Hotel
import com.example.hotelfragment.details.HotelDetailsFragment
import com.example.hotelfragment.form.HotelFormFragment
import com.example.hotelfragment.list.HotelListFragment

class HotelActivity :
        AppCompatActivity(), HotelListFragment.OnHotelClickListener, SearchView.OnQueryTextListener, MenuItem.OnActionExpandListener, HotelFormFragment.OnHotelSavedListener {

    private var lastSearchTerm: String = ""
    private var searchView: SearchView? = null

    private val listFragment: HotelListFragment by lazy {
        supportFragmentManager.findFragmentById(R.id.fragmentList) as HotelListFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fabAdd.setOnClickListener {
            listFragment.hideDeleteMode()
            HotelFormFragment.newInstance().open(supportFragmentManager)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState?.putString(EXTRA_SEARCH_TERM, lastSearchTerm)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        lastSearchTerm = savedInstanceState?.getString(EXTRA_SEARCH_TERM) ?: ""
    }

    //private fun isTablet() = findViewById<View>(R.id.details) != null
    private fun isTablet() = resources.getBoolean(R.bool.tablet)
    private fun isSmartphone() = resources.getBoolean(R.bool.smartphone)

    override fun onHotelClick(hotel: Hotel) {
        when {
            isTablet() -> {
                showDetailsFragment(hotel.id)
            }
            isSmartphone() -> {
                showDetailsActivity(hotel.id)
            }
            else -> {
                showDetailsActivity(hotel.id)
            }
        }
    }

    private fun showDetailsActivity(hotelId: Long) {
        HotelDetailsActivity.open(this, hotelId)
    }

    /*Para tabletes*/
    private fun showDetailsFragment(hotelId: Long) {
        /*Recria o menu para que nao seja notificado com texto vazio*/
        searchView?.setOnQueryTextListener(null)

        val fragment = HotelDetailsFragment.newInstance(hotelId)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.details, fragment, HotelDetailsFragment.TAG_DETAILS)
                .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.hotel, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        searchItem?.setOnActionExpandListener(this)
        searchView = searchItem?.actionView as SearchView
        searchView?.queryHint = getString(R.string.hint_search)
        searchView?.setOnQueryTextListener(this)

        if (lastSearchTerm.isNotEmpty()) {
            Handler().post {
                val query = lastSearchTerm
                searchItem.expandActionView()
                searchView?.setQuery(query, true)
                searchView?.clearFocus()
            }
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            R.id.action_info -> AboutDialogFragment().show(supportFragmentManager, TAG_ABOUT)
            //R.id.action_new -> HotelFormFragment().open(supportFragmentManager)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(p0: String?) = true

    override fun onQueryTextChange(newText: String?): Boolean {
        lastSearchTerm = newText ?: ""
        listFragment.search(lastSearchTerm)
        return true
    }

    override fun onMenuItemActionExpand(p0: MenuItem?) = true /*Para expandir a view*/

    override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
        lastSearchTerm = ""
        listFragment.clearSearch() /*Para voltar ao normal*/
        return true
    }

    override fun onHotelSaved(hotel: Hotel) {
        listFragment.search(lastSearchTerm)
    }

    companion object {
        private val EXTRA_SEARCH_TERM = "lastSearch"
        private val TAG_ABOUT = "about"
    }
}