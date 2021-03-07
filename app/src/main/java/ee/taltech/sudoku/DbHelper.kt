package ee.taltech.sudoku


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context):SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "app.db"
        const val DATABASE_VERSION = 1

        const val TABLE_PERSONS = "PERSONS"
        const val PERSON_ID = "_id"
        const val GAMESTATE = "gamestate"
        const val PERSON_LASTNAME = "lastname"

        const val SQL_CREATE_TABLES =
                "create table $TABLE_PERSONS (" +
                        "$PERSON_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "$GAMESTATE TEXT NOT NULL, " +
                        "$PERSON_LASTNAME TEXT NOT NULL);"

        const val SQL_DELETE_TABLES =
                "DROP TABLE IF EXISTS $TABLE_PERSONS;"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLES);
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_TABLES);
        onCreate(db);
    }
}
