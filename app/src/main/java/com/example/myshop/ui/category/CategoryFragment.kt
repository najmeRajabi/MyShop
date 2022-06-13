package com.example.myshop.ui.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myshop.R
import com.example.myshop.databinding.FragmentCategoryBinding
import com.example.myshop.adapters.CategoryAdaptor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment() {


    lateinit var binding: FragmentCategoryBinding
    val vModel: CategoryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_category, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkConnectionInternet()
        initView()
    }

    private fun initView() {

        binding.lifecycleOwner= viewLifecycleOwner

        val adaptor = CategoryAdaptor{
            goToList(it.id)
        }
        binding.recyclerCategory.adapter=adaptor

        vModel.categories.observe(viewLifecycleOwner){
            adaptor.submitList(it)
        }


    }

    private fun checkConnectionInternet() {
        vModel.checkForInternet(requireContext())
        vModel.isConnected.observe(viewLifecycleOwner){
            if (!it)
                findNavController().navigate(R.id.action_categoryFragment_to_disconnectBlankFragment)
        }
    }

    private fun goToList(id: Int) {
        findNavController().navigate(CategoryFragmentDirections.actionCategoryFragmentToListFragment(id))
    }


}