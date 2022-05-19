package com.proy.easywork.presentation.login.view

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
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.facebook.*
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.proy.easywork.BuildConfig.GoogleTokenId
import com.proy.easywork.R
import com.proy.easywork.data.model.request.RQAuthenticationFacebook
import com.proy.easywork.data.model.request.RQAuthenticationGoogle
import com.proy.easywork.databinding.FragmentLoginBinding
import com.proy.easywork.domain.repositories.LoginRepository
import com.proy.easywork.presentation.login.viewmodel.LoginViewModel

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    companion object{
        const val RC_SIGN_IN = 100
    }

    private val callbackManager = CallbackManager.Factory.create()
    private lateinit var  mGoogleSignInClient : GoogleSignInClient

    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var mLocationRequest: LocationRequest? = null
    private var locationCallback: LocationCallback? = null
    private val REQUEST_LOCATION = 12
    private val UPDATE_INTERVAL = (60 * 1000).toLong() /* 60 secs */
    private val FASTEST_INTERVAL = 20000L /* 20 sec */
    private var actualLocation: Location? = null
    private lateinit var auth: FirebaseAuth

    private val viewModel by viewModels<LoginViewModel> {
        LoginViewModel.LoginModelFactory(LoginRepository(activity?.application!!))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        //FacebookSdk.sdkInitialize(FacebookSdk.getApplicationContext())
        //AppEventsLogger.activateApp(requireActivity().application)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        binding = FragmentLoginBinding.inflate(inflater, container, false)
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
        auth = Firebase.auth


    }

    private fun setUpUI() {

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(GoogleTokenId)
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        viewModel.onMessageError.observe(viewLifecycleOwner){
            it?.let {
                showMessage(it)
            }
        }

        viewModel.onMessageSuccesful.observe(viewLifecycleOwner){

        }

        viewModel.registerCompletedFacebook.observe(viewLifecycleOwner){
            view?.let {
                Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_loginCodePhoneFbGoogleFragment)
            }
        }

        viewModel.registerCompletedGoogle.observe(viewLifecycleOwner){
            view?.let {
                Navigation.findNavController(it).navigate(R.id.action_loginFragment_to_loginCodePhoneFbGoogleFragment2)
            }
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

    private fun setUpEvents() {
        binding.btnTelefono.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_create_account_loginFragment_to_loginCodePhoneFragment)
        }

        binding.btnLogIn.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_log_in_loginFragment_to_logInEmailPasswordFragment)
        }

        binding.btnFb.setOnClickListener {

            /*LoginManager.getInstance().logInWithReadPermissions(this, listOf("email"))
            LoginManager.getInstance().registerCallback(callbackManager, object  :
                FacebookCallback<LoginResult> {
                override fun onCancel() {
                }

                override fun onError(error: FacebookException) {
                    error.printStackTrace()
                    Log.e(":)", "onError ${error.message}")
                }

                override fun onSuccess(result: LoginResult) {
                    result?.let{


                        // App code
                        Log.e(":)", "loginResult ${it.toString()}")
                        val accessToken = AccessToken.getCurrentAccessToken()
                        actualLocation?.let {
                            viewModel.loginFacebook(RQAuthenticationFacebook(accessToken!!.token, it.latitude, it.longitude))
                        }
                        val isLoggedIn = accessToken != null && !accessToken.isExpired
                        Log.e(":)", "isLoggedIn $isLoggedIn")
                        Log.e(":)", "accessToken ${accessToken?.token}")

                    }
                }

            })*/
            //Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_loginCodePhoneFbGoogleFragment)
        }

        binding.btnGoogle.setOnClickListener {
            startActivityForResult(mGoogleSignInClient.signInIntent, RC_SIGN_IN)
            // Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_loginCodePhoneFbGoogleFragment2)
        }

        binding.btnCreateAccount.setOnClickListener {

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        callbackManager.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            // Signed in successfully, show authenticated UI.
            Log.e(":)","handleSignInResult account -> ${account.toString()}")
            Log.e(":)","handleSignInResult idToken -> ${account?.idToken}")
            Log.e(":)","handleSignInResult id -> ${account?.id}")
            Log.e(":)","handleSignInResult account -> ${account?.account.toString()}")
            Log.e(":)","handleSignInResult email -> ${account?.email}")
            Log.e(":)","handleSignInResult displayName -> ${account?.displayName}")
            Log.e(":)","handleSignInResult photoUrl -> ${account?.photoUrl}")
            Log.e(":)","handleSignInResult serverAuthCode -> ${account?.serverAuthCode}")
            Log.e(":)","handleSignInResult isExpired -> ${account?.isExpired}")

            val idToken: String = account!!.idToken.toString()

            Log.e(":)","idToken-> $idToken")
            actualLocation?.let {
                viewModel.loginGoogle(RQAuthenticationGoogle(idToken, it.latitude, it.longitude))
            }

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e(":)", "signInResult:failed code=" + e.statusCode)
        }
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