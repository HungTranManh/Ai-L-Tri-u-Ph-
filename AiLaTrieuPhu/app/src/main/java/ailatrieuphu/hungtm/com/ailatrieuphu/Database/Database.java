package ailatrieuphu.hungtm.com.ailatrieuphu.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ailatrieuphu.hungtm.com.ailatrieuphu.PlayGame.Question;

public class Database {
    private Context context;
    String path;
    private static final String NAME_DATA = "Question";
    private SQLiteDatabase database;

    public Database(Context context) {
        this.context = context;
        copyData();
    }

    public void copyData() {
        String pathRoot = Environment.getDataDirectory() + "/data/" + context.getPackageName();
        String pathFolderData = pathRoot + "/database";
        new File(pathFolderData).mkdir();
        path = pathFolderData + "/" + NAME_DATA;
        File file = new File(path);
        if (file.exists()) {
            return;
        }
        try {
            InputStream in = context.getAssets().open(NAME_DATA);
            FileOutputStream out = new FileOutputStream(path);
            byte[] b = new byte[1024];
            int le = in.read(b);
            while (le >= 0) {
                out.write(b, 0, le);
                le = in.read(b);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openDatabase() {
        if (database != null && database.isOpen()) {
            return;
        }
        database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);

    }

    private void closeDatabase() {
        if (database != null && database.isOpen()) {
            database.close();
            database = null;
        }
    }

    public List<Question> select15Question() {
        List<Question> questions = new ArrayList<>();
        openDatabase();
        String query = "SELECT * FROM (SELECT * FROM Question ORDER BY RANDOM()) GROUP BY (level) ORDER BY _id ASC";
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Question q = new Question();
            int question = cursor.getColumnIndex("question");
            int id = cursor.getColumnIndex("_id");
            int level = cursor.getColumnIndex("level");
            int caseA = cursor.getColumnIndex("casea");
            int caseB = cursor.getColumnIndex("caseb");
            int caseC = cursor.getColumnIndex("casec");
            int caseD = cursor.getColumnIndex("cased");
            int trueCase = cursor.getColumnIndex("truecase");
            q.setQuery(cursor.getString(question));
            q.setId(cursor.getInt(id));
            q.setLevel(cursor.getInt(level));
            q.setCaseA(cursor.getString(caseA));
            q.setCaseB(cursor.getString(caseB));
            q.setCaseC(cursor.getString(caseC));
            q.setCaseD(cursor.getString(caseD));
            q.setTrueCase(cursor.getInt(trueCase));
            questions.add(q);
            cursor.moveToNext();
        }
        closeDatabase();
        return questions;
    }
    public long saveScore(String name,int score,int level,String money){
       openDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",name);
        contentValues.put("score",score);
        contentValues.put("level",level);
        contentValues.put("money",money);
       long check=database.insert("HightScore",null,contentValues);
       closeDatabase();
       return check;
    }
}
