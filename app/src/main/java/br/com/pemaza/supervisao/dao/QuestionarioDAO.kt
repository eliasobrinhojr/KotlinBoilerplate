package br.com.pemaza.supervisao.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import br.com.pemaza.supervisao.model.Questionario
import io.reactivex.Single

@Dao
interface QuestionarioDAO {

    @Query("SELECT * FROM questionario where questionario.situacao = :situacao")
    fun getListaQuestionario(situacao: String): Single<List<Questionario>>

    @Query("SELECT * FROM questionario where questionario.id_agendamento = :id")
    fun getQuestionarioById(id: Int): Single<Questionario>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdateQuestionario(questionario: List<Questionario>)

    @Query("SELECT * FROM questionario WHERE questionario.situacao = :situacao LIMIT :limite")
    fun getListaQuestionarioLimit(situacao: String, limite: Int): Single<List<Questionario>>
}