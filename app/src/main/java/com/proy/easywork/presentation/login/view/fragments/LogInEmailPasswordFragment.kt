package com.proy.easywork.presentation.login.view.fragments

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.gms.location.*
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.messaging.FirebaseMessaging
import com.proy.easywork.BuildConfig
import com.proy.easywork.R
import com.proy.easywork.data.datasource.preferences.MDefaultSharedPref
import com.proy.easywork.data.datasource.storage.MDataInjection
import com.proy.easywork.data.model.request.RQDispositivo
import com.proy.easywork.databinding.FragmentLogInEmailPasswordBinding
import com.proy.easywork.domain.repositories.LoginRepository
import com.proy.easywork.presentation.login.viewmodel.LoginViewModel
import com.proy.easywork.presentation.principal.view.activities.PrincipalActivity

class LogInEmailPasswordFragment : Fragment() {
    private val sp: MDefaultSharedPref = MDataInjection.instance.providePreferences() as MDefaultSharedPref
    private lateinit var binding: FragmentLogInEmailPasswordBinding

    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var mLocationRequest: LocationRequest? = null
    private var locationCallback: LocationCallback? = null
    private val REQUEST_LOCATION = 12
    private val UPDATE_INTERVAL = (60 * 1000).toLong() /* 60 secs */
    private val FASTEST_INTERVAL = 20000L /* 20 sec */
    private var actualLocation: Location? = null

    private val viewModel by viewModels<LoginViewModel> {
        LoginViewModel.LoginModelFactory(LoginRepository(activity?.application!!))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        binding = FragmentLogInEmailPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        checkLocation()
    }

    override fun onStop() {
        super.onStop()
        if (fusedLocationClient != null) {
            if (locationCallback != null) {
                fusedLocationClient!!.removeLocationUpdates(locationCallback)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUI()
        setUpEvents()
    }

    private fun setUpEvents() {

        binding.imgBackArrow.setOnClickListener {
            view?.let {
                Navigation.findNavController(it).popBackStack()
            }
        }

        binding.btnRegistrate.setOnClickListener {
            view?.let {
                Navigation.findNavController(it).navigate(R.id.action_logInEmailPasswordFragment_to_loginCodePhoneFragment)
            }
        }

        binding.btnIniciarSesion.setOnClickListener {
            if(validateField()){
                viewModel.login(binding.etCorreo.text.toString(), binding.edContrasenia.text.toString())
            }
        }

        binding.btnOlvidasteContra.setOnClickListener {
            view?.let {
                Navigation.findNavController(it).navigate(R.id.action_logInEmailPasswordFragment_to_passwordRecoveryEmailFragment)
            }
        }
    }

    private fun setUpUI() {
        viewModel.login.observe(viewLifecycleOwner){
            viewModel.listarMaestros()
        }

        viewModel.cargaMaestros.observe(viewLifecycleOwner){
            registrarDispositivo()
        }
        viewModel.onMessageError.observe(viewLifecycleOwner){
            it?.let {
                showMessage(it)
            }
        }

        viewModel.onMessageSuccesful.observe(viewLifecycleOwner){
            context?.let { it1 -> startActivity(PrincipalActivity().newIntent(it1)) }
        }

        viewModel.isViewLoading.observe(viewLifecycleOwner) {
            it.let {
                if (it) {
                    binding.pb.visibility = View.VISIBLE
                } else {
                    binding.pb.visibility = View.GONE
                }
            }
        }
    }

    private fun registrarDispositivo() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@addOnCompleteListener
                }
                val tokenFCM: String = task.result
                sp.saveTokenFCM(tokenFCM)
                if(actualLocation!=null){
                    actualLocation?.let {
                        viewModel.registrarDispositivo(RQDispositivo(tokenFCM,BuildConfig.VERSION_CODE.toString() ,BuildConfig.VERSION_NAME,it.latitude,it.latitude))
                    }

                }else{
                    viewModel.registrarDispositivo(RQDispositivo(tokenFCM,BuildConfig.VERSION_CODE.toString() ,BuildConfig.VERSION_NAME,0.0,0.0))
                }

            }
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

    private fun validateField():Boolean{
        if (binding.etCorreo.text.isNullOrEmpty()){
            showMessage("Ingrese usuario")
            return false
        }
        if (binding.edContrasenia.text.isNullOrEmpty()){
            showMessage("Ingrese contraseña")
            return false
        }

        return true
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


    private fun startLocationUpdates() {
        // Create the location request to start receiving updates
        mLocationRequest = LocationRequest()
        mLocationRequest?.let {
            it.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            it.interval = UPDATE_INTERVAL
            it.setFastestInterval(FASTEST_INTERVAL)
        }


        // Create LocationSettingsRequest object using location request
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