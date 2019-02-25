package com.mc.cache;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.mc.exception.CityConnectivityException;

 
@Component
public class CityConnectivityMap implements CityMap {

	private boolean[][] cityMatrix = null;

	final private Map<String, Integer> cityIndexMap = new LinkedHashMap<String, Integer>(1024);
	final private Map<Integer, Node> cityLinkedMap = new LinkedHashMap<Integer, Node>(1024);

	final private Map<Integer, String> indexCityMap = new LinkedHashMap<Integer, String>(1024);

	@SuppressWarnings("unused")
	private Integer size = 0;
	private int counter = 0;

	final private double LOAD_FACTOR = 1.6;

	public CityConnectivityMap() {
		super();
	}

	public CityConnectivityMap(Integer size) {
		this.size = size;
	}

	static class Node {
		int columnIndex;
		Node next;

		public Node(int conlumnIndex) {
			this.columnIndex = conlumnIndex;
		}

		public int getColumnIndex() {
			return this.columnIndex;
		}
	}

	/**
	 * please make sure, city map will have valid values.
	 * 
	 * @param key
	 * @param value
	 */
	public void put(String key, String value) {
		if (key == null || value == null) {
			System.out.println(" key : " + key + ", value : " + value);
			throw new IllegalArgumentException();
		}
		buildEntry(key, value);
	}

