package com.cinemaworld.views.base_for_cinema

interface ViewLayout {

    fun responseEmpty() {}
    fun showViewLoading() {}
    fun showErrorScreen(error: String?) {}


}
