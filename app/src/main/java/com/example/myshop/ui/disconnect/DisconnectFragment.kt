package com.example.myshop.ui.disconnect

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.example.myshop.R
import com.example.myshop.databinding.FragmentDisconnectBinding
import com.example.myshop.databinding.FragmentHomeBinding
import com.example.myshop.ui.home.HomeViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class DisconnectBlankFragment : Fragment() {

    val vModel: HomeViewModel by viewModels()
    lateinit var binding: FragmentDisconnectBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_disconnect, container, false)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_home).visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRetryConnection.setOnClickListener {
//            if (vModel.checkForInternet(requireContext())){
                requireActivity().onBackPressed()
//            }
        }
    }
}