	@Override
	public String isConnected(String src, String dest) throws CityConnectivityException{
		boolean found = false;
		List<String> isVisited = new ArrayList<String>();
		if (src.equals(dest)) {
			return "yes";
		}
		Integer rowNum = cityIndexMap.get(src);
		if (rowNum == null) {
			return "no";
		}
		try {
			boolean isConnected = isConnected(src, dest, rowNum, isVisited, found);
			if (isConnected) {
				return "yes";
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CityConnectivityException("connectivity check operation failed");
		}
		return "no";
	}

	@Override
	public String isConnectedQuickCheck(String src, String dest) throws CityConnectivityException {
		boolean found = false;
		List<String> isVisited = new ArrayList<String>();
		if (src.equals(dest)) {
			return "yes";
		}
		Integer rowNum = cityIndexMap.get(src);
		if (rowNum == null) {
			return "no";
		}
		try {
			boolean isConnected = isConnectedQuickCheck(src, dest, rowNum, isVisited, found);
			if (isConnected) {
				return "yes";
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new CityConnectivityException("connectivity check operation failed");
		}
		return "no";
	}

	/**
	 * please make sure, city map will have valid values.
	 * 
	 * @param key
	 * @param value
	 */

	public boolean isConnected(String src, String dest, int rowStart, List<String> isVisited, boolean found) {
		// System.out.println(src + " # " + dest); //used for debug, trace path
		for (int cCnt = 0; cCnt < cityIndexMap.size() && (found == false); cCnt++) {
			String destCity = indexCityMap.get(cCnt);
			// System.out.println(src + " # " + destCity); //used for debug, trace path

			if (cityMatrix[rowStart][cCnt] == true && !isVisited.contains(destCity)) {
				isVisited.add(destCity);

				if (destCity == null) {
					found = false;
					break;
				}
				if (destCity.equals(dest)) {
					found = true;
					break;
				} else {
					found = isConnected(destCity, dest, cCnt, isVisited, found);
					if (found == true) {
						break;
					}
				}
			}
		}
		return found;
	}

	public boolean isConnectedQuickCheck(String src, String dest, int rowStart, List<String> isVisited, boolean found) {
		Node nd = cityLinkedMap.get(rowStart);
		while (nd != null && (found == false)) {

			int cCnt = nd.getColumnIndex();
			String destCity = indexCityMap.get(cCnt);

			if (!isVisited.contains(destCity)) {
				isVisited.add(destCity);

				if (destCity == null) {
					found = false;
					break;
				}
				if (destCity.equals(dest)) {
					found = true;
					break;
				} else {
					found = isConnected(destCity, dest, cCnt, isVisited, found);
					if (found == true) {
						break;
					}
				}
			}
			nd = nd.next;
		}
		return found;
	}

	private Map<String, Integer> initCityIndexMap(String srcCity, String destCity) {
		if (!cityIndexMap.containsKey(srcCity)) {
			cityIndexMap.put(srcCity, counter);
			indexCityMap.put(counter, srcCity);
			counter = counter + 1;
		}
		if (!cityIndexMap.containsKey(destCity)) {
			cityIndexMap.put(destCity, counter);
			indexCityMap.put(counter, destCity);
			counter = counter + 1;
		}
		return cityIndexMap;
	}

	private boolean[][] initBasicEntries(boolean[][] cityMatrix, int fromIndex, int toIndex) {
		for (int iCnt = fromIndex; iCnt <= toIndex; iCnt++) {
			for (int jCnt = fromIndex; jCnt <= toIndex; jCnt++) {
				if (iCnt == jCnt) {
					cityMatrix[iCnt][jCnt] = true;
				} else {
					cityMatrix[iCnt][jCnt] = false;
				}
			}
		}
		return cityMatrix;
	}

	private boolean[][] copyMatrix(boolean[][] srcCityMatrix, boolean[][] destCityMatrix) {
		for (int iCnt = 0; iCnt < srcCityMatrix.length; iCnt++) {
			for (int jCnt = 0; jCnt < srcCityMatrix.length; jCnt++) {
				destCityMatrix[iCnt][jCnt] = srcCityMatrix[iCnt][jCnt];
			}
		}
		return destCityMatrix;
	}

	/**
	 * Added make `link list` inside the matrix to directly connect the `correct
	 * nodes`(path) inside each row.
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	private boolean[][] buildEntry(String key, String value) {

		initCityIndexMap(key, value);

		if (cityMatrix == null) {
			cityMatrix = new boolean[1][1];
			initBasicEntries(cityMatrix, 0, cityMatrix.length - 1);
		}

		int rowCnt = cityIndexMap.get(key);
		int columnCnt = cityIndexMap.get(value);

		if (columnCnt >= cityMatrix.length) {
			int newSize = (int) (cityMatrix.length * LOAD_FACTOR) + 2;
			boolean[][] tempCityMatrx = new boolean[newSize][newSize];
			copyMatrix(cityMatrix, tempCityMatrx);
			initBasicEntries(tempCityMatrx, cityMatrix.length, tempCityMatrx.length - 1);
			cityMatrix = tempCityMatrx;
		}

		if (cityLinkedMap.get(rowCnt) != null) {
			Node nd = cityLinkedMap.get(rowCnt);
			nd.next = new Node(columnCnt);
		} else {
			cityLinkedMap.put(rowCnt, new Node(columnCnt));
		}

		if (cityLinkedMap.get(columnCnt) != null) {
			Node nd = cityLinkedMap.get(rowCnt);
			nd.next = new Node(rowCnt);
		} else {
			cityLinkedMap.put(columnCnt, new Node(rowCnt));
		}

		cityMatrix[rowCnt][columnCnt] = true;
		cityMatrix[columnCnt][rowCnt] = true;

		return cityMatrix;
	}

	public boolean[][] getMatrixTable() {
		return cityMatrix;
	}

	public static void print(boolean[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if (j + 1 == matrix.length) {
					if (matrix[i][j]) {
						System.out.println(1);
					} else {
						System.out.println(0);
					}
				} else {
					if (matrix[i][j]) {
						System.out.print(1 + " ");
					} else {
						System.out.print(0 + " ");
					}

				}
			}
		}
	}

	public static void main(String[] args) {
		CityConnectivityMap map = new CityConnectivityMap();
		map.put("A", "B");
		map.put("B", "F");
		map.put("C", "D");
		map.put("D", "E");
		map.put("E", "F");
		map.put("F", "G");
		map.put("G", "F");
		map.put("G", "I");

		map.put("Boston", "New York");
		map.put("Philadelphia", "Newark");
		map.put("Newark", "Boston");
		map.put("Trenton", "Albany");

		boolean[][] ans = map.getMatrixTable();
		System.out.println();
		CityConnectivityMap.print(ans);
		System.out.println("----------------------------------");

		long st = System.nanoTime();
		System.out.println(map.isConnected("G", "I"));
		long ed = System.nanoTime();
		System.out.println(map.isConnectedQuickCheck("A", "I"));
		long ed1 = System.nanoTime();
		System.out.println("first call " + (ed - st));
		System.out.println("second call " + (ed1 - ed));

		System.out.println(map.isConnected("I", "A"));

		System.out.println(map.isConnectedQuickCheck("New York1", "Newark"));

	}

}
