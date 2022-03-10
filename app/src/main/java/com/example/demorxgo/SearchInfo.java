package com.example.demorxgo;

public class SearchInfo {
    private static SearchInfo Medical;
    private SearchInfo()
    {
        // Empty constructor needed
    }
    public static SearchInfo getInstance(){
        if (null == Medical){
            Medical = new SearchInfo();
        }
        return Medical;
    }

}
