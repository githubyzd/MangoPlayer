package com.mango.player;

import android.content.Context;
import android.test.mock.MockContext;
import android.widget.Toast;

import com.mango.player.bean.MusicList;
import com.mango.player.dao.DaoMaster;
import com.mango.player.dao.DaoSession;
import com.mango.player.dao.MusicListDao;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yzd on 2017/11/6 0006.
 */
public class DBTest {
    @Test
    public void insert() throws Exception {
        Context context = new MockContext();

        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "user.db");

        DaoMaster daoMaster = new DaoMaster(helper.getWritableDb());

        DaoSession daoSession = daoMaster.newSession();

        MusicListDao musicListDao = daoSession.getMusicListDao();


        MusicList list = new MusicList();
        List path = new ArrayList();
        path.add("test1");
        path.add("test2");
        path.add("test3");
        path.add("test4");
        list.setMusics(path);
        long insertID = musicListDao.insert(list);

        if (insertID >= 0) {
            Toast.makeText(context, "插入 User 成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "插入User 失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void query() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

}