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
	private static Vector<BigInteger> facRList = new Vector<BigInteger>();

/*
	public static void _generateRList(){
		int count = 0;
		long sqrtL = (long)Math.sqrt(L)+1;
		sqrtL = 2000;
		for (BigInteger j = BigInteger.valueOf(1); j.compareTo(BigInteger.valueOf(sqrtL)) <= 0; j = j.add(BigInteger.ONE)){
			for (BigInteger k = BigInteger.valueOf(1); k.compareTo(BigInteger.valueOf(sqrtL)) <= 0; k = k.add(BigInteger.ONE)){
				rList.add(generateR(k, j));	
			//	if (++count >= L)return;
			}
		}
	}

	public static void cleanRList(){
		for (int i = 0; i < rList.size(); i++){
			BigInteger y = (rList.get(i).multiply(rList.get(i))).mod(N);
			for (int j = 0; j < facBase.size(); j++){
				while (y.mod(BigInteger.valueOf(facBase.get(j))).compareTo(BigInteger.ZERO) == 0){
					y = y.divide(BigInteger.valueOf(facBase.get(j)));
						
					if (y.compareTo(BigInteger.ONE) ==0)
						facRList.add(rList.get(i));
				}

			}
		}

	}
*/

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
		int max = 1;
		int k = max;
		int j = max;
		do {
			BigInteger x = generateR(BigInteger.valueOf(k), BigInteger.valueOf(j)); 
			addFactor(x, (x.multiply(x)).mod(N));
			//incrementing max
			if (j == 1 && k == max){
				max++; j = max; k = 1; continue;
			}
			//incrementing k
			if (j == max && k < max){
				k++; continue;
			}
			//cecrement j
			if (j > 1 && k == max){
				j--; continue;
			}
		} while (rList.size() < L);
	}

	public static void addFactor(BigInteger x, BigInteger y){
		for (int j = 0; j < facBase.size(); j++){
			while (y.mod(BigInteger.valueOf(facBase.get(j))).compareTo(BigInteger.ZERO) == 0){
				y = y.divide(BigInteger.valueOf(facBase.get(j)));
					
				if (y.compareTo(BigInteger.ONE) ==0)
					rList.add(x);
			}
		}
	}

	public static void main(String[] args){
		N = new BigInteger(args[0]);
		initFacBase();
		L = facBase.size()+5;
//		System.out.println(facBase.size());
		generateRList();
//		System.out.println(rList);
//		System.out.println(rList.size());
	}
}
