package com.example.myshop.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myshop.R
import com.example.myshop.databinding.FragmentHomeBinding
import com.example.myshop.ui.adapters.HomeListsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    val vModel: HomeViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkConnectionInternet()
        initViews()



    }

    private fun checkConnectionInternet() {
        vModel.checkForInternet(requireContext())
        vModel.isConnected.observe(viewLifecycleOwner){
            if (!it)
                findNavController().navigate(R.id.action_homeFragment_to_disconnectBlankFragment)
        }
    }



    private fun initViews() {
        val lastAdapter=HomeListsAdapter{
            goToDetail(it.id)}
        val mostSeenAdapter=HomeListsAdapter{ goToDetail(it.id) }
        val favoriteAdapter=HomeListsAdapter{ goToDetail(it.id) }
        binding.lifecycleOwner = viewLifecycleOwner
        binding.lastAdaptor = lastAdapter
        binding.mostSeenAdaptor = mostSeenAdapter
        binding.favoriteAdaptor = favoriteAdapter


        vModel.recentProducts.observe(viewLifecycleOwner){
            lastAdapter.submitList(it)
        }

        vModel.mostSeenProducts.observe(viewLifecycleOwner){
            mostSeenAdapter.submitList(it)
        }

        vModel.favoriteProducts.observe(viewLifecycleOwner){
            favoriteAdapter.submitList(it)
        }

        binding.fabGoToListHome.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_categoryFragment)
        }
    }

    private fun goToDetail(id: Int) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(id))
    }

}