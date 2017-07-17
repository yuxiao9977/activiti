package com.yx.sm.frame.test;

import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

public class UnitTest {
	
	@Test
	public void test1() {
		int[] a = new int[1]; 
		a[0] = 2;   
		System.out.println(a[0]);
	}
	
	@Test
	public void test2() {
		Map<String , Integer> map = new TreeMap<String , Integer>();   
		map.put("B" , 2);
		map.put("A" , 1);
		map.put("C" , 3);
		map.put("D" , 4);
		map.put("E" , 5);   
		map.put("F" , 6);
		map.put("G" , 7);
		map.put("H" , 8);   
		map.put("I" , 9);
		map.put("J" , 10);
		
		for (String v:map.keySet()) {
			System.out.print(v+"  ");
		}
		System.out.println(" ---------------------");
		for (Integer v:map.values()) {
			System.out.print(v+"  ");
		}
		System.out.println(" ---------------------");
		for (int i = map.size()-1; i >= 0; i--) {
			Object[] str = map.keySet().toArray();
			System.out.print(map.get(str[i])+"  ");
		}
	}

}
