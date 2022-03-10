package com.example.demorxgo;

public class SearchInfo {
    private static SearchInfo Medical;
    private SearchInfo()
    {

    }
    public static SearchInfo getInstance(){
        if (null == Medical){
            Medical = new SearchInfo();
        }
        return Medical;
    }

}
