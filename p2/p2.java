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
		private int[] initRegister;
		private boolean zerod;

		public LFSR(int[] conP, int[] startState, int m) {
			degree = conP.length;
			mod = m;
			connectionP = conP;
			register = startState; 
			initRegister = new int[degree];
			System.arraycopy(startState, 0, initRegister, 0, degree);
			zerod = false;
		}

		public int shift(int next){
			if (next == -1) {
				zerod = true;
				return 0;
			}
			zerod = false;
			int[] temp = register;
			int popped = register[0];
			for (int i = 0; i < register.length-1; i++)
				register[i] = temp[i+1];
			register[register.length-1] = next;
			return popped;
		}
		
		public int compNext(){
			if ((Arrays.equals(register, initRegister)) && !zerod) {
				return -1;
			}
			int sum = 0;
			for (int i = 0; i < connectionP.length; i++)
				sum += (connectionP[i] * register[i]);
			/*
			for (int i = 0; i < register.length; i++)
				System.out.print(register[i]);
			System.out.println();
			*/
			return (sum % mod);
		}
		
	}

	public static int map(int i, int j) {
		if (i == 0)
			return j * 2;
		return (j * 2) + 1;
	}
		
	public static void main(String[] args){
		LFSR mod2 = new LFSR(new int[] {1,1,0,0}, new int[] {1,0,0,0}, 2);
		LFSR mod5 = new LFSR(new int[] {2,4,1,1}, new int[] {1,0,0,0}, 5);
		int out2;
		int out5;
		for (int i = 0; i < 1; i++){
			out2 = mod2.shift(mod2.compNext());
			out5 = mod5.shift(mod5.compNext());
			System.out.print(map(out2, out5);
		}
	}
}
	
