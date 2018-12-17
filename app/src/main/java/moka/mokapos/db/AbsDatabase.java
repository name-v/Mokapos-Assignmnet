package moka.mokapos.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.provider.BaseColumns;
import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public abstract class AbsDatabase<T> implements BaseColumns {

    protected static final String TEXT_TYPE = " TEXT";
    protected static final String INTEGER_TYPE = " INTEGER";
    protected static final String COMMA_SEP = ", ";

    public final String TAG = this.getClass().getSimpleName();

    protected final DatabaseHelper mDBHelper;

    public AbsDatabase(DatabaseHelper dbQueueHelper) throws IllegalArgumentException {
        if (dbQueueHelper == null)
            throw new IllegalArgumentException("DataBaseHelper is null or uninitialized");
        this.mDBHelper = dbQueueHelper;
    }

    protected abstract String getTableName();

    protected abstract void writeContentValue(T t, ContentValues values);

    protected abstract String getUniqueKeyColumnName();

    protected abstract List<T> getDataFromCursor(Cursor cursor);

    public void addAll(List<T> list) throws SQLiteException {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.beginTransaction();

        ContentValues values = new ContentValues();
        for (T t : list) {
            writeContentValue(t, values);
            db.insertOrThrow(getTableName(), null, values);
            values.clear();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
    }

    protected void add(T t) throws SQLiteException {
        SQLiteDatabase db = mDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        writeContentValue(t, values);
        db.insertOrThrow(getTableName(), null, values);
        values.clear();
        db.close();
    }

    public void inserOrUpdate(T t, String id) {
        if (getById(id) == null) {
            add(t);
        } else {
            SQLiteDatabase db = mDBHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            writeContentValue(t, values);
            db.update(getTableName(), values, getUniqueKeyColumnName() + " = ?", new String[]{id});
            values.clear();
            db.close();
        }
    }


    protected final boolean writeToDb(ContentValues contentValues) throws SQLiteException {
        SQLiteDatabase db = null;
        if (contentValues == null) return false;
        try {
            db = mDBHelper.getWritableDatabase();
            if (db == null) {
                Log.w(TAG, "DB object is null");
                return false;
            }
            long result = db.insert(getTableName(), null, contentValues);
            return result != -1;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public final boolean deleteFromDB(String id) {
        SQLiteDatabase db;
        if (TextUtils.isEmpty(id)) return false;
        db = mDBHelper.getWritableDatabase();
        if (db == null) {
            Log.w(TAG, "DB object is null");
            return false;
        }
        long result = db.delete(getTableName(), getUniqueKeyColumnName() + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return result != -1;
    }


    public List<T> getAllData() {
        SQLiteDatabase db = null;
        db = mDBHelper.getReadableDatabase();
        if (db == null) {
            Log.w(TAG, "DB object is null");
            return null;
        }
        String query = String.format("SELECT * FROM %s;", getTableName());
        Cursor cursor = db.rawQuery(query, null);
        return getDataFromCursor(cursor);
    }


    public List<T> getAllData(String whereClause, String[] whereArgs, String orderByColumn, boolean sortDesc) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = mDBHelper.getReadableDatabase();
            if (db == null) {
                Log.w(TAG, "DB object is null");
                return new ArrayList<>();
            }
            cursor = db.query(getTableName(), null, whereClause, whereArgs, null, null, orderByColumn + (sortDesc ? " DESC" : ""));
            return getDataFromCursor(cursor);
        } catch (SQLiteException e) {
            Log.w(TAG, e);
            return new ArrayList<>();
        } finally {
            if (db != null) {
                db.close();
            }
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public T getById(String uid) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        if (TextUtils.isEmpty(uid)) return null;
        try {
            db = mDBHelper.getReadableDatabase();
            if (db == null) {
                Log.w(TAG, "DB object is null");
                return null;
            }
            cursor = db.query(getTableName(), null, String.format("%s = ?", getUniqueKeyColumnName()), new String[]{uid}, null, null, null);
            List<T> items = getDataFromCursor(cursor);
            if (items.size() == 1)
                return items.get(0);
            else if (items.size() > 1) {
                Log.e(TAG, "More than 1 items have same id. Returning first");
                return items.get(0);
            }
            return null;
        } catch (SQLiteException e) {
            Log.w(TAG, e);
            return null;
        } finally {
            if (db != null) {
                db.close();
            }
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public void clearAllData() {
        SQLiteDatabase db = null;
        try {
            db = mDBHelper.getWritableDatabase();
            if (db == null) {
                Log.w(TAG, "DB object is null");
            }
            if (db != null) {
                db.delete(getTableName(), null, null);
            }
        } catch (SQLiteException e) {
            Log.w(TAG, e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }

    public long getItemCount() {
        SQLiteDatabase db = null;
        try {
            db = mDBHelper.getReadableDatabase();
            if (db == null) {
                Log.w(TAG, "DB object is null");
                return 0;
            }
            return DatabaseUtils.queryNumEntries(db, getTableName());
        } catch (SQLiteException e) {
            Log.w(TAG, e);
            return -1;
        } catch (Exception e) {
            Log.w(TAG, e);
            return -1;
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }
}
