package com.mango.player.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import java.util.List;

import com.mango.player.bean.MusicList;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MUSIC_LIST".
*/
public class MusicListDao extends AbstractDao<MusicList, Long> {

    public static final String TABLENAME = "MUSIC_LIST";

    /**
     * Properties of entity MusicList.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "list_name");
        public final static Property Musics = new Property(2, String.class, "musics", false, "MUSICS");
    };

    private final StringConverter musicsConverter = new StringConverter();

    public MusicListDao(DaoConfig config) {
        super(config);
    }
    
    public MusicListDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MUSIC_LIST\" (" + //
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "\"list_name\" TEXT NOT NULL UNIQUE ," + // 1: name
                "\"MUSICS\" TEXT);"); // 2: musics
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MUSIC_LIST\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, MusicList entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getName());
 
        List musics = entity.getMusics();
        if (musics != null) {
            stmt.bindString(3, musicsConverter.convertToDatabaseValue(musics));
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, MusicList entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getName());
 
        List musics = entity.getMusics();
        if (musics != null) {
            stmt.bindString(3, musicsConverter.convertToDatabaseValue(musics));
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public MusicList readEntity(Cursor cursor, int offset) {
        MusicList entity = new MusicList( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : musicsConverter.convertToEntityProperty(cursor.getString(offset + 2)) // musics
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, MusicList entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.getString(offset + 1));
        entity.setMusics(cursor.isNull(offset + 2) ? null : musicsConverter.convertToEntityProperty(cursor.getString(offset + 2)));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(MusicList entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(MusicList entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
