package com.proy.easywork.presentation.principal.view.fragments

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.proy.easywork.R
import com.proy.easywork.databinding.FragmentMapsBinding

class MapsFragment : Fragment(), GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener {
    private lateinit var binding: FragmentMapsBinding
    private val RQ_PERMISSIONS_REQUEST = 25
    private val callback = OnMapReadyCallback { googleMap ->

        googleMap.uiSettings.isMyLocationButtonEnabled=true
        googleMap.uiSettings.isCompassEnabled=true
        //val sydney = LatLng(-34.0, 151.0)
        //googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return@OnMapReadyCallback
        }
        googleMap.isMyLocationEnabled=true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentMapsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLocation()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)


        binding.tvAtras.setOnClickListener {
            Navigation.findNavController(it).popBackStack()
        }
    }

    override fun onMapClick(p0: LatLng) {
        Toast.makeText(context, " "+p0.latitude.toString(), Toast.LENGTH_LONG).show()
    }

    override fun onMapLongClick(p0: LatLng) {
        TODO("Not yet implemented")
    }

    private fun setLocation(){
        val mediaPermissions =
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        if (!checkPermissions(requireContext(), mediaPermissions)) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                mediaPermissions,
                RQ_PERMISSIONS_REQUEST
            )
        }
    }

    fun checkPermissions(context: Context, permissions: Array<String>): Boolean {
        return permissions.none { ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED }
    }
}