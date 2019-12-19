package ishiicai.shohoku.ac.jp.yami_tweet_mixir;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TestOpenHelper extends SQLiteOpenHelper {

    //データベースのバージョン
    private static final int DATABASE_VERSION = 3;

    //データベース情報を変数に格納
    private static final String DATABASE_NAME = "StrNumberDB.db";
    private static final String TABLE_NAME = "strNumdb";
    private static final String _ID = "_id";
    private static final String COLUMN_NAME_TITLE = "StrNumber";

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" + _ID +
                    " INTEGER PRIMARY KEY," + COLUMN_NAME_TITLE + " INTEGER)";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    TestOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(
                SQL_CREATE_ENTRIES
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(
                SQL_DELETE_ENTRIES
        );
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onUpgrade(db, oldVersion, newVersion);
    }
}
