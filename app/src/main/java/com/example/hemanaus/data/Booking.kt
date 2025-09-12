package com.example.hemanaus.data
import java.time.LocalTime
import java.time.LocalDate

data class Booking(
    val id: Long = System.currentTimeMillis(),
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val selectedDate: LocalDate = LocalDate.now(),
    val selectedTime: LocalTime = LocalTime.now(),
    val donationType: DonationType = DonationType.BLOOD,
    val status: BookingStatus = BookingStatus.SCHEDULED,
    val location: String = "Hemoam/AM - Manaus"
)

enum class DonationType(val displayName: String) {
    BLOOD("Doação de Sangue"),
    PLATELETS("Doação de Plaquetas"),
    PLASMA("Doação de Plasma")
}
enum class BookingStatus {
    SCHEDULED,
    COMPLETED,
    CANCELLED
}

data class BloodStock(
    val type: String,
    val level: Int,
    val status: StockStatus
)

enum class StockStatus {
    CRITICAL,
    LOW,
    NORMAL
}

data class Impact(
    val livesSaved: Int = 4,
    val totalDonations: Int = 3,
    val nextEligibleDate: LocalDate = LocalDate.now().plusDays(60),
    val rank: String = "Herói de Bronze",
    val points: Int = 75
)

data class Achievement(
    val title: String,
    val description: String,
    val unlocked: Boolean
)

data class DonationHistory(
    val date: LocalDate,
    val type: String,
    val location: String,
    val impact: Int
)