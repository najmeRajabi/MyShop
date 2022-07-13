package com.example.myshop.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Message
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.myshop.R
import com.example.myshop.adapters.HomeListsAdapter
import com.example.myshop.adapters.Orientation
import com.example.myshop.adapters.ReviewAdapter
import com.example.myshop.adapters.SliderAdapter
import com.example.myshop.databinding.FragmentProductDetailBinding
import com.example.myshop.model.Product
import com.example.myshop.model.Review
import com.example.myshop.ui.disconnect.State
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import me.relex.circleindicator.CircleIndicator3
import org.intellij.lang.annotations.JdkConstants

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    private val args: ProductDetailFragmentArgs by navArgs()
    lateinit var binding: FragmentProductDetailBinding
    val vModel: ProductViewModel by activityViewModels()

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
        initReviews()

    }

    private fun initReviews() {
        val adapter = ReviewAdapter ({
            vModel.deleteReview(it.id)
        }){
            showAddReviewDialog(it)
        }
        binding.recyclerReview.adapter = adapter

        vModel.reviews.observe(viewLifecycleOwner){
            adapter.submitList(it)
        }
    }


    private fun addToCart() {
        binding.menuDetail.imvAddToCart.setOnClickListener {
            vModel.createOrder(requireContext())
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
        observeState()
        initSameProducts()
        addReview()

    }


    private fun addReview() {
        binding.txvAddReview.setOnClickListener {
//            vModel.setReview("sssssssssssaaaaaaaaaaaaaaa")
            if (vModel.registered.value == false) {
                vModel.showDefaultDialog(
                requireContext()," ثبت نام نکرده اید؟ "," برای ثبت نظر لازم است ثبت نام کنید یا در صورتی که حساب دارید وارد شوید! "," فهمیدم ")
            }else{
                showAddReviewDialog(null)
            }
        }

    }


    private fun initSameProducts() {
        val adapterSameProduct = HomeListsAdapter(Orientation.HORIZONTAL){
            goToDetail(it)
        }
        binding.recyclerSameProduct.adapter= adapterSameProduct
        vModel.sameProducts.observe(viewLifecycleOwner){
            adapterSameProduct.submitList(it)
        }
    }

    private fun goToDetail(product: Product) {
        findNavController().navigate(
            ProductDetailFragmentDirections.
            actionProductDetailFragmentToProductDetailFragment(product.id))
    }

    private fun observeState() {
        vModel.state.observe(viewLifecycleOwner){
            when (it){
                State.LOADING -> { showLoading() }
                State.SUCCESS -> { hideLoading() }
                State.FAILED  -> { showErrorMessage()}
            }
        }
    }

    private fun showErrorMessage() {
        binding.progressBarDetail.visibility = View.GONE
        binding.constraintDetail.visibility= View.GONE
        binding.imvProblemDetail.visibility = View.VISIBLE
        vModel.message.value?.let { vModel.showDefaultDialog(requireContext(),"خطا", it,"فهمیدم") }
    }

    private fun hideLoading() {
        binding.progressBarDetail.visibility = View.GONE
        binding.constraintDetail.visibility= View.VISIBLE
        binding.imvProblemDetail.visibility = View.GONE
    }

    private fun showLoading() {
        binding.progressBarDetail.visibility = View.VISIBLE
        binding.constraintDetail.visibility= View.GONE
        binding.imvProblemDetail.visibility = View.GONE
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

    private fun showAddReviewDialog(review: Review?) {
        val alertDialog = AlertDialog.Builder(requireContext())

        val dialogView = LayoutInflater.from(requireContext()).
        inflate(R.layout.dialog_view , null)

        val btnSetReview = dialogView.findViewById<MaterialButton>(R.id.btn_addReview_dialog)
        val btnCancel = dialogView.findViewById<MaterialButton>(R.id.btn_cancel_dialog)
        val edtReview = dialogView.findViewById<EditText>(R.id.edt_dialog_addReview)

        alertDialog.
            setView(dialogView)
        val mAlertDialog = alertDialog.create()
            alertDialog.apply {
            btnSetReview.setOnClickListener {
                if (edtReview.text.isNullOrBlank()){
                    edtReview.error= " نظر خالی است!!! "
                }else{
                    if (review == null) {
                        vModel.setReview(edtReview.text.toString())
                        mAlertDialog.dismiss()
                    }
                    else{
                        vModel.updateReview(review.id , edtReview.text.toString(),review)
                        mAlertDialog.dismiss()
                    }
                }
            }
            btnCancel.setOnClickListener {
                mAlertDialog.dismiss()
            }

        }
        mAlertDialog.show()

    }




}