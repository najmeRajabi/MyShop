package com.example.myshop.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myshop.R
import com.example.myshop.adapters.HomeListsAdapter
import com.example.myshop.adapters.Orientation
import com.example.myshop.databinding.FragmentSearchBinding
import com.example.myshop.ui.disconnect.State
import com.example.myshop.ui.home.HomeFragmentDirections
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding
    val vModel: SearchViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_search, container, false)
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_home).visibility = View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        search()
        initViews()
        sortProducts()
        filterProducts()
    }

    private fun filterProducts() {
        binding.txvFilterSearch.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_filterFragment)
        }
    }

    private fun sortProducts() {

        val txvSort = binding.txvSort
        val items = listOf("پربازدید ترین","محبوبترین","جدیدترین","گران ترین","ارزانترین")
        val adapter = ArrayAdapter(requireContext(), R.layout.spiner_item, vModel.sortedItems)
        (txvSort as? AutoCompleteTextView)?.setAdapter(adapter)

        txvSort.setOnItemClickListener { adapterView, view, i, l ->
            when (i){
                0 -> {
                    vModel.sortItem = SortItemProduct.POPULARITY.name.lowercase(Locale.getDefault())
                    vModel.searchInProducts()
                }
                1 -> {
                    vModel.sortItem = SortItemProduct.RATING.name.lowercase(Locale.getDefault())
                    vModel.searchInProducts()
                }
                2 -> {
                    vModel.sortItem = SortItemProduct.DATE.name.lowercase(Locale.getDefault())
                    vModel.searchInProducts()
                }
                3 -> {
                    vModel.sortItem = SortItemProduct.PRICE.name.lowercase(Locale.getDefault())
                    vModel.searchInProducts()
                }
                4 -> {
                    vModel.sortItem = SortItemProduct.DESC.name.lowercase(Locale.getDefault())
                    vModel.searchInProducts()
                }
            }

        }


    }

    private fun initViews() {
        observeState()
        if (vModel.term != null){
            binding.txvFilterSearch.text = vModel.term.toString()
        }
//        if (vModel.color != null){
//            binding.txvFilterSearch.text = vModel.color
//        }else if (vModel.size != null){
//            binding.txvFilterSearch.text = vModel.size
//        }

        val searchAdapter= HomeListsAdapter(Orientation.VERTICAL){
            goToDetail(it.id)
        }
        binding.recyclerSearch.adapter = searchAdapter
        vModel.searchList.observe(viewLifecycleOwner){
            searchAdapter.submitList(it)
        }
    }

    private fun search() {
        binding.searchEdtSearch.addTextChangedListener{ text ->
            vModel.searchKey = text.toString()
            vModel.searchInProducts()
        }
    }

    private fun goToDetail(id: Int) {
        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToProductDetailFragment(id))
    }

    private fun observeState() {
        vModel.state.observe(viewLifecycleOwner){
            when (it){
                State.LOADING -> {
                    binding.recyclerSearch.visibility = View.INVISIBLE
                    binding.progressBarSearch.visibility = View.VISIBLE
                    binding.imvProblemSearch.visibility = View.GONE
                }
                State.SUCCESS -> {
                    binding.recyclerSearch.visibility = View.VISIBLE
                    binding.progressBarSearch.visibility = View.GONE
                    binding.imvProblemSearch.visibility = View.GONE
                }
                State.FAILED -> {
                    binding.recyclerSearch.visibility = View.INVISIBLE
                    binding.progressBarSearch.visibility = View.INVISIBLE
                    binding.imvProblemSearch.visibility = View.VISIBLE
                }
            }
        }
    }

}