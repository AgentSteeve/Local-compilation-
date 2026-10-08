package com.agentsteeve.client

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import android.widget.TextView
import android.graphics.Color
import android.graphics.Typeface

class OverlayView(private val context: Context) {

    private val windowManager =
        context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var view: TextView? = null

    fun show() {
        if (view != null) return

        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        ).apply {
            gravity = Gravity.TOP or Gravity.START
            x = 40
            y = 120
        }

        val tv = TextView(context).apply {
            text = "AgentSteeve · actif"
            setTextColor(Color.parseColor("#22D3EE"))
            setBackgroundColor(Color.parseColor("#CC0B1220"))
            typeface = Typeface.MONOSPACE
            textSize = 12f
            setPadding(24, 14, 24, 14)
        }

        view = tv
        windowManager.addView(tv, params)
    }

    fun hide() {
        view?.let {
            windowManager.removeView(it)
            view = null
        }
    }
}
