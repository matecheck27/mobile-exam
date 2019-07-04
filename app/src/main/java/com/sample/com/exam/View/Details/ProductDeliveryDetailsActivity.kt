package com.sample.com.exam.View.Details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CoordinatorLayout
import android.support.v4.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.sample.com.exam.AppDelegate
import com.sample.com.exam.Contracts.Details.IDetailsView
import com.sample.com.exam.Dependency.Component.ApplicationComponent
import com.sample.com.exam.Presenter.Base.Interface.IPresenter
import com.sample.com.exam.Presenter.Details.DetailsPresenter
import com.sample.com.exam.R
import com.sample.com.exam.Shared.Constants.Companion.EXTRAS_ID
import com.sample.com.exam.View.Base.ClientView
import com.sample.com.exam.ViewModel.ProductsViewModel
import kotlinx.android.synthetic.main.collapsing_map_layout_sample.*
import kotlinx.android.synthetic.main.row_delivery_layout.*
import javax.inject.Inject

class ProductDeliveryDetailsActivity : ClientView(),
        IDetailsView,
        GoogleMap.OnMapLoadedCallback,
        OnMapReadyCallback {

    @Inject
    lateinit var mPresenter: DetailsPresenter
    private var mGoogleMap: GoogleMap? = null
    private var mDefaultOffSet = -450

    private var mDeliveryCoordinate = LatLng(14.6197, 121.1000)

    companion object {
        fun newIntent(context: Context, id: Int): Intent {
            val intent = Intent(context.applicationContext, ProductDeliveryDetailsActivity::class.java)
            intent.putExtra(EXTRAS_ID, id)
            return intent
        }
    }

    override fun getContentView(): Int {
        return R.layout.collapsing_map_layout_sample
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)
        expandToolbarDefaultOffset(mDefaultOffSet)
    }

    private fun expandToolbarDefaultOffset(default: Int) {
        val params = app_bar_layout.layoutParams as CoordinatorLayout.LayoutParams
        params.behavior = AppBarLayout.Behavior()
        val behavior = params.behavior as AppBarLayout.Behavior?
        if (behavior != null) {
            behavior.topAndBottomOffset = default
        }
    }

    override fun onLoad() {
        onEvents()
        val id = onGetIntent() ?: 0
        mPresenter.loadProductDetail(id)

        mapView.onResume()
    }

    private fun onGetIntent(): Int? {
        return intent?.extras?.getInt(EXTRAS_ID)
    }

    override fun onLoadItem(item: ProductsViewModel) {

        val lat = item.Location?.Lat ?: 0.0
        val lng = item.Location?.Lng ?: 0.0

        mDeliveryCoordinate = LatLng(lat, lng)

        val desc = "${item.Description} ${getSeparator()}"
        tv_description.text = desc
        tv_address.text = item.Location?.Address

        val photo = item.ImageUrl

        Glide.with(this)
                .load(photo)
                .priority(Priority.HIGH)
                .placeholder(R.drawable.ic_image)
                .into(iv_product_image)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        this.mGoogleMap = googleMap

        try {
            googleMap?.isMyLocationEnabled = true
            googleMap?.uiSettings?.isMyLocationButtonEnabled = true
        } catch (e: SecurityException) {
            showToast(e.toString())
        }

        val markerOptions = MarkerOptions().position(mDeliveryCoordinate)
        mGoogleMap?.addMarker(markerOptions)
        mGoogleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(mDeliveryCoordinate, 17.0f))

    }

    override fun onMapLoaded() {

    }

    private fun getSeparator(): String? {
        return getString(R.string.label_separator)
    }

    private fun onEvents() {
        btn_iv_back.setOnClickListener {
            finish()
        }
    }

    override fun getPresenter(): IPresenter? {
        return mPresenter
    }

    override fun inject(applicationComponent: ApplicationComponent) {
        (application as AppDelegate).getComponent().inject(this)
    }
}