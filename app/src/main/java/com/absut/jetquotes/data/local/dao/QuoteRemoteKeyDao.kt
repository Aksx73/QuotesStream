package com.absut.jetquotes.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.absut.jetquotes.model.QuoteRemoteKeys

@Dao
interface QuoteRemoteKeyDao {

    @Query("SELECT * FROM QuoteRemoteKeys WHERE id=:id")
    suspend fun getRemoteKey(id:String): QuoteRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllRemoteKeys(remoteKeys:List<QuoteRemoteKeys>)

    @Query("DELETE FROM QuoteRemoteKeys")
    suspend fun deleteAllRemoteKeys()

}