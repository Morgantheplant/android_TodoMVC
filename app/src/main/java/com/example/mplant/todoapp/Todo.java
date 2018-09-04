package com.example.mplant.todoapp;

public class Todo {
    private String todoText;
    private Boolean isComplete = false;
    private Boolean isVisible = true;
    public Todo(String text){
        todoText = text;
    }
    public Boolean getIsComplete(){
        return isComplete;
    }
    public void setIsComplete(Boolean isComplete){
        this.isComplete = isComplete;
    }
    public String getTodoText(){
        return todoText;
    }
    public void setTodoText(String todoText){
        this.todoText = todoText;
    }
    public Boolean getVisible() {
        return isVisible;
    }
    public void setVisible(Boolean visible) {
        isVisible = visible;
    }
}

