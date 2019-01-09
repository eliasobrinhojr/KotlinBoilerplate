package br.com.pemaza.supervisao.dagger.modules

import android.app.Application
import android.arch.persistence.room.Room
import br.com.pemaza.supervisao.dao.QuestionarioDAO
import br.com.pemaza.supervisao.dao.UsuarioDAO
import br.com.pemaza.supervisao.dao.UsuarioQuestionarioDAO
import br.com.pemaza.supervisao.data.AppDatabase
import br.com.pemaza.supervisao.utilities.DbName
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
open class DbModule(private val context: Application) {

    @Provides
    @Singleton
    open fun provideDatabase(): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, DbName).build()
    }

    @Provides
    open fun provideQuestionarioDao(db: AppDatabase): QuestionarioDAO = db.questionarioDAO()

    @Provides
    open fun provideUsuarioQuestionarioDao(db: AppDatabase): UsuarioQuestionarioDAO = db.usuarioQuestionarioDAO()

    @Provides
    open fun provideUsuarioDao(db: AppDatabase): UsuarioDAO = db.usuarioDAO()
}