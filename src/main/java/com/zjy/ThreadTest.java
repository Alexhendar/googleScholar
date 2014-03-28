package com.zjy;

import java.util.Random;

public class ThreadTest {

	public static void main(String[] args) {
		Random random = new Random();
		for(int i=1;i<=100;i++){
			if(i%10 == 0){
				try {
					Thread.sleep(random.nextInt(5)*1000);
					System.out.println();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.print(i + "\t");
		}
	}

}
