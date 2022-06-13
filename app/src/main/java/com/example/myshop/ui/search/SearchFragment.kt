package com.example.myshop.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    }

    private fun initViews() {
        val searchAdapter= HomeListsAdapter(Orientation.VERTICAL){
            goToDetail(it.id)
        }
        binding.recyclerSearch.adapter = searchAdapter
        vModel.searchList.observe(viewLifecycleOwner){
            searchAdapter.submitList(it)
        }
    }

    private fun search() {
        binding.searchMenu.searchEdtHome.addTextChangedListener{ text ->
            vModel.searchInProducts(text.toString())
        }
    }

    private fun goToDetail(id: Int) {
        findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToProductDetailFragment(id))
    }

}