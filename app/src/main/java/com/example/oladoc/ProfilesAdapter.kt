import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.oladoc.R
import com.example.oladoc.database.UserProfile

class ProfilesAdapter(private var profiles: List<UserProfile>) :
    RecyclerView.Adapter<ProfilesAdapter.ProfileViewHolder>() {

    class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val ageTextView: TextView = itemView.findViewById(R.id.ageTextView)

        fun bind(profile: UserProfile) {
            nameTextView.text = profile.name
            ageTextView.text = profile.age.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.profile_item, parent, false)
        return ProfileViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.bind(profiles[position])
    }

    override fun getItemCount(): Int = profiles.size

    fun updateProfiles(newProfiles: List<UserProfile>) {
        profiles = newProfiles
        notifyDataSetChanged() // Notify any registered observers that the data set has changed.
    }
}
