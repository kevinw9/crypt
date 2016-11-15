import java.math.*;
import java.io.*;
import java.util.*;
import java.lang.*;

public class p1{
	
	private static BigInteger N;
	private static int bound = 16000;
	private static Vector<Integer> facBase = new Vector<Integer>();
	private static long L;
	private static Vector<BigInteger> rList = new Vector<BigInteger>();
	private static int[][] inMatrix;
	private static int[][] outMatrix;
	private static Hashtable<String,Boolean> inMatrixStringTable = new Hashtable<String,Boolean>();

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
		inMatrix = new int[(int)L][facBase.size()];
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
		String boolString = "";
		//System.out.println(" ");
		//System.out.println("New Number " + y);
		boolean added = false;
		for (int j = 0; j < facBase.size(); j++){
			while (y.mod(BigInteger.valueOf(facBase.get(j))).compareTo(BigInteger.ZERO) == 0){
				//System.out.println("Divides by " + facBase.get(j));
				y = y.divide(BigInteger.valueOf(facBase.get(j)));
				
				inMatrix[rList.size()][j]++;
				
				if (y.compareTo(BigInteger.ONE) ==0) {
					if (rList.contains(x)) {
						//System.out.println("Already has it");
					} else {
						
						for (int i = 0; i < facBase.size(); i++) {
							if (((inMatrix[rList.size()][i]) % 2) == 0) {
								boolString += "0";
							} else {
								boolString += "1";
							}
						}
						if (inMatrixStringTable.contains(boolString)) {
							System.out.println("Already has equivalent");
						} else {
						//	if (((L - rList.size())%10) ==0)System.out.println((L - rList.size()) + "Added: " + x);
							rList.add(x);
							added = true;
						}
					}
				}
			}
			
		}
		if (added) {
			inMatrixStringTable.put(boolString, true);
		} else {
			for (int i = 0; i < facBase.size(); i++) {
				inMatrix[rList.size()][i] = 0;
			}
		}
		//System.out.println(inMatrixStringTable.get(boolString));
	}

	public static void makeWrite(){
		try{
			PrintWriter writer = new PrintWriter("in", "UTF-8");
			writer.println(L + " " + facBase.size());
			for (int i = 0; i < L; i++){
				for (int j = 0; j < facBase.size(); j++){
					writer.print(((inMatrix[i][j])) +  " ");
				}
				writer.println();
			}
			writer.close();

		} catch (Exception e) {
		
		}

	}

	public static void execGauss(){
		String[] execArray = new String[]{"./GaussBin.exe", "in", "out"};
		try {
			Process p = Runtime.getRuntime().exec(execArray);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void readOut(){
		try {
			Scanner inFile = new Scanner(new File("out"));
			int i = inFile.nextInt();
			outMatrix = new int[i][(int)L];
			int count = 0;
			int row = 0;
			while (inFile.hasNextInt()){
				i = inFile.nextInt();
				outMatrix[row][count] = i;
				if (count == L-1){
					row++;
					count = 0;
				}
				else
					count++;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

	public static void createSolutionsMatrix(){
		makeWrite();
		execGauss();
		readOut();
	}

	public static BigInteger computePrimes(){
		BigInteger ySquared;
		BigInteger x;
		for (int i = 1; i < outMatrix.length; i++){
			ySquared = BigInteger.ONE;
			x = BigInteger.ONE;
			for (int j = 1; j < outMatrix[1].length; j++){
				if (outMatrix[i][j] != 0){
					for (int k = 0; k < inMatrix[1].length; k++){
						int z = (int)Math.pow(facBase.get(k), inMatrix[j][k]);
						ySquared = ySquared.multiply(BigInteger.valueOf(z));
					}
					x = x.multiply(rList.get(j));
				}
			}
			BigInteger dif = ((squareRoot(ySquared)).subtract(x)).gcd(N);
			if (dif.compareTo(N) != 0 && dif.compareTo(BigInteger.ONE) != 0){
				return dif;
			}
		}
		return BigInteger.ONE;
	}

	public static void main(String[] args){
		long primeTimeStart = System.nanoTime();
		N = new BigInteger(args[0]);
		initFacBase();
		L = facBase.size()+10;
		generateRList();
		createSolutionsMatrix();
		BigInteger factor = computePrimes();

		System.out.println(N.divide(factor) + " " +  factor);
		long primeTimeEnd = System.nanoTime();
		long durationSeconds = (primeTimeEnd - primeTimeStart) / 1000000000;
		System.out.println("Total Execution Time: " + durationSeconds + " seconds");
	}
}
