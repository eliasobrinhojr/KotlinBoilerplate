package br.com.pemaza.supervisao.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Filial(
        @SerializedName("codigo")
        val codigo: String,
        @SerializedName("endereco")
        val endereco: String,
        @SerializedName("bairro")
        val bairro: String,
        @SerializedName("cidade")
        val cidade: String,
        @SerializedName("estado")
        val estado: String
) : Serializable {
    fun getLoja(): String {
        return "Loja $codigo"
    }

    fun getEnderecoCompleto(): String {
        return "$endereco, $bairro, \n$cidade, $estado"
    }
}