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
        val contentValues = ContentValues()
        contentValues.putNull(DbHelper.GAMESTATE_ID)
        contentValues.put(DbHelper.GAMEBOARD, gameState.gameBoard)
        contentValues.put(DbHelper.DIFFICULTY, gameState.difficulty)
        contentValues.put(DbHelper.TIMESPENT, gameState.timeSpent)
        contentValues.put(DbHelper.GAMEFINISHED, gameState.gameFinished)
        db.insert(DbHelper.TABLE_GAMESTATE, null, contentValues)
    }

    fun getAll(): List<GameState> {
        val gameStates = ArrayList<GameState>()
        val columns = arrayOf(DbHelper.GAMESTATE_ID, DbHelper.GAMEBOARD, DbHelper.DIFFICULTY, DbHelper.TIMESPENT, DbHelper.GAMEFINISHED)
        val cursor = db.query(DbHelper.TABLE_GAMESTATE, columns, null, null, null, null, DbHelper.TIMESPENT)
        while(cursor.moveToNext()){
            gameStates.add(
                    GameState(
                        cursor.getInt(cursor.getColumnIndex(DbHelper.GAMESTATE_ID)),
                        cursor.getString(cursor.getColumnIndex(DbHelper.GAMEBOARD)),
                        cursor.getString(cursor.getColumnIndex(DbHelper.DIFFICULTY)),
                        cursor.getLong(cursor.getColumnIndex(DbHelper.TIMESPENT)),
                        cursor.getInt(cursor.getColumnIndex(DbHelper.GAMEFINISHED))
                    )
            )
        }
        cursor.close()
        return gameStates
    }
}
