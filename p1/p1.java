import java.math.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class p1{
	
	private static BigInteger N;
	private static int bound = 1000;
	private static Vector<Integer> facBase = new Vector<Integer>();
	private static long L;
	private static Vector<BigInteger> rList = new Vector<BigInteger>();

	public static boolean isPrime(int num){
		if (num<2) return false;
		if (num==2) return true;
		if (num % 2 == 0) return false;
		for (int i = 3; i * i <= num; i += 2)
			if (num % i == 0) return false;
		return true;
	}

	/** Calculate the square root of a BigInteger in logarithmic time */
	public static BigInteger squareRoot(BigInteger x) {
		  BigInteger right = x, left = BigInteger.ZERO, mid;
		  while(right.subtract(left).compareTo(BigInteger.ONE) > 0) {
				mid = (right.add(left)).shiftRight(1);
				if(mid.multiply(mid).compareTo(x) > 0)
					  right = mid;
				else
					  left = mid;
		  }
		  return left;
	} 

	public static void initFacBase(){
		facBase.add(2);
		for (int i = 3; i <= bound; i += 2)	
			if (isPrime(i))facBase.add(i);
	}

	public static BigInteger generateR(BigInteger k, BigInteger j){
		return (squareRoot(N.multiply(k))).add(j);
	}

	public static void generateRList(){
		int count = 0;
		long sqrtL = (long)Math.sqrt(L)+1;
		for (BigInteger j = BigInteger.valueOf(1); j.compareTo(BigInteger.valueOf(sqrtL)) <= 0; j = j.add(BigInteger.ONE)){
			for (BigInteger k = BigInteger.valueOf(1); k.compareTo(BigInteger.valueOf(sqrtL)) <= 0; k = k.add(BigInteger.ONE)){
				rList.add(generateR(k, j));	
				if (++count >= L)return;
			}
	}

	public static void main(String[] args){
		N = new BigInteger(args[0]);
		initFacBase();
		L = facBase.size()+5;
		generateRList();
		System.out.println(rList);
	}
}
