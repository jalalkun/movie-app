package com.jalalkun.movieapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: FavoriteMovieEntity): Long

    @Query(
        """
        SELECT * FROM FavoriteMovieEntity ORDER BY created_at DESC LIMIT :limit OFFSET :offset
    """
    )
    fun getListFavoriteMovie(limit: Int, offset: Int): List<FavoriteMovieEntity>

    @Query("DELETE FROM FavoriteMovieEntity WHERE id=:id")
    fun deleteById(id: Long)

    @Query("DELETE FROM FavoriteMovieEntity WHERE movie_id=:id")
    fun deleteByMovieId(id: Int): Int

    @Query("SELECT COUNT(*) FROM FavoriteMovieEntity WHERE movie_id = :movieId")
    fun isMovieExist(movieId: Int): Int

    @Query("""SELECT * FROM FavoriteMovieEntity WHERE movie_id=:movieId""")
    fun getMovie(movieId: Int): FavoriteMovieEntity?
}