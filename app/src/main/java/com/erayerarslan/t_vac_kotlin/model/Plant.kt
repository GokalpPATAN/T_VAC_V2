package com.farukayata.t_vac_kotlin.model

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import kotlinx.parcelize.Parcelize
import kotlinx.coroutines.tasks.await

@Parcelize
data class Plant(
    val name: String = "",
    val category: String = "",
    val temperatureRange: List<Int> = emptyList(),
    val humidityRange: List<Int> = emptyList(),
    val features: String = "",
    val img: String = "",
    val plantingInfo: String = "",
    val locationNote: String = ""
) : Parcelable {

    fun isSuitable(temperature: Int, humidity: Int): Boolean {
        return temperature in temperatureRange && humidity in humidityRange
    }

    fun nameFilter(query: String): Boolean {
        return name.contains(query, ignoreCase = true)
    }

    companion object {
        // DatabaseReference’inizi burada tanımlayın
        private val plantsRef: DatabaseReference =
            FirebaseDatabase.getInstance().getReference("plants")

        /**
         * 1) Callback tabanlı, tek seferlik liste çekme:
         */
        fun fetchAll(
            onResult: (List<Plant>) -> Unit,
            onError: (DatabaseError) -> Unit
        ) {
            plantsRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = snapshot.children.mapNotNull {
                        it.getValue(Plant::class.java)
                    }
                    onResult(list)
                }

                override fun onCancelled(error: DatabaseError) {
                    onError(error)
                }
            })
        }

        fun fetchAllAsLiveData(): LiveData<List<Plant>> {
            val liveData = MutableLiveData<List<Plant>>()
            plantsRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = snapshot.children.mapNotNull {
                        it.getValue(Plant::class.java)
                    }
                    liveData.postValue(list)
                }

                override fun onCancelled(error: DatabaseError) {
                    liveData.postValue(emptyList())
                }
            })
            return liveData
        }

        suspend fun fetchAllSuspend(): List<Plant> {
            val snapshot = plantsRef.get().await()
            return snapshot.children.mapNotNull {
                it.getValue(Plant::class.java)
            }
        }
    }
}
