package br.com.pemaza.supervisao.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable
import java.util.Date

@Entity
data class Questionario(
        @PrimaryKey(autoGenerate = false) var id_agendamento: String,
        @ColumnInfo(name = "filial") var filial: Int,
        @ColumnInfo(name = "data_agendamento") var data_agendamento: String,
        @ColumnInfo(name = "data_inicio") var data_inicio: Date,
        @ColumnInfo(name = "data_fim") var data_fim: Date,
        @ColumnInfo(name = "situacao") var situacao: String,
        @ColumnInfo(name = "percentual_avaliado") var percentual_avaliado: String,
        @ColumnInfo(name = "num_avaliadores") var num_avaliadores: Int
): Serializable