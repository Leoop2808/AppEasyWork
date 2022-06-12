package com.proy.easywork.presentation.principal.view.fragments

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.proy.easywork.R
import com.proy.easywork.databinding.FragmentMapsBinding
import java.io.IOException
import java.util.*


class MapsFragment : Fragment(){
    private lateinit var binding: FragmentMapsBinding
    private val RQ_PERMISSIONS_REQUEST = 25
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var mLocationRequest: LocationRequest? = null
    private var locationCallback: LocationCallback? = null
    private val REQUEST_LOCATION = 12
    private val UPDATE_INTERVAL = (60 * 1000).toLong() /* 60 secs */
    private val FASTEST_INTERVAL = 20000L /* 20 sec */
    private var actualLocation: Location? = null
    private var markerActual : Marker?=null
    private var mDireccion=""
    private var mLatLng: LatLng?=null
    private val callback = OnMapReadyCallback { googleMap ->

        googleMap.uiSettings.isMyLocationButtonEnabled=true
        googleMap.uiSettings.isCompassEnabled=true

        maps=googleMap
        googleMap.setOnMapClickListener {
            if(markerActual!=null) {
                markerActual!!.remove()
            }
            val markerOptions = MarkerOptions()
            markerOptions.position(it)
            markerOptions.title("Actual")
            markerActual= googleMap.addMarker(markerOptions)
            setLocation(it)

        }
    }

    private var maps : GoogleMap?=null

    fun setLocation(loc: LatLng) {
        //Obtener la direccion de la calle a partir de la latitud y la longitud
        if (loc.latitude != 0.0 && loc.longitude != 0.0) {
            try {
                val geocoder = Geocoder(context, Locale.getDefault())
                val list: List<Address> = geocoder.getFromLocation(
                    loc.latitude, loc.longitude, 1
                )
                if (!list.isEmpty()) {
                    val DirCalle: Address = list[0]
                    mLatLng= loc
                    mDireccion=DirCalle.getAddressLine(0)
                    binding.etDireccion.setText(DirCalle.getAddressLine(0))
                    binding.btnContinuar.visibility=View.VISIBLE
                }
            } catch (e: IOException) {
                e.printStackTrace()

                Log.e("error", e.message.toString())
            }
        }
    }
    override fun onStop() {
        super.onStop()
        if (fusedLocationClient != null) {
            if (locationCallback != null) {
                fusedLocationClient!!.removeLocationUpdates(locationCallback)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        checkLocation()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding= FragmentMapsBinding.inflate(inflater, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pb.visibility = View.VISIBLE
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        setUpEvents()
    }

    private fun setUpEvents() {
        binding.imgBuscar.setOnClickListener {
            if(binding.etDireccion.text.isNullOrEmpty()){
                showMessage("Escriba una dirección")
            }else{
                try {
                    val geocoder = Geocoder(context, Locale.getDefault())
                    val list: List<Address> = geocoder.getFromLocationName(binding.etDireccion.text.toString(),1)
                    if (!list.isEmpty()) {
                        val DirCalle: Address = list[0]
                        mLatLng=LatLng(DirCalle.latitude,DirCalle.longitude)
                        mDireccion=DirCalle.getAddressLine(0)
                        binding.etDireccion.setText(DirCalle.getAddressLine(0))
                        binding.btnContinuar.visibility=View.VISIBLE

                        val markerOptions = MarkerOptions()
                        markerOptions.position(mLatLng!!)
                        markerOptions.title("Actual")
                        markerActual= maps!!.addMarker(markerOptions)
                        maps!!.moveCamera(CameraUpdateFactory.newLatLng(mLatLng))
                        maps!!.animateCamera(CameraUpdateFactory.zoomTo( 18.0f ))

                    }
                } catch (e: IOException) {
                    e.printStackTrace()

                    Log.e("error", e.message.toString())
                }
            }

        }

        binding.btnContinuar.setOnClickListener {
            val b = bundleOf(Pair("direccion",mDireccion), Pair("latitud",mLatLng?.latitude), Pair("longitud",mLatLng?.longitude), Pair("codCategoria", arguments?.getString("codCategoria")))
            Navigation.findNavController(it).navigate(R.id.action_mapsFragment_to_formularioFragment,b)
        }
    }



    fun checkPermissions(context: Context, permissions: Array<String>): Boolean {
        return permissions.none { ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED }
    }


    private fun showAlertLocation() {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setMessage("La configuración de su ubicación está desactivada, habilite la ubicación para usar esta aplicación")
        dialog.setPositiveButton("Ajustes") { dialog1: DialogInterface?, id: Int ->
            val myIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(myIntent)
        }
        dialog.setNegativeButton(
            "Salir"
        ) { dialog12: DialogInterface?, id: Int -> checkLocation() }
        dialog.setCancelable(false)
        dialog.show()
    }

    private fun checkLocation() {
        val PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                PERMISSIONS,
                REQUEST_LOCATION
            )
        } else {
            val manager =
                requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                showAlertLocation()
            } else {
                startLocationUpdates()
            }
        }
    }


    private fun startLocationUpdates() {
        // Create the location request to start receiving updates
        mLocationRequest = LocationRequest()
        mLocationRequest?.let {
            it.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            it.interval = UPDATE_INTERVAL
            it.setFastestInterval(FASTEST_INTERVAL)
        }


        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest)
        val locationSettingsRequest = builder.build()

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        val settingsClient = LocationServices.getSettingsClient(requireActivity())
        settingsClient.checkLocationSettings(locationSettingsRequest)

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        val PERMISSIONS = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                PERMISSIONS,
                REQUEST_LOCATION
            )
        } else {
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    actualLocation = locationResult.lastLocation
                    if(actualLocation!=null){
                        if(maps!=null){
                            if(markerActual==null){
                                val actualLatLng = LatLng(actualLocation!!.latitude, actualLocation!!.longitude)
                                val markerOptions = MarkerOptions()
                                markerOptions.position(actualLatLng)
                                markerOptions.title("Actual")
                                markerActual= maps!!.addMarker(markerOptions)
                                maps!!.moveCamera(CameraUpdateFactory.newLatLng(actualLatLng))
                                maps!!.animateCamera(CameraUpdateFactory.zoomTo( 18.0f ))
                                setLocation(actualLatLng)

                                binding.pb.visibility = View.GONE
                            }
                        }
                    }
                }
            }
            fusedLocationClient!!.requestLocationUpdates(
                mLocationRequest,
                locationCallback,
                Looper.myLooper()
            )
        }
    }


    private fun showMessage(message: String) {
        mShowMessageSnackBar(message, binding.clContainer)
    }

    private fun mShowMessageSnackBar(error: String, snackContainer: View) {
        Snackbar.make(snackContainer, error, Snackbar.LENGTH_SHORT).show()
    }
}