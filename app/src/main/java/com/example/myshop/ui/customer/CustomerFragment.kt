package com.example.myshop.ui.customer

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.myshop.R
import com.example.myshop.databinding.FragmentCustomerBinding
import com.example.myshop.model.Customer
import com.example.myshop.ui.disconnect.State
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CustomerFragment : Fragment() {

    val locationPermissionRequest = registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissions ->
        getLocationPermission(permissions)
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var binding: FragmentCustomerBinding
    val vModel: CustomerViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_customer, container, false)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_home).visibility = View.VISIBLE
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkRegister()
        initViews()
    }

    private fun checkRegister() {
        vModel.checkRegistered(requireContext())
        vModel.customer.observe(viewLifecycleOwner){
            if (it.id != null){
                findNavController().navigate(R.id.action_customerFragment_to_customerRegisteredFragment)
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun initViews() {
        clickOnGetLocation()
        observeState()
        binding.btnRegister.setOnClickListener {
            if (checkedRegisterField()) {

                vModel.register(
                    Customer(
                        null,
                        binding.emailEdtRegister.text.toString(),
                        binding.nameEdtRegister.text.toString(),
                        binding.usernameEdtRegister.text.toString(),
                        binding.passwordEdtRegister.text.toString(),
                    ),requireContext()
                )
            }
        }
        binding.btnSignIn.setOnClickListener {
            binding.txvCustomerMessage.text = ""
            if (!binding.idEdtSignIn.text.isNullOrBlank() &&
                    !binding.passwordEdtSignIn.text.isNullOrBlank()){
                vModel.login(
                    binding.idEdtSignIn.text.toString().toInt(),
                    binding.passwordEdtSignIn.text.toString()
                    ,requireContext()
                )
            }
        }
        binding.btnRegisterCard.setOnClickListener {
            binding.txvCustomerMessage.text = ""
            binding.llRegister.visibility = View.VISIBLE
            binding.llSignin.visibility = View.GONE
            binding.btnRegisterCard.textSize = 18f
            binding.btnSigninCard.textSize = 12f
        }
        binding.btnSignIn.isEnabled = false
        binding.btnSigninCard.setOnClickListener {
            binding.txvCustomerMessage.text = ""
            binding.llRegister.visibility = View.GONE
            binding.llSignin.visibility = View.VISIBLE
            binding.btnSigninCard.textSize = 18f
            binding.btnRegisterCard.textSize = 12f
        }
        vModel.registerMessage.observe(viewLifecycleOwner){
            binding.txvCustomerMessage.text = it
        }
    }

    private fun checkedRegisterField(): Boolean {
        var result = false
        when {
            binding.nameEdtRegister.text.isNullOrBlank() -> {
                binding.nameEdtFieldRegister.error = getString(R.string.emptyField)
            }
            binding.usernameEdtRegister.text.isNullOrBlank() -> {
                binding.usernameEdtFieldRegister.error = getString(R.string.emptyField)
            }
            binding.emailEdtRegister.text.isNullOrBlank() -> {
                binding.emailEdtFieldRegister.error = getString(R.string.emptyField)
            }
            binding.passwordEdtRegister.text.isNullOrBlank() -> {
                binding.passwordEdtFieldRegister.error = getString(R.string.emptyField)
            }
            else -> result = true
        }
       return  result
    }

    private fun observeState() {
        vModel.state.observe(viewLifecycleOwner){
            when (it){
                State.LOADING -> {
                    binding.txvCustomerMessage.visibility = View.INVISIBLE
                    binding.progressBarProfile.visibility = View.VISIBLE
                }
                State.SUCCESS -> {
                    binding.txvCustomerMessage.visibility = View.VISIBLE
                    binding.progressBarProfile.visibility = View.GONE
                }
                State.FAILED -> {
                    binding.txvCustomerMessage.visibility = View.VISIBLE
                    binding.progressBarProfile.visibility = View.INVISIBLE
                    vModel.showDefaultDialog(
                        requireContext() , "خطا!" ,
                        vModel.message.value + " مشکلی پیش آمده: ",
                        "متوجه شدم"
                    )
                }
            }
        }
    }


    fun getLocationPermission(permissions : Map<String , Boolean>) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {


//            val locationPermissionRequest = registerForActivityResult(
//                ActivityResultContracts.RequestMultiplePermissions()
//            ) { permissions ->
                when {
                    permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                        Toast.makeText(requireContext(), "fine", Toast.LENGTH_SHORT).show()
                        getUserLocation()
                    }
                    permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                        Toast.makeText(requireContext(), "corse", Toast.LENGTH_SHORT).show()
                        getUserLocation()
                    }
                    else -> {
                        // No location access granted.
                    }
//                }
            }

            // ...

            // Before you perform the actual permission request, check whether your app
            // already has the permissions, and whether your app needs to show a permission
            // rationale dialog. For more details, see Request permissions.
            locationPermissionRequest.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }

    }

    private fun getUserLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                location?.let {
                    Toast.makeText(requireContext() , "lat"+ it.latitude + " lang : " + it.longitude, Toast.LENGTH_SHORT).show()
                }
            }
    }


    private fun clickOnGetLocation() {

        binding.imvGetLocation1.setOnClickListener {
            locationPermissionRequest
//            getLocationPermission()
        }
        binding.imvGetLocation2.setOnClickListener {
//            getLocationPermission()
        }
    }

}