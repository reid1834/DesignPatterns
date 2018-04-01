package com.reid.sqlite.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.reid.sqlite.db.annotion.DbFiled;
import com.reid.sqlite.db.annotion.DbTable;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * The type Base dao.
 *
 * @param <T> the type parameter
 */
public abstract class BaseDao<T> implements IBaseDao<T> {

    /*
     * 持有数据库的引用
     */
    private SQLiteDatabase database;
    /*
     *  保证实例话一次
     */
    private boolean isInit = false;

    /*
     * 持有操作数据库表所对应的java类型
     */
    private Class<T> entityClass;

    /*
     * 维护着表名与成员变量的映射关系
     * key-->表名
     * value-->Field
     */
    private HashMap<String, Field> cacheMap;

    private String tableName;

    /**
     * Init boolean.
     *
     * @param entity         the entity
     * @param sqLiteDatabase the sq lite database
     * @return the boolean
     */
    protected synchronized boolean init(Class<T> entity, SQLiteDatabase sqLiteDatabase) {
        if (!isInit) {
            entityClass = entity;
            this.database = sqLiteDatabase;
            if (entity.getAnnotation(DbTable.class) == null) {
                tableName = entity.getClass().getSimpleName();
            } else {
                tableName = entity.getAnnotation(DbTable.class).value();
            }

            if (!database.isOpen()) {
                return false;
            }

            if (!TextUtils.isEmpty(createTable())) {
                database.execSQL(createTable());
            }

            cacheMap = new HashMap<>();
            initCacheMap();

            isInit = true;
        }

        return isInit;
    }

    /*
     * 维护映射关系
     */
    private void initCacheMap() {
        String sql = "select * from " + this.tableName + " limit 1, 0";
        Cursor cursor = null;
        try {
            cursor = database.rawQuery(sql, null);
            //表的列名数组
            String[] columnNames = cursor.getColumnNames();
            //拿到Fields数组
            Field[] columnFields = entityClass.getFields();
            for (Field field : columnFields) {
                field.setAccessible(true);

            }
            //开始找对应关系
            for (String columnName : columnNames) {
                Field columnField = null;

                for (Field field : columnFields) {
                    String fieldName = null;

                    if (field.getAnnotation(DbFiled.class) != null) {
                        fieldName = field.getAnnotation(DbFiled.class).value();
                    } else {
                        fieldName = field.getName();
                    }

                    //如果表的列名等于成员变量的注解名字
                    if (columnName.equals(fieldName)) {
                        columnField = field;
                        break;
                    }
                }

                //找到了对应关系
                if (columnField != null) {
                    cacheMap.put(columnName, columnField);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public Long insert(T entity) {
        Map<String, String> map = getValues(entity);
        ContentValues values = getContentValues(map);
        Long result = database.insert(tableName, null, values);
        return result;
    }

    //将map转换成ContentValues
    private ContentValues getContentValues(Map<String, String> map) {
        ContentValues contentValues = new ContentValues();
        Set keys = map.keySet();
        Iterator<String> iterator = keys.iterator();

        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = map.get(key);

            if (value != null) {
                contentValues.put(key, value);
            }
        }

        return contentValues;
    }

    private Map<String, String> getValues(T entity) {
        HashMap<String, String> result = new HashMap<>();
        Iterator fieldsIterator = cacheMap.values().iterator();

        //循环遍历映射map的Field
        while (fieldsIterator.hasNext()) {
            Field columnToField = (Field) fieldsIterator.next();
            String cacheKey = null;
            String cacheValue = null;

            if (columnToField.getAnnotation(DbFiled.class) != null) {
                cacheKey = columnToField.getAnnotation(DbFiled.class).value();
            } else {
                cacheKey = columnToField.getName();
            }

            try {
                if (null == columnToField.get(entity)) {
                    continue;
                }

                cacheValue = columnToField.get(entity).toString();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            result.put(cacheKey, cacheValue);
        }

        return result;
    }

    @Override
    public Long update(T entity, T where) {
        return null;
    }

    /*
     * 创建表
     */
    protected abstract String createTable();
}
