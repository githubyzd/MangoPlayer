package com.mango.player.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.mango.player.bean.MusicList;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by yzd on 2017/11/6 0006.
 */

public class DBManager {
    private final static String dbName = "music_list";
    private static DBManager mInstance;
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;

    public DBManager(Context context) {
        this.context = context;
        openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
    }

    /**
     * 获取单例引用
     *
     * @param context
     * @return
     */
    public static DBManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (DBManager.class) {
                if (mInstance == null) {
                    mInstance = new DBManager(context);
                }
            }
        }
        return mInstance;
    }
    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getReadableDatabase();
        return db;
    }
    /**
     * 获取可写数据库
     */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName, null);
        }
        SQLiteDatabase db = openHelper.getWritableDatabase();
        return db;
    }

    /**
     * 插入一条记录
     *
     * @param list
     */
    public long insertList(MusicList list) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        MusicListDao listDao = daoSession.getMusicListDao();
        return listDao.insert(list);
    }

    /**
     * 插入用户集合
     *
     * @param lists
     */
    public void insertlistList(List<MusicList> lists) {
        if (lists == null || lists.isEmpty()) {
            return;
        }
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        MusicListDao listDao = daoSession.getMusicListDao();
        listDao.insertInTx(lists);
    }

    /**
     * 删除一条记录
     *
     * @param list
     */
    public void deletelist(MusicList list) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        MusicListDao listDao = daoSession.getMusicListDao();
        listDao.delete(list);
    }

    /**
     * 更新一条记录
     *
     * @param list
     */
    public void updatelist(MusicList list) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        MusicListDao listDao = daoSession.getMusicListDao();
        listDao.update(list);
    }

    /**
     * 查询用户列表
     */
    public List<MusicList> querylistList() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        MusicListDao listDao = daoSession.getMusicListDao();
        QueryBuilder<MusicList> qb = listDao.queryBuilder();
        List<MusicList> list = qb.list();
        return list;
    }
}
