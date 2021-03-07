package ee.taltech.sudoku


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "app.db"
        const val DATABASE_VERSION = 1

        const val TABLE_GAMESTATE = "GAMESTATE"
        const val GAMESTATE_ID = "_id"
        const val GAMEBOARD = "gameboard"
        const val DIFFICULTY = "difficulty"
        const val TIMESPENT = "timespent"
        const val GAMEFINISHED = "gamefinished"

        const val SQL_CREATE_TABLES =
                "create table $TABLE_GAMESTATE (" +
                        "$GAMESTATE_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "$GAMEBOARD TEXT NOT NULL, " +
                        "$DIFFICULTY TEXT NOT NULL, " +
                        "$TIMESPENT INTEGER NOT NULL, " +
                        "$GAMEFINISHED INTEGER);"

        const val SQL_DELETE_TABLES =
                "DROP TABLE IF EXISTS $TABLE_GAMESTATE;"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLES);
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_TABLES);
        onCreate(db);
    }
}
