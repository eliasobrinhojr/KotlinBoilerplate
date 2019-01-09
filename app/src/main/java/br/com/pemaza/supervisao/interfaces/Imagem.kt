package br.com.pemaza.supervisao.interfaces

import android.content.res.Resources
import android.widget.ImageView

interface Imagem {
    fun setImage(view: ImageView, resources: Resources)
}