package com.example.auth;

public class Stat implements Comparable<Stat>{

    private  String fullName;
    private int wordCount;

    public Stat() {
    }

    public Stat(String fullName, int wordCount) {
        this.fullName = fullName;
        this.wordCount = wordCount;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }


    @Override
    public int compareTo(Stat stat) {
        int compareage = ((Stat)stat).getWordCount();
        return this.getWordCount() - compareage;
    }
}
