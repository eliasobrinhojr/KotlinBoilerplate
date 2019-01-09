package br.com.pemaza.supervisao.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import br.com.pemaza.supervisao.model.Usuario

@Dao
interface UsuarioDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertOrUpdateUsuario(usuario: Usuario)
}