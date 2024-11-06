package loki.edu.yogaclassadmin.repository.firestore


import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import loki.edu.yogaclassadmin.model.Booking

class BookingRepository(private val firestore: FirebaseFirestore) {

    private val bookingCollection = firestore.collection("bookings")

    suspend fun addBooking(booking: Booking): Boolean {
        return try {
            bookingCollection.document(booking.id).set(booking).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getBookingById(bookingId: String): Booking? {
        return try {
            bookingCollection.document(bookingId).get().await().toObject(Booking::class.java)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun updateBooking(booking: Booking): Boolean {
        return try {
            bookingCollection.document(booking.id).set(booking).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun deleteBooking(bookingId: String): Boolean {
        return try {
            bookingCollection.document(bookingId).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }
}
