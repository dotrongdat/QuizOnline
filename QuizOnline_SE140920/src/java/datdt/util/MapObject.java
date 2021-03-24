/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datdt.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author Win
 */
public class MapObject extends HashMap<String, String> implements Serializable{
    public MapObject() {
    }
    public void getMap(String resource) throws IOException{
        FileReader fr=null;
        BufferedReader br=null;
        try{
            fr=new FileReader(resource);
            if(fr!=null){
                br=new BufferedReader(fr);
                if(br!=null){
                    while(br.ready()){
                        String line=br.readLine();
                        String[] splitStr=line.split("=");
                        if(splitStr.length==2){
                            this.put(splitStr[0].trim(), splitStr[1].trim());
                        }                        
                    }
                }
            }
        }finally{
            if(fr!=null){
                fr.close();
            }
            if(br!=null){
                br.close();
            }
        }
    }
}
