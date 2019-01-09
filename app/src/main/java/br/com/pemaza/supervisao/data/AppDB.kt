package br.com.pemaza.supervisao.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import br.com.pemaza.supervisao.dao.QuestionarioDAO
import br.com.pemaza.supervisao.dao.UsuarioDAO
import br.com.pemaza.supervisao.dao.UsuarioQuestionarioDAO
import br.com.pemaza.supervisao.model.Questionario
import br.com.pemaza.supervisao.model.Usuario
import br.com.pemaza.supervisao.model.UsuarioQuestionario

@Database(entities = [Questionario::class, Usuario::class, UsuarioQuestionario::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun questionarioDAO(): QuestionarioDAO
    abstract fun usuarioQuestionarioDAO(): UsuarioQuestionarioDAO
    abstract fun usuarioDAO(): UsuarioDAO
}
