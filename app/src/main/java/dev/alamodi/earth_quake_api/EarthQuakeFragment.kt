package dev.alamodi.earth_quake_api

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.alamodi.earth_quake_api.models.Features
import java.math.RoundingMode
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

class EarthQuakeFragment : Fragment() {
    lateinit var earthQuakeViewModel: EarthQuakeViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: QuakeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        earthQuakeViewModel =
            ViewModelProviders.of(this).get(EarthQuakeViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_earth_quake, container, false)
        recyclerView = view.findViewById(R.id.quake_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        earthQuakeViewModel.quakesLiveData.observe(
            this,
            Observer { features ->
//                Toast.makeText(context, features[0].id, Toast.LENGTH_LONG).show()
                updateUI(features)
            })
    }

    private fun updateUI(features: List<Features>) {
        adapter = QuakeAdapter(features)
        recyclerView.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() = EarthQuakeFragment()
    }

    private class QuakeHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        private val magView = itemView.findViewById(R.id.mag_view) as TextView
        private val countryView = itemView.findViewById(R.id.country_view) as TextView
        private val placeView = itemView.findViewById(R.id.place_view) as TextView
        private val dateView = itemView.findViewById(R.id.date_view) as TextView
        private val timeView = itemView.findViewById(R.id.time_view) as TextView
        private var p1: Double = 0.0
        private var p2: Double = 0.0
        private var p: Double = 0.0

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(features: Features) {
            setMag(features.properties.mag)
            setCountryAndPlace(features.properties.place)
            setDateAndTime(features.properties.time)
            getCoordinates(features.geometry.coordinates)
        }

        fun setMag(mag: Double) {
            magView.apply {
                text = mag.toBigDecimal().setScale(1, RoundingMode.CEILING).toString()
                when {
                    mag < 4.0 -> setBackgroundResource(R.drawable.mag_shap_low)
                    mag < 5.0 -> setBackgroundResource(R.drawable.mag_shap_med)
                    mag <= 6.0 -> setBackgroundResource(R.drawable.mag_shap_med2)
                    mag in 6.0..10.0 -> setBackgroundResource(R.drawable.mag_shap_high)
                }
            }
        }

        fun setCountryAndPlace(str: String) {
            val parts: List<String> = str.split("of")
            placeView.text = """${parts[0]}OF"""
            countryView.text = "${parts[1]}"
        }

        fun setDateAndTime(dateTime: Long) {
            val calendar = Calendar.getInstance()
            calendar.time = Date(dateTime)

            val stringDate = "${calendar.get(Calendar.YEAR)}-" +
                    "${calendar.get(Calendar.MONTH)}-" +
                    "${calendar.get(Calendar.DAY_OF_MONTH)}"

            val stringTime = "${calendar.get(Calendar.HOUR_OF_DAY)}:" +
                    "${calendar.get(Calendar.MINUTE)}"

            dateView.text = stringDate
            timeView.text = stringTime
        }

        fun getCoordinates(coordinates: List<Double>) {
            p1 = coordinates[0]
            p1 = coordinates[1]
            p1 = coordinates[2]
        }

        override fun onClick(p0: View?) {
            val uri = Uri.parse("geo:$p1,$p2")
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = uri
            }
            ContextCompat.startActivity(itemView.context, intent, null)
        }
    }

    private class QuakeAdapter(private val featuresItems: List<Features>) :
        RecyclerView.Adapter<QuakeHolder>() {
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): QuakeHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.quake_list_item, parent, false)
            return QuakeHolder(view)
        }

        override fun getItemCount(): Int = featuresItems.size

        override fun onBindViewHolder(holder: QuakeHolder, position: Int) {
            val features = featuresItems[position]
            holder.bind(features)
        }

        interface CallBack {
            fun onItemClicked(intent: Intent)
        }
    }
}