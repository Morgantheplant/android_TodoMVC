package com.example.mplant.todoapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.UUID;

@Entity(tableName = "todo_table")
@TypeConverters(DateConverter.class)
public class TodoItem {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String mId;

    @ColumnInfo
    private String text;

    @ColumnInfo(name= "complete")
    private Boolean isComplete;

    @ColumnInfo
    private Date lastUpdated;

    @ColumnInfo
    private Date createDate;

    @Ignore
    public TodoItem(String text){
        this.mId = UUID.randomUUID().toString();
        this.text = text;
        this.isComplete = false;
        this.createDate = new Date();
        this.lastUpdated = this.createDate;
    }

    public TodoItem(String id, String text, Boolean complete, Date lastUpdated, Date createDate) {
        this.mId = id;
        this.text = text;
        this.isComplete = complete;
        this.lastUpdated = lastUpdated;
        this.createDate = createDate;
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

    public void setLastUpdated(Date lastUpdated){ this.lastUpdated = lastUpdated; }
    public Date getLastUpdated(){ return this.lastUpdated; }
    public void setCreateDate(Date createDate){this.createDate = createDate;}
    public Date getCreateDate() { return this.createDate; }

}
