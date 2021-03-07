package ee.taltech.sudoku

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

class GameStateRepository(val context: Context) {
    private lateinit var dbHelper: DbHelper
    private lateinit var db: SQLiteDatabase

    fun open(): GameStateRepository {
        dbHelper = DbHelper(context)
        db = dbHelper.writableDatabase
        return this
    }

    fun close(){
        dbHelper.close()
    }

    fun add(gameState: GameState){
        var contentValues = ContentValues()
        contentValues.put(DbHelper.GAMESTATE, gameState.gameBoard)
        contentValues.put(DbHelper.PERSON_LASTNAME, gameState.difficulty)
        db.insert(DbHelper.TABLE_PERSONS, null, contentValues)
    }

    fun getAll(): List<GameState> {
        val persons = ArrayList<GameState>()
        val columns = arrayOf(DbHelper.PERSON_ID, DbHelper.GAMESTATE, DbHelper.PERSON_LASTNAME)
        val cursor = db.query(DbHelper.TABLE_PERSONS, columns, null, null, null, null, DbHelper.PERSON_LASTNAME + ", " + DbHelper.GAMESTATE)
        while(cursor.moveToNext()){
            persons.add(
                    GameState(cursor.getInt(0),
                            cursor.getString(cursor.getColumnIndex(DbHelper.GAMESTATE)),
                            cursor.getString(cursor.getColumnIndex(DbHelper.PERSON_LASTNAME)))
            )
        }
        cursor.close()
        return persons
    }
}
