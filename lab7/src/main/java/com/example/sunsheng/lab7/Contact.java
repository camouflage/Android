package com.example.sunsheng.lab7;

/**
 * Created by sunsheng on 12/4/15.
 */
public class Contact {
    String stuId;
    String name;
    String tel;

    public Contact(String stuId, String name, String tel) {
        this.stuId = stuId;
        this.name = name;
        this.tel = tel;
    }

    public String getStuId() {
        return stuId;
    }

    public String getName() {
        return name;
    }

    public String getTel() {
        return tel;
    }
}
