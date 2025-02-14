import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.oladoc.database.UserProfile
import com.google.firebase.database.FirebaseDatabase

class ProfilesViewModel : ViewModel() {
    private val _profiles = MutableLiveData<List<UserProfile>>()
    val profiles: LiveData<List<UserProfile>> = _profiles

    init {
        loadProfiles()
    }

    private fun loadProfiles() {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("profiles")

        myRef.get().addOnSuccessListener { dataSnapshot ->
            val newProfiles = mutableListOf<UserProfile>()
            dataSnapshot.children.forEach { snapshot ->
                snapshot.getValue(UserProfile::class.java)?.let { userProfile ->
                    newProfiles.add(userProfile)
                }
            }
            _profiles.postValue(newProfiles)
        }.addOnFailureListener {
            // Handle errors here
        }
    }
}
