package br.com.pemaza.supervisao.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey

@Entity(tableName = "usuario_questionario_join",
        primaryKeys = ["id_usuario", "id_questionario"])
data class UsuarioQuestionario(
        @ForeignKey(entity = Usuario::class,
                parentColumns = ["usuario_codigo"],
                childColumns = ["id_usuario"],
                onDelete = ForeignKey.CASCADE)
        @ColumnInfo(name = "id_usuario")
        var idUsuario: String,

        @ForeignKey(entity = Questionario::class,
                parentColumns = ["id_agendamento"],
                childColumns = ["id_questionario"],
                onDelete = ForeignKey.CASCADE)
        @ColumnInfo(name = "id_questionario")
        var idQuestionario: String
)