package com.company;

/**
 * Created by Apple on 2018/3/8.
 */
public class ColumnModel implements Comparable<ColumnModel>{
    public String column1;
    public String column2;
    public String column3;
    public String column4;
    public String column5;


    public ColumnModel(String column1, String column2, String column3) {
        this.column1 = column1;
        this.column2 = column2;
        this.column3 = column3;
    }

    public ColumnModel(String column1, String column2, String column3, String column4, String column5) {
        this.column1 = column1;
        this.column2 = column2;
        this.column3 = column3;
        this.column4 = column4;
        this.column5 = column5;

    }



    @Override
    public int compareTo(ColumnModel o) {
        if (Utils.isNumeric(this.column3)&&Utils.isNumeric(o.column3)){
            int i = (int) (Double.parseDouble(this.column3)-Double.parseDouble(o.column3));
            return i;

        }else {
            return 0;
        }




    }
}
