import java.math.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class p3{

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
			if ((Arrays.equals(register, initRegister)) && !zerod)
				return -1;
			int sum = 0;
			for (int i = 0; i < connectionP.length; i++)
				sum += (connectionP[i] * register[i]);
			return (sum % mod);
		}

		public int[] getRegister() {
			int[] returnReg = new int[register.length];
			for (int i = 0; i < register.length; i++) {
				returnReg[i] = register[i];
			}
			return returnReg;
		}
		
	}

	public static int map(int i, int j) {
		if (i == 0)
			return j + 5;
		return j  ;
	}

	public static int[] correlationAttack(int[] poly, int[] startState, int loops) {
		int[] keySeq = {0,0,0,0,0,1,1,1,0,1,1,1,0,1,0,0,0,1,1,0,0,0,1,0,1,1,0,1,1,1,1,0,0,0,1,0,0,1,1,0,0,0,1,1,0,1,1,1,1,1,0,1,0,0,0,1,0,0,1,0,1,0,0,1,1,0,0,0,1,0,0,1,0,1,1,0,1,1,1,1,1,0,1,1,1,0,0,0,1,1,1,1,0,1,0,1,0,0,0,1,1,1,0,1,1,0,0,1,0,1,1,1,1,0,1,1,1,1,0,1,0,1,1,0,1,0,0,1,0,1,1,0,0,1,0,1,0,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0,0,1,0,1,0,0,0,0,1,0,0,0,0,0,0,1,0,1,1,1,1,0,1,1,1,0,1,0,1,0,0,1,1,0,1,1,0,0,0};
		LFSR l13 = new LFSR(poly, startState, 2);
		int counter = 0;
		double max = 1;
		int out5 = 0;

		int[] maxState = startState;
		int[] init = startState;

		for (int j = 0; j < loops; j++){
			LFSR loop13 = new LFSR(poly, l13.getRegister(), 2);
			double diff = 0;
			int out = 0;
			for (int i = 0; i < 193; i++){
				out = loop13.shift(loop13.compNext());
				if (keySeq[i] != out) diff++;
			}

			if ((Arrays.equals(loop13.getRegister(), init))) {
				for (int i = 0; i < maxState.length; i++) {
					//System.out.print(loop13.getRegister()[i] + ", ");
				}
				//System.out.println("got here");
			}

			diff /= 193;
			if (diff < .30) {
				System.out.println(diff);
			}
			if (diff < max) {
				max = diff;
				maxState = l13.getRegister();
			}

			l13.shift(l13.compNext());
		

		}

		System.out.println(max);
		for (int i = 0; i < maxState.length; i++) {
			System.out.print(maxState[i] + " ");

		}
		System.out.println("");
		
		return maxState;
	}


		
	public static void main(String[] args){
		int[] start13 = correlationAttack(new int[] {1,0,1,1,0,0,1,1,0,1,0,1,1}, new int[] {0,0,0,0,0,0,0,0,0,0,0,0,1}, 9000);
		int[] start15 = correlationAttack(new int[] {1,0,1,0,1,1,0,0,1,1,0,1,0,1,0}, new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}, 33000);
		int[] start17 = correlationAttack(new int[] {1,1,0,0,1,0,0,1,0,1,0,0,1,1,0,1,0}, new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}, 132000);



		String testNum = "";
		String realNum = "0000011101110100011000101101111000100110001101111101000100101001100010010110111110111000111101010001110110010111101111010110100101100101010000000011111110010100001000000101111011101010011011000";
		LFSR mod13 = new LFSR(new int[] {1,0,1,1,0,0,1,1,0,1,0,1,1}, start13, 2);
		LFSR mod15 = new LFSR(new int[] {1,0,1,0,1,1,0,0,1,1,0,1,0,1,0}, start15, 2);
		LFSR mod17 = new LFSR(new int[] {1,1,0,0,1,0,0,1,0,1,0,0,1,1,0,1,0}, start17, 2);
		for (int i = 0; i < 193; i++){
			int count = 0;
			count += mod13.shift(mod13.compNext()) + mod15.shift(mod15.compNext()) + mod17.shift(mod17.compNext());
			//System.out.println(count);
			if (count > 1) {
				testNum += "1";
			} else {
				testNum += "0";
			}
			
			

		}
		if (testNum.equals(realNum)) {
			System.out.println("True");
		} else {
			System.out.println("False");
		}
		System.out.println(testNum);
		

		//0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1
		//0,0,1,1,0,0,1,1,1,0,0,1,1,0,0
		//0,0,0,1,0,1,0,1,0,0,0,1,1,1,1,1,0
	}
}
	