package br.com.pemaza.supervisao.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Usuario(
        @PrimaryKey(autoGenerate = false) var usuario_codigo: String,
        var usuario_nome: String,
        var filial: String
)