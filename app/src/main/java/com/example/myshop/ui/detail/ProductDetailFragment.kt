package com.example.myshop.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.myshop.R
import com.example.myshop.adapters.SliderAdapter
import com.example.myshop.databinding.FragmentProductDetailBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import me.relex.circleindicator.CircleIndicator3

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private val args: ProductDetailFragmentArgs by navArgs()
    lateinit var binding: FragmentProductDetailBinding
    val vModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.fragment_product_detail,
            container,
            false
        )
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_home).visibility =
            View.GONE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkConnectionInternet()
        initView()
        addToCart()

    }

    private fun addToCart() {
        binding.menuDetail.imvAddToCart.setOnClickListener {
            vModel.createOrder()
            vModel.saveOrderToSharedPreferences(requireContext())
            showMessage()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initView() {
        val id = args.idArg
        vModel.getProduct(id)
        binding.vModel = vModel
        binding.lifecycleOwner = viewLifecycleOwner

        vModel.product.observe(viewLifecycleOwner) {
            binding.txvDescriptionDetail.text =
                it.description?.let { it1 -> HtmlCompat.fromHtml(it1, HtmlCompat.FROM_HTML_MODE_LEGACY) }
            binding.txvPriceDetail.text =
                "%,d".format(it.price.toInt()) + " " + getString(R.string.tooman)
            binding.ratingDetail.progress = it.averageRating?.toInt() ?: 0
            binding.txvRateCountDetail.text = " (${it.ratingCount}) رأی "
        }

        binding.menuDetail.imvBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        initImageView()
    }

    private fun checkConnectionInternet() {
        vModel.checkForInternet(requireContext())
        vModel.isConnected.observe(viewLifecycleOwner) {
            if (!it)
                findNavController().navigate(R.id.action_productDetailFragment_to_disconnectBlankFragment)
        }
    }

    private fun initImageView() {

        val viewPager: ViewPager2 = binding.viewPagerDetail
        vModel.product.observe(viewLifecycleOwner) {
            val sliderAdapter = SliderAdapter(requireContext(), it.images)
            viewPager.adapter = sliderAdapter
            val indicator: CircleIndicator3 = binding.indicatorDetail
            indicator.setViewPager(viewPager)
        }


    }

    private fun showMessage() {
        vModel.orderMessage.observe(viewLifecycleOwner) {
            val snackbar =
                Snackbar.make(binding.coordinatorDetail, it, Snackbar.LENGTH_SHORT)
            snackbar.show()
        }
    }

}