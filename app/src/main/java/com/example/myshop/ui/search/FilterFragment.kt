package com.example.myshop.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.myshop.R
import com.example.myshop.adapters.FilterAdapter
import com.example.myshop.adapters.FilterCategoryAdapter
import com.example.myshop.databinding.FragmentFilterBinding
import com.example.myshop.databinding.FragmentSearchBinding
import com.example.myshop.model.Attribute
import com.example.myshop.model.Terms
import com.example.myshop.ui.disconnect.State
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class FilterFragment : Fragment() {

    lateinit var binding: FragmentFilterBinding
    val vModel: SearchViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_filter, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        observeState()
        val adapter = FilterAdapter{ term, checked ->

            if (checked){
                vModel.term = term.id
                requireActivity().onBackPressed()
            }


        }
        val adapterCategoryFilter = FilterCategoryAdapter{
            vModel.retrieveTerms(it)
            vModel.filter =if ( it.name == "رنگ") {
                "pa_color"
            }  else
                "pa_size"
        }

        binding.recyclerFilter.adapter = adapter
        binding.recyclerFilterName.adapter = adapterCategoryFilter

        vModel.attributes.observe(viewLifecycleOwner){
            Log.d("search----TAG", "initViews: $it")
            adapterCategoryFilter.submitList(it)
        }
        vModel.terms.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }


    }

    private fun observeState() {
        vModel.state.observe(viewLifecycleOwner){
            when (it){
                State.LOADING -> {
                    binding.recyclerFilter.visibility = View.VISIBLE
                    binding.recyclerFilterName.visibility = View.VISIBLE
                    binding.progressBarFilter.visibility = View.VISIBLE
                    binding.imvProblemFilter.visibility = View.GONE
                }
                State.SUCCESS -> {
                    binding.recyclerFilter.visibility = View.VISIBLE
                    binding.recyclerFilterName.visibility = View.VISIBLE
                    binding.progressBarFilter.visibility = View.GONE
                    binding.imvProblemFilter.visibility = View.GONE
                }
                State.FAILED -> {
                    binding.recyclerFilter.visibility = View.INVISIBLE
                    binding.recyclerFilterName.visibility = View.INVISIBLE
                    binding.progressBarFilter.visibility = View.INVISIBLE
                    binding.imvProblemFilter.visibility = View.VISIBLE
                }
            }
        }
    }

}