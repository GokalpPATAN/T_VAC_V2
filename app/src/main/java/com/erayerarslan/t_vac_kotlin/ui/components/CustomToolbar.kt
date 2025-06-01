package com.erayerarslan.t_vac_kotlin.ui.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.LinearLayout
import com.erayerarslan.t_vac_kotlin.R

class CustomToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val btnBack: ImageButton
    private val btnHome: ImageButton
    private val titleText: TextView

    var onBackClick: (() -> Unit)? = null
    var onHomeClick: (() -> Unit)? = null

    init {
        LayoutInflater.from(context).inflate(R.layout.custom_toolbar, this, true)
        btnBack = findViewById(R.id.btnBack)
        btnHome = findViewById(R.id.btnHome)
        titleText = findViewById(R.id.toolbarTitle)

        btnBack.setOnClickListener { onBackClick?.invoke() }
        btnHome.setOnClickListener { onHomeClick?.invoke() }
    }

    fun setTitle(title: String) {
        titleText.text = title
    }

    fun showBack(show: Boolean) {
        btnBack.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun showHome(show: Boolean) {
        btnHome.visibility = if (show) View.VISIBLE else View.GONE
    }
}