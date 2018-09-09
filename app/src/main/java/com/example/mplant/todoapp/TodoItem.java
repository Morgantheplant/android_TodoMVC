package com.example.mplant.todoapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

@Entity(tableName = "todo_table")
public class TodoItem {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String mId;

    @ColumnInfo
    private String text;

    @ColumnInfo(name= "complete")
    private Boolean isComplete;

    @Ignore
    public TodoItem(String text){
        this.mId = UUID.randomUUID().toString();
        this.text = text;
        this.isComplete = false;
    }

    public TodoItem(String id, String text, Boolean complete) {
        this.mId = id;
        this.text = text;
        this.isComplete = complete;
    }

    @NonNull
    public String getMId(){
        return this.mId;
    }

    public void setMId(@NonNull String id){
        this.mId = id;
    }
    public Boolean getIsComplete(){
        return this.isComplete;
    }

    public void setIsComplete(Boolean isComplete){
        this.isComplete = isComplete;
    }

    public String getText(){
        return this.text;
    }

    public void setText(String text){
        this.text = text;
    }

}
