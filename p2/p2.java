import java.math.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class p2{

	public static class LFSR {
		private int degree;
		private int mod;
		private int[] connectionP;
		private int[] register;

		public LFSR(int[] conP, int[] startState, int mod) {
			degree = conP.length;
			mod = mod;
			connectionP = conP;
			register = startState; 
		}

		public int shift(int next){
			int[] temp = register;
			for (int i = 0; i < register.length-1; i++){
				register[i] = temp[i+1];
			}
			register[register.length-1] = next;
			return temp[0];
		}

		
	}
		
	public static void main(String[] args){

		LFSR test = new LFSR(new int[] {0,0,1,1}, new int[] {1,0,0,0}, 2);
		for (int i = 0; i < test.degree; i++)
			System.out.println(test.register[i]);
		test.shift(2);
		for (int i = 0; i < test.degree; i++)
			System.out.println(test.register[i]);
	}
}
	
