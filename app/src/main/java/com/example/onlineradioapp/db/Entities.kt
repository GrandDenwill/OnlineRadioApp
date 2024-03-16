package com.example.onlineradioapp.db

import androidx.media3.common.C.SelectionFlags
import androidx.room.*
import com.example.onlineradioapp.repo.RadioModel
import com.example.onlineradioapp.utils.Constants.GENRES_TABLE
import com.example.onlineradioapp.utils.Constants.RADIO_TABLE
import kotlinx.coroutines.flow.Flow
import java.util.*

@Entity(tableName = GENRES_TABLE, indices = [Index(value = ["id"])])
data class GenreEntity(
    @PrimaryKey
    val id: String,
    val name: String
)


@Entity(tableName = RADIO_TABLE, indices = [Index(value = ["id"])],
    foreignKeys = [ForeignKey(
        entity = GenreEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("radioGenre"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class RadioEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val radioId: String = UUID.randomUUID().toString(),
    val radioName: String,
    val radioDescription: String,
    val radioURL: String,
    @ColumnInfo(index=true,name = "radioGenre")
    val radioGenre:Int,
    val isFavorite: Boolean,
    val radioImage: ByteArray?
) {
    constructor(model: RadioModel) : this(
        radioId = model.radioId,
        radioName = model.radioName,
        radioDescription = model.radioDescription,
        radioURL = model.radioURL,
        radioGenre = model.radioGenre,
        isFavorite = model.isFavorite,
        radioImage = model.radioImage
    )

    fun toModel(): RadioModel {
        return RadioModel(
            radioId = radioId,
            radioName = radioName,
            radioDescription = radioDescription,
            radioURL = radioURL,
            radioGenre = radioGenre,
            isFavorite = isFavorite,
            radioImage = radioImage
        )
    }

    data class GenreAndRadioStations(
        @Embedded
        val genre: GenreEntity,
        @Relation(
            parentColumn = "id",
            entityColumn = "radioGenre"
        )
        val stations:List<RadioEntity>
    )

    @Dao
    interface Store {
        @Insert
        suspend fun save(genre: GenreEntity)

        @Insert
        suspend fun save(vararg radioEntity: RadioEntity)

        @Transaction
        @Query("SELECT * FROM genres")
        fun getAll(): List<GenreAndRadioStations>

        @Transaction
        @Query("SELECT * FROM genres")
        fun all(): Flow<List<GenreAndRadioStations>>
        
        @Transaction
        @Query("SELECT * from genres where id=:id")
        suspend fun getByGenreId(id:Int): GenreAndRadioStations
        @Transaction
        @Query("SELECT * from radio ")
        fun getAllRadio():Flow<List<RadioEntity>>
    }
}