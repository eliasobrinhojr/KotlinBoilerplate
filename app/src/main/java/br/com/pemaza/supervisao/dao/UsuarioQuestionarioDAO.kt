package br.com.pemaza.supervisao.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import br.com.pemaza.supervisao.helpers.enums.QuestionarioStatus
import br.com.pemaza.supervisao.model.Questionario
import br.com.pemaza.supervisao.model.UsuarioQuestionario
import io.reactivex.Single

@Dao
interface UsuarioQuestionarioDAO {
    @Query("SELECT quest.* FROM questionario quest INNER JOIN usuario_questionario_join userQuest ON quest.id_agendamento = userQuest.id_questionario WHERE userQuest.id_usuario = :id_usuario AND quest.situacao = :situacao")
    fun getListaQuestionarioPorUsuario(situacao: String, id_usuario: String): Single<List<Questionario>>

    @Query("SELECT quest.* FROM questionario quest INNER JOIN usuario_questionario_join userQuest ON quest.id_agendamento = userQuest.id_questionario WHERE userQuest.id_usuario = :id_usuario AND quest.situacao != :situacao")
    fun getListaQuestionarioPorUsuarioAndamentoPendentes(situacao: String, id_usuario: String): Single<List<Questionario>>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertRelacao(usuarioQuestionario: UsuarioQuestionario)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertListRelacao(lista: List<UsuarioQuestionario>)
}