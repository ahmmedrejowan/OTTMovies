package com.rejowan.ottmovies.data.local

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.rejowan.ottmovies.model.PlayHistory

class PlayHistoryDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    companion object {
        private const val DATABASE_NAME = "play_history.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_NAME = "play_history"
        private const val COLUMN_ID = "id"
        private const val COLUMN_MOVIE_ID = "movie_id"
        private const val MOVIE_URL = "movie_url"
        private const val COLUMN_TIMESTAMP = "timestamp"

        private const val CREATE_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,$COLUMN_MOVIE_ID TEXT,$MOVIE_URL TEXT,$COLUMN_TIMESTAMP TEXT)"

        private const val DROP_TABLE_QUERY = "DROP TABLE IF EXISTS $TABLE_NAME"

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE_QUERY)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_TABLE_QUERY)
        onCreate(db)
    }

    //-----------------------------------INSERT-----------------------------------
    fun insertPlayHistory(playHistory: PlayHistory): Long {
        val db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_MOVIE_ID, playHistory.movieId)
        contentValues.put(MOVIE_URL, playHistory.movieUrl)
        contentValues.put(COLUMN_TIMESTAMP, playHistory.timestamp)
        val result = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return result
    }

    //-----------------------------------UPDATE-----------------------------------
    fun updatePlayHistory(playHistory: PlayHistory): Int {
        val db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_MOVIE_ID, playHistory.movieId)
        contentValues.put(MOVIE_URL, playHistory.movieUrl)
        contentValues.put(COLUMN_TIMESTAMP, playHistory.timestamp)
        val result = db.update(TABLE_NAME, contentValues, "$COLUMN_ID=?", arrayOf(playHistory.id.toString()))
        db.close()
        return result
    }

    fun updateTimeStamp(id: Int, timestamp: String): Int {
        val db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_TIMESTAMP, timestamp)
        val result = db.update(TABLE_NAME, contentValues, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
        return result
    }

    fun updateTimeByMovieId(movieId: String, timestamp: String): Int {
        val db = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_TIMESTAMP, timestamp)
        val result = db.update(TABLE_NAME, contentValues, "$COLUMN_MOVIE_ID=?", arrayOf(movieId))
        db.close()
        return result
    }

    //-----------------------------------GET-----------------------------------
    fun getPlayHistory(): ArrayList<PlayHistory> {
        val playHistoryList = ArrayList<PlayHistory>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        if (cursor.moveToFirst()) {
            do {
                val playHistory = PlayHistory(
                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_ID)),
                    cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_URL)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIMESTAMP))
                )
                playHistoryList.add(playHistory)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return playHistoryList
    }

    fun getPlayHistoryByMovieId(movieId: String): PlayHistory? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_ID, COLUMN_MOVIE_ID, MOVIE_URL, COLUMN_TIMESTAMP),
            "$COLUMN_MOVIE_ID=?",
            arrayOf(movieId),
            null,
            null,
            null,
            null
        )
        var playHistory: PlayHistory? = null
        if (cursor.moveToFirst()) {
            playHistory = PlayHistory(
                cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOVIE_ID)),
                cursor.getString(cursor.getColumnIndexOrThrow(MOVIE_URL)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIMESTAMP))
            )
        }
        cursor.close()
        db.close()
        return playHistory
    }

    //-----------------------------------DELETE-----------------------------------
    fun deletePlayHistory(id: Int): Int {
        val db = writableDatabase
        val result = db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(id.toString()))
        db.close()
        return result
    }

    fun deletePlayHistoryByMovieId(movieId: String): Int {
        val db = writableDatabase
        val result = db.delete(TABLE_NAME, "$COLUMN_MOVIE_ID=?", arrayOf(movieId))
        db.close()
        return result
    }

    fun deleteAllPlayHistory(): Int {
        val db = writableDatabase
        val result = db.delete(TABLE_NAME, null, null)
        db.close()
        return result
    }

    //-----------------------------------Exists-----------------------------------
    fun isPlayHistoryExists(movieId: String): Boolean {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_ID),
            "$COLUMN_MOVIE_ID=?",
            arrayOf(movieId),
            null,
            null,
            null,
            null
        )
        val result = cursor.count > 0
        cursor.close()
        db.close()
        return result
    }


}