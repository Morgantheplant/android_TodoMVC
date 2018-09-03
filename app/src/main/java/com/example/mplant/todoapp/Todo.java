package com.example.mplant.todoapp;

public class Todo {
    private String todoText;
    private Boolean isComplete = false;
    public Todo(String text){
        todoText = text;
    }
    public Boolean getIsComplete(){
        return isComplete;
    }
    public void setIsComplete(Boolean completed){
        isComplete = completed;
    }
    public String getTodoText(){
        return todoText;
    }
}
