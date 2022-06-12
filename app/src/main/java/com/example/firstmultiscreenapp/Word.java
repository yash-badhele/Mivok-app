package com.example.firstmultiscreenapp;

public class Word {
    private String English;
    private String Mivok;
    private int imageAddress=-1;
    private int audioResourceId;

    public Word(String e,String m,int audio){
        English=e;
        Mivok=m;
        audioResourceId=audio;
    }

    public Word(String e,String m,int i,int audio){

        English=e;
        Mivok=m;
        imageAddress=i;
        audioResourceId=audio;
    }

    public String getEnglish() {
        return English;
    }

    public String getMivok() {
        return Mivok;
    }

    public int getImageAddress(){
        return  imageAddress;
    }

    public int getAudioResourceId(){return audioResourceId;}
}
