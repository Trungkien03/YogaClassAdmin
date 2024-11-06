package loki.edu.yogaclassadmin.repository.sqlite


import android.content.ContentValues
import android.content.Context
import loki.edu.yogaclassadmin.database.sqlite.DatabaseHelper
import loki.edu.yogaclassadmin.model.YogaClass

class SQLiteYogaClassRepository(context: Context) {

    private val dbHelper = DatabaseHelper(context)

    fun addClass(yogaClass: YogaClass): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_CLASS_ID, yogaClass.id)
            put(DatabaseHelper.COLUMN_DATE, yogaClass.date)
            put(DatabaseHelper.COLUMN_TIME, yogaClass.time)
            put(DatabaseHelper.COLUMN_CAPACITY, yogaClass.capacity)
            put(DatabaseHelper.COLUMN_CLASS_TYPE, yogaClass.classType)
            put(DatabaseHelper.COLUMN_PRICE, yogaClass.price)
            put(DatabaseHelper.COLUMN_DESCRIPTION, yogaClass.description)
        }
        return db.insert(DatabaseHelper.TABLE_CLASSES, null, values)
    }

    fun getClasses(): List<YogaClass> {
        val db = dbHelper.readableDatabase
        val cursor = db.query(DatabaseHelper.TABLE_CLASSES, null, null, null, null, null, null)
        val classes = mutableListOf<YogaClass>()
        if (cursor.moveToFirst()) {
            do {
                val yogaClass = YogaClass(
                    id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CLASS_ID)),
                    date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DATE)),
                    time = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_TIME)),
                    capacity = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CAPACITY)),
                    classType = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_CLASS_TYPE)),
                    price = cursor.getDouble(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_PRICE)),
                    description = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_DESCRIPTION))
                )
                classes.add(yogaClass)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return classes
    }

    fun deleteClass(classId: String): Int {
        val db = dbHelper.writableDatabase
        return db.delete(DatabaseHelper.TABLE_CLASSES, "${DatabaseHelper.COLUMN_CLASS_ID} = ?", arrayOf(classId))
    }
}

