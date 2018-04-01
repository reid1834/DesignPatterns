package com.reid.sqlite;

import com.reid.sqlite.db.BaseDao;

public class UserDao extends BaseDao {
    @Override
    protected String createTable() {
        return "create table if not exists tb_user(name varchar(20), password varchar(10))";
    }
}
