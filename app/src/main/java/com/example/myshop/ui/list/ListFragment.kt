package com.example.myshop.ui.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myshop.R
import com.example.myshop.databinding.FragmentListBinding
import com.example.myshop.adapters.HomeListsAdapter
import com.example.myshop.adapters.Orientation
import com.example.myshop.ui.disconnect.State
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {

    private val args: ListFragmentArgs by navArgs()
    lateinit var binding: FragmentListBinding
    val vModel: ListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_list, container, false)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_home).visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkConnectionInternet()
        initView()

    }

    private fun initView() {
        val categoryId = args.categoryArg
        vModel.getProductList(categoryId)

        val adapter = HomeListsAdapter(Orientation.VERTICAL){
            goToDetail(it.id)
        }
        binding.recyclerList.adapter= adapter

        vModel.productList.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
        observeState()
    }

    private fun observeState() {
        vModel.state.observe(viewLifecycleOwner){
            when (it){
                State.LOADING -> {
                    binding.recyclerList.visibility = View.INVISIBLE
                    binding.progressBarList.visibility = View.VISIBLE
                    binding.imvProblemList.visibility = View.GONE
                }
                State.SUCCESS -> {
                    binding.recyclerList.visibility = View.VISIBLE
                    binding.progressBarList.visibility = View.GONE
                    binding.imvProblemList.visibility = View.GONE
                }
                State.FAILED -> {
                    binding.recyclerList.visibility = View.INVISIBLE
                    binding.progressBarList.visibility = View.INVISIBLE
                    binding.imvProblemList.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun checkConnectionInternet() {
        vModel.checkForInternet(requireContext())
        vModel.isConnected.observe(viewLifecycleOwner){
            if (!it)
                findNavController().navigate(R.id.action_listFragment_to_disconnectBlankFragment)
        }
    }

    private fun goToDetail(id: Int) {
        findNavController().navigate(ListFragmentDirections.actionListFragmentToProductDetailFragment(id))
    }

}