package com.reid.sqlite;

import com.reid.sqlite.db.annotion.DbFiled;
import com.reid.sqlite.db.annotion.DbTable;

@DbTable("tb_user")
public class User {

    @DbFiled("name")
    public String name;

    @DbFiled("password")
    public String password;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User() {

    }
}
