package br.com.pemaza.supervisao.model

import android.content.res.Resources
import android.support.v4.content.res.ResourcesCompat
import android.widget.ImageView
import br.com.pemaza.supervisao.R
import br.com.pemaza.supervisao.interfaces.Imagem

class FotoPlaceholder: Imagem {
    override fun setImage(view: ImageView, resources: Resources) {
        val placeholder = ResourcesCompat.getDrawable(resources, R.drawable.icon_foto_placeholder, null)
        view.setImageDrawable(placeholder)
    }
}