package com.skymxc.demo.lession_14_dialog;

/**
 * Created by sky-mxc
 */
public class PersonPorperty {
    String value;
    PORPERTY porperty;
   public enum PORPERTY{

       NAME("姓名")
       ;
      private String desc;

       private PORPERTY(String desc){
           this.desc =desc;
       }


       @Override
       public String toString() {
           return desc;
       }
   }
}
