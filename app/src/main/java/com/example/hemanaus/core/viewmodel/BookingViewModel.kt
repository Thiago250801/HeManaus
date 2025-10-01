package com.example.hemanaus.core.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.hemanaus.core.model.Booking
import com.example.hemanaus.core.model.User

class BookingViewModel : ViewModel() {

    var user = mutableStateOf<User?>(null)
        private set

    var booking = mutableStateOf<Booking?>(null)
        private set

    fun setUser(user: User) {
        this.user.value = user
    }

    fun setBooking(booking: Booking) {
        this.booking.value = booking
    }

    fun clearBooking() {
        booking.value = null
    }
}
