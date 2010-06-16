package com.breskeby.example;

/**
 * Created by IntelliJ IDEA.
 * User: Rene
 * Date: 08.06.2010
 * Time: 22:06:00
 * To change this template use File | Settings | File Templates.
 */
//: SoftHashMapTest.java
import java.lang.ref.*;
import java.util.*;

public class SoftHashMapTest {
  
  
  public static void main(String[] args) {
    Map hashMap = new HashMap();
    Map softMap = new SoftHashMap(5);

    for(int i = 0; i < 100; i++){
        softMap.put("" + i, new byte[10  * 1024*1024]);      // 10 MB
        softMap.get("" + i);
        System.out.println("Cache Size: "  + softMap.size());
    }

  }
}