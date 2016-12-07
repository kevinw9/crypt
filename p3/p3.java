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
			return register;
		}
		
	}

	public static int map(int i, int j) {
		if (i == 0)
			return j + 5;
		return j  ;
	}

	public static void correlationAttack() {
		int[] keySeq = {0,0,0,0,0,1,1,1,0,1,1,1,0,1,0,0,0,1,1,0,0,0,1,0,1,1,0,1,1,1,1,0,0,0,1,0,0,1,1,0,0,0,1,1,0,1,1,1,1,1,0,1,0,0,0,1,0,0,1,0,1,0,0,1,1,0,0,0,1,0,0,1,0,1,1,0,1,1,1,1,1,0,1,1,1,0,0,0,1,1,1,1,0,1,0,1,0,0,0,1,1,1,0,1,1,0,0,1,0,1,1,1,1,0,1,1,1,1,0,1,0,1,1,0,1,0,0,1,0,1,1,0,0,1,0,1,0,1,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0,0,1,0,1,0,0,0,0,1,0,0,0,0,0,0,1,0,1,1,1,1,0,1,1,1,0,1,0,1,0,0,1,1,0,1,1,0,0,0};
		LFSR l13 = new LFSR(new int[] {1,0,1,1,0,0,1,1,0,1,0,1,1}, new int[] {0,0,0,0,0,0,0,0,0,0,0,0,1}, 2);
		int counter = 0;
		double max = 1;
		int[] maxState = {0,0,0,0,0,0,0,0,0,0,0,0,1};
		int[] init = new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1};
		while(counter < 9000) {
			LFSR loop13 = new LFSR(new int[] {1,0,1,1,0,0,1,1,0,1,0,1,1}, l13.getRegister(), 2);
			double diff = 0;
			int out = 0;
			for (int i = 0; i < 193; i++){
				out = loop13.shift(loop13.compNext());
				if (keySeq[i] != out) diff++;
			}

			if ((Arrays.equals(loop13.getRegister(), init))) {
				System.out.println("got here");
			}

			diff /= 193;
			if (diff < max) {
				max = diff;
				maxState = loop13.getRegister();
			} 
			l13.shift(l13.compNext());

			counter++;
			//System.out.print(out);
			//if ((counter % 1000) == 0) System.out.println("Counter: " + counter);
		}
		System.out.println(max);
		for (int i = 0; i < maxState.length; i++) {
			System.out.print(maxState[i] + ", ");
		}
		System.out.println();
	}
		
	public static void main(String[] args){
		//correlationAttack();



		//LFSR mod5 = new LFSR(new int[] {1,0,1,1,0,0,1,1,0,1,0,1,1}, new int[] {0,0,0,0,0,0,0,0,0,0,0,0,1}, 2);
		LFSR mod5 = new LFSR(new int[] {1,0,1,0,1,1,0,0,1,1,0,1,0,1,0}, new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}, 2);
		//LFSR mod5 = new LFSR(new int[] {1,1,0,0,1,0,0,1,0,1,0,0,1,1,0,1,0}, new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}, 2);
		int out2;
		int out5;
		for (int i = 0; i < 33000; i++){
			//out2 = mod2.shift(mod2.compNext());
			out5 = mod5.shift(mod5.compNext());
			System.out.print(out5);

		}
	}
}
	