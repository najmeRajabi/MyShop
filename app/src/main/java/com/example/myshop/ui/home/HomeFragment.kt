package com.example.myshop.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HeaderViewListAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.myshop.R
import com.example.myshop.adapters.HomeListsAdapter
import com.example.myshop.databinding.FragmentHomeBinding
import com.example.myshop.ui.disconnect.State
import com.google.android.material.snackbar.Snackbar
import com.example.myshop.adapters.Orientation
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
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vModel = vModel
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
//        val header: RecyclerView.ItemDecoration = layoutInflater.inflate(layoutInflater,R.layout.category_list_item,false)
//        binding.recyclerLastHome.addItemDecoration(header)
        val lastAdapter=HomeListsAdapter(Orientation.HORIZONTAL){
            goToDetail(it.id)}
        val mostSeenAdapter=HomeListsAdapter(Orientation.HORIZONTAL){ goToDetail(it.id) }
        val favoriteAdapter=HomeListsAdapter(Orientation.HORIZONTAL){ goToDetail(it.id) }
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

        vModel.state.observe(viewLifecycleOwner){
            when (it){
                State.LOADING -> { showLoading() }
                State.SUCCESS -> { hideLoading() }
                State.FAILED  -> { showErrorMessage()}
                else -> {}
            }
        }

        binding.fabGoToListHome.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_categoryFragment)
        }

        binding.menuSearch.searchEdtHome.setOnClickListener{ text ->
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun showErrorMessage() {
        val snackbar = Snackbar.make(binding.coordinator
            ,vModel.message.value.toString(),Snackbar.LENGTH_SHORT)
        snackbar.setAction("تلاش دوباره", View.OnClickListener {

            snackbar.dismiss()
        }).show()
    }

    private fun hideLoading() {
        binding.progressHome.visibility = View.INVISIBLE
        binding.recyclerMostSeenHome.visibility = View.VISIBLE
        binding.recyclerFavoriteHome.visibility = View.VISIBLE
        binding.recyclerLastHome.visibility = View.VISIBLE
        binding.txvFavoriteHome.visibility = View.VISIBLE
        binding.txvLastHome.visibility = View.VISIBLE
        binding.txvMostSeenHome.visibility = View.VISIBLE
    }

    private fun showLoading() {

        binding.progressHome.visibility = View.VISIBLE
        binding.recyclerMostSeenHome.visibility = View.INVISIBLE
        binding.recyclerFavoriteHome.visibility = View.INVISIBLE
        binding.recyclerLastHome.visibility = View.INVISIBLE
        binding.txvFavoriteHome.visibility = View.INVISIBLE
        binding.txvLastHome.visibility = View.INVISIBLE
        binding.txvMostSeenHome.visibility = View.INVISIBLE
    }

    private fun goToDetail(id: Int) {
        findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(id))
    }

}