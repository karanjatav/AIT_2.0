package com.example.darkknight.ait_20.Pojo;

/**
 * Created by DARKKNIGHT on 5/19/2017.
 */

public class Result_Object {
    String sub_code,sub_name,max_marks,obt_marks,pass_fail;

public Result_Object(String sub_code, String sub_name, String max_marks, String obt_marks,String pass_fail) {
        this.sub_code = sub_code;
        this.sub_name = sub_name;
        this.max_marks = max_marks;
        this.obt_marks = obt_marks;
        this.pass_fail = pass_fail;

        }

public String getSub_code() {
        return sub_code;
        }

public String getSub_name() {
        return sub_name;
        }

public String getMax_marks() {
        return max_marks;
        }

public String getObt_marks() {
        return obt_marks;
        }

public String getPass_fail() {
        return pass_fail;
        }

public void setSub_code(String sub_code) {
        this.sub_code = sub_code;
        }

public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
        }

public void setMax_marks(String max_marks) {
        this.max_marks = max_marks;
        }

public void setObt_marks(String obt_marks) {
        this.obt_marks = obt_marks;
        }

public void setPass_fail(String pass_fail) {
        this.pass_fail = pass_fail;
        }
        }
