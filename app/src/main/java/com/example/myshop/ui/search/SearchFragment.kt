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
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myshop.R
import com.example.myshop.adapters.HomeListsAdapter
import com.example.myshop.adapters.Orientation
import com.example.myshop.databinding.FragmentSearchBinding
import com.example.myshop.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SearchFragment : Fragment() {

    lateinit var binding: FragmentSearchBinding
    val vModel: SearchViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_search, container, false)
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
        val items = listOf("پربازدید ترین","محبوبترین","جدیدترین","بیشترین قیمت")
        val adapter = ArrayAdapter(requireContext(), R.layout.spiner_item, items)
        (txvSort as? AutoCompleteTextView)?.setAdapter(adapter)

        txvSort.setOnItemClickListener { adapterView, view, i, l ->
            when (i){
                0 -> vModel.sortItem = SortItemProduct.POPULARITY.name.lowercase(Locale.getDefault())
                1 -> vModel.sortItem = SortItemProduct.RATING.name.lowercase(Locale.getDefault())
                2 -> vModel.sortItem = SortItemProduct.DATE.name.lowercase(Locale.getDefault())
                3 -> vModel.sortItem = SortItemProduct.PRICE.name.lowercase(Locale.getDefault())
            }
        }


    }

    private fun initViews() {
        binding.searchMenuEdtFieldHome.setEndIconOnClickListener{
            vModel.searchInProducts()
        }
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
        }
    }

    private fun goToDetail(id: Int) {
        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToProductDetailFragment(id))
    }

}