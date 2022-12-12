import java.util.Scanner;

public class PLD {

	public char[] expanded;
	public int[][] big;
	public int[][] little;
	char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
			't', 'u', 'v', 'w', 'x', 'y', 'z' };
	int placement = 0;
	int count = 0;
	char letter = 'a';

	public int[][] Expansion(char[] expanded, boolean polynomial) {

		int L = expanded.length;
		int sCount, tCount, eCount, eTerms;
		sCount = tCount = eCount = eTerms = 0;
		int sQ = -1; // separator quantity
		boolean rCount = false;
		boolean fVar = false;
		boolean varCheck = false;
		char[] Tseparator = { '+', '-', '(', ')' };
		char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
				's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

		for (int i = 0; i < L; i++) {
			if (fVar == true) {
				fVar = false;
				break;
			}
			for (int j = 0; j < 26; j++) {
				if (expanded[i] == alphabet[j]) {
					letter = expanded[i];
					fVar = true;
					break;
				}
			}
		}

		for (int i = 0; i < L; i++) {
			for (int j = 0; j < 4; j++) {
				if (expanded[i] == Tseparator[j]) {
					if (Tseparator[j] == ')') {
						rCount = true;
						sCount++;
					} else {
						sCount++;
					}

				}
				if (sCount == 2) {
					if (rCount == true) {
						tCount++;
						sCount = 0;
						rCount = false;
					} else {
						tCount++;
						sCount = 1;
					}
				}
			}
		}

		boolean[] signs = new boolean[tCount]; // false when negative, true when positive
		boolean check = false;
		int count = 0;

		for (int i = 0; i < L; i++) {
			if (expanded[i] == '(') {
				check = true;
				continue;
			}
			if (check == true && expanded[i] == '-') {
				signs[count] = false;
				check = false;
				count++;
				continue;
			} else if (check == true && expanded[i] != '-') {
				signs[count] = true;
				check = false;
				count++;
				continue;
			}
			if (expanded[i] == '+') {
				signs[count] = true;
				count++;
			} else if (expanded[i] == '-') {
				signs[count] = false;
				count++;
			}
		}

		int[][] ieV = new int[tCount][2];
		boolean DDigit = false;
		boolean closed = false;
		boolean caret = false;
		varCheck = false;

		for (int i = 0; i < L; i++) {
			if (expanded[i] == '+' || expanded[i] == '-' || expanded[i] == '(') {
				sQ++;
				if (closed == false) {
					eTerms = sQ + 1;
				}
				varCheck = false;
				DDigit = false;
				caret = false;
				continue;
			} else if (expanded[i] == ')') {
				closed = true;
				continue;
			}
			if (Character.isDigit(expanded[i])) {
				if (varCheck == false) {
					if (DDigit == false) {
						ieV[sQ][0] = expanded[i] - 48;
						DDigit = true;
						continue;
					} else {
						ieV[sQ][0] = (ieV[sQ][0] * 10) + (expanded[i] - 48);
						DDigit = false;
						continue;
					}
				} else if (varCheck == true && caret == true) {
					if (DDigit == false) {
						ieV[sQ][1] = expanded[i] - 48;
						DDigit = true;
						continue;
					} else {
						ieV[sQ][1] = (ieV[sQ][1] * 10) + (expanded[i] - 48);
						DDigit = false;
						continue;
					}
				}
			}
			if (expanded[i] == letter) {
				varCheck = true;
				DDigit = false;
				ieV[sQ][1] = 1;
				if (ieV[sQ][0] == 0) {
					ieV[sQ][0] = 1;
				}
				continue;
			}
			if (expanded[i] == '^') {
				caret = true;
				continue;
			}

		}

		for (int i = 0; i < tCount; i++) {
			if (signs[i] == false) {
				ieV[i][0] = ieV[i][0] * -1;
			}
		}

		int fi = eTerms;
		int la = tCount - fi;
		int tc2 = fi * la; // crucial variable, tc2 is number of terms before simplification

		int[][] expansion = new int[tc2][2];

		for (int i = 0; i < fi; i++) {
			for (int j = fi; j < tCount; j++) {
				expansion[eCount][0] = ieV[i][0] * ieV[j][0];
				expansion[eCount][1] = ieV[i][1] + ieV[j][1];
				eCount++;
			}
		}

		System.out.println();
		System.out.println("Expanded Expression:");

		boolean one = false;
		// prints out the current equation before simplification
		for (int i = 0; i < tc2; i++) {
			one = false;
			if (i == 0) {
				if (expansion[i][0] == 1) {
					one = true;
				} else if (expansion[i][0] == 0) {
					continue;
				} else if (expansion[i][0] == -1) {
					System.out.print("-");
					one = true;
				} else if (expansion[i][0] > 1 || expansion[i][0] < -1) {
					System.out.print(expansion[i][0]);
				}
				if (expansion[i][1] == 0 && one == true) {
					System.out.print("1");
					one = false;
				} else if (expansion[i][1] == 1) {
					System.out.print(letter);
				} else if (expansion[i][1] > 1) {
					System.out.print(letter + "^" + expansion[i][1]);
				}
			} else {
				if (expansion[i][0] == 1) {
					System.out.print(" + ");
					one = true;
				} else if (expansion[i][0] == 0) {
					continue;
				} else if (expansion[i][0] == -1) {
					System.out.print(" - ");
					one = true;
				} else if (expansion[i][0] > 1) {
					System.out.print(" + " + expansion[i][0]);
				} else if (expansion[i][0] < -1) {
					System.out.print(" - " + (expansion[i][0] * -1));
				}
				if (expansion[i][1] == 0 && one == true) {
					System.out.print("1");
				} else if (expansion[i][1] == 1) {
					System.out.print(letter);
				} else if (expansion[i][1] > 1) {
					System.out.print(letter + "^" + expansion[i][1]);
				}
			}
		}
		return expansion;
	}

	public int[][] store(char[] expanded, boolean polynomial) {
		int L = expanded.length;
		int terms = 1;

		for (int i = 0; i < L; i++) {
			if (i > 0) {
				if (expanded[i] == '+' || expanded[i] == '-') {
					terms++;
				}
			}
		}
		int[][] expansion = new int[terms][2];
		boolean[] signs = new boolean[terms]; // false when negative, true when positive;
		int count = 0;
		boolean varCheck, DDigit, caret, fVar;
		varCheck = DDigit = caret = fVar = false;

		for (int i = 0; i < L; i++) {
			if (fVar == true) {
				fVar = false;
				break;
			}
			for (int j = 0; j < 26; j++) {
				if (expanded[i] == alphabet[j]) {
					letter = expanded[i];
					fVar = true;
					break;
				}
			}
		}

		for (int i = 0; i < L; i++) {
			if (i == 0) {
				if (expanded[i] == '-') {
					signs[count] = false;
					count++;
				} else {
					signs[count] = true;
					count++;
				}
			}
			if (i > 0) {
				if (expanded[i] == '-') {
					signs[count] = false;
					count++;
				} else if (expanded[i] == '+') {
					signs[count] = true;
					count++;
				}
			}
		}

		count = 0;
		for (int i = 0; i < L; i++) {
			if (i > 0 && (expanded[i] == '+' || expanded[i] == '-')) {
				count++;
				expansion[count][1] = expansion[count][0] = 0;
				varCheck = false;
				DDigit = false;
				caret = false;
				continue;
			}
			if (Character.isDigit(expanded[i])) {
				if (varCheck == false) {
					if (DDigit == false) {
						expansion[count][0] = expanded[i] - 48;
						DDigit = true;
						continue;
					} else {
						expansion[count][0] = (expansion[count][0] * 10) + (expanded[i] - 48);
						DDigit = false;
						continue;
					}
				} else if (varCheck == true && caret == true) {
					if (DDigit == false) {
						expansion[count][1] = expanded[i] - 48;
						DDigit = true;
						continue;
					} else {
						expansion[count][1] = (expansion[count][1] * 10) + (expanded[i] - 48);
						DDigit = false;
						continue;
					}
				}
			}
			if (expanded[i] == letter) {
				varCheck = true;
				DDigit = false;
				expansion[count][1] = 1;
				if (expansion[count][0] == 0) {
					expansion[count][0] = 1;
				}
				continue;
			}
			if (expanded[i] == '^') {
				caret = true;
				continue;
			}

		}

		for (int i = 0; i < terms; i++) {
			if (signs[i] == false) {
				expansion[i][0] = expansion[i][0] * -1;
			}
		}

		return expansion;
	}

	public int[][] simplify(int[][] expanded, boolean polynomial) {

		int tc2 = expanded.length;
		int[] sorted = new int[tc2];
		int[] unsorted = new int[tc2];

		for (int i = 0; i < tc2; i++) {
			unsorted[i] = expanded[i][1];
		}

		for (int i = 0; i < tc2; i++) {
			for (int j = i + 1; j < tc2; j++) {

				if (expanded[i][1] > expanded[j][1]) {

					placement = expanded[i][1];
					expanded[i][1] = expanded[j][1];
					expanded[j][1] = placement;

				}
			}
			sorted[i] = expanded[i][1];

		}

		int[] ph = new int[tc2]; // placeholders array
		int[] coefficients = new int[tc2];
		boolean used = false;
		boolean esc = false;

		for (int i = 0; i < tc2; i++) {
			ph[i] = tc2;
		}

		for (int i = 0; i < tc2; i++) {
			esc = false;
			for (int j = 0; j < tc2; j++) {
				used = false;
				if (esc == true) {
					break;
				}
				if (unsorted[j] == sorted[i]) {
					for (int k = 0; k < tc2; k++) {
						if (ph[k] == j) {
							used = true;
						}
						if (k == tc2 - 1 && used == false) {
							coefficients[i] = expanded[j][0];
							ph[i] = j;
							esc = true;
						}
					}
				}

			}
		}

		int sTerms = 1; // determines how many terms there should be once the expression is simplified

		for (int i = 1; i < tc2; i++) {
			if (sorted[i] == sorted[i - 1]) {

			} else {
				sTerms++;
			}
		}

		count = 0;
		int[][] simplified = new int[sTerms][2];

		for (int i = 0; i < tc2; i++) {
			if (i == 0) {
				simplified[count][1] = sorted[i];
				simplified[count][0] = coefficients[i];
			} else if (i > 0) {
				if (sorted[i] == sorted[i - 1]) {
					simplified[count][0] = simplified[count][0] + coefficients[i];
				} else {
					count++;
					simplified[count][1] = sorted[i];
					simplified[count][0] = coefficients[i];
				}
			}
		}

		System.out.println();
		System.out.println();
		System.out.println("Simplified Expression:");

		// prints out the equation after simplification
		boolean one = false;
		for (int i = sTerms - 1; i >= 0; i--) {
			one = false;
			if (i == sTerms - 1) {
				if (simplified[i][0] == 1) {
					one = true;
				} else if (simplified[i][0] == 0) {
					continue;
				} else if (simplified[i][0] == -1) {
					System.out.print("-");
					one = true;
				} else if (simplified[i][0] > 1 || simplified[i][0] < -1) {
					System.out.print(simplified[i][0]);
				}
				if (simplified[i][1] == 0 && one == true) {
					System.out.print("1");
					one = false;
				} else if (simplified[i][1] == 1) {
					System.out.print(letter);
				} else if (simplified[i][1] > 1) {
					System.out.print(letter + "^" + simplified[i][1]);
				}
			} else {
				if (simplified[i][0] == 1) {
					System.out.print(" + ");
					one = true;
				} else if (simplified[i][0] == 0) {
					continue;
				} else if (simplified[i][0] == -1) {
					System.out.print(" - ");
					one = true;
				} else if (simplified[i][0] > 1) {
					System.out.print(" + " + simplified[i][0]);
				} else if (simplified[i][0] < -1) {
					System.out.print(" - " + (simplified[i][0] * -1));
				}
				if (simplified[i][1] == 0 && one == true) {
					System.out.print("1");
				} else if (simplified[i][1] == 1) {
					System.out.print(letter);
				} else if (simplified[i][1] > 1) {
					System.out.print(letter + "^" + simplified[i][1]);
				}
			}
		}
		System.out.println();

		if (polynomial == true) {
			big = new int[sTerms][2];
			for (int i = 0; i < sTerms; i++) {
				big[i][0] = simplified[i][0];
				big[i][1] = simplified[i][1];
			}
			return big;
		} else {
			little = new int[sTerms][2];
			for (int i = 0; i < sTerms; i++) {
				little[i][0] = simplified[i][0];
				little[i][1] = simplified[i][1];
			}
			return little;
		}
	}

	public void PLD(int[][] dd, int[][] dr) {

		int count, count2;
		count = count2 = 0;
		int table = 0;
		int answer = 1;
		int rm = 2;

		int[] degree = new int[2];

		for (int i = 0; i < dd.length; i++) {
			if (dd[i][0] != 0) {
				count++;
			}
		}
		for (int i = 0; i < dr.length; i++) {

			if (dr[i][0] != 0) {
				count2++;
			}
		}

		int[][] dividend = new int[count][2];
		int[][] divisor = new int[count2][2];
		count = 0;
		count2 = 0;

		for (int i = 0; i < dd.length; i++) {
			if (dd[i][0] == 0) {
				continue;
			} else {
				dividend[count][0] = dd[i][0];
				dividend[count][1] = dd[i][1];
				count++;
			}
		}
		for (int i = 0; i < dr.length; i++) {
			if (dr[i][0] == 0) {
				continue;
			} else {
				divisor[count2][0] = dr[i][0];
				divisor[count2][1] = dr[i][1];
				count2++;
			}

		}

		for (int i = 0; i < dividend.length; i++) {
			degree[0] = dividend[dividend.length - 1][1];
		}

		for (int i = 0; i < divisor.length; i++) {
			degree[1] = divisor[divisor.length - 1][1];
		}
		if (degree[1] > degree[0]) {
			System.out.println();
			System.out.println(
					"The degree of the dividend is less than the degree of the divisor, so PLD can't be performed");

		}
		int L = dividend.length;

		print(dividend, divisor, table);
		System.out.println();

		int[][] filleD = new int[degree[0] + 1][2];

		// compares degrees in dividend and sets new arrays to integer value 0 if no
		// exponent value is found
		count = 0;
		for (int i = 0; i <= degree[0]; i++) {
			for (int j = 0; j < L; j++) {
				if (dividend[j][1] == i) {
					filleD[i][0] = dividend[j][0];
					filleD[i][1] = dividend[j][1];
					count++;
					break;
				} else if (j == L - 1 && dividend[j][1] != i) {
					filleD[i][0] = 0;
					filleD[i][1] = i;
					count++;
				}
			}
		}

		print(filleD, divisor, table);

		int[][] multiply = new int[divisor.length][2]; // temporary array, will change every step
		int[][] subtract = new int[divisor.length][2];
		int[][] divide = new int[degree[0] - degree[1] + 1][2]; // permanent array, will hold answer
		int[][] empty = new int[0][0];
		int[][] remainder = new int[divisor.length - 1][2];
		boolean s1 = true;
		int step = 0;

		count = degree[0] - degree[1];

		if (s1 == true) {
			int x = filleD.length - 1;
			for (int i = 0; i < divisor.length; i++) {
				subtract[i][0] = filleD[x][0];
				subtract[i][1] = filleD[x][1];
				x--;
			}
			s1 = false;
		}
		for (int i = count; i >= 0; i--) {
			divide[i][0] = subtract[0][0] / divisor[divisor.length - 1][0];
			divide[i][1] = subtract[0][1] - divisor[divisor.length - 1][1];
			for (int j = 0; j < divisor.length; j++) {
				multiply[j][0] = divide[i][0] * divisor[divisor.length - 1 - j][0];
				multiply[j][1] = divide[i][1] + divisor[divisor.length - 1 - j][1];
			}

			for (int j = 0; j < divisor.length - 1; j++) {
				subtract[j][0] = subtract[j + 1][0] - multiply[j + 1][0];
				subtract[j][1] = subtract[j + 1][1];
			}
			if (i != 0) {
				subtract[divisor.length - 1][0] = filleD[filleD.length - 1 - step - divisor.length][0];
				subtract[divisor.length - 1][1] = filleD[filleD.length - 1 - step - divisor.length][1];
			}
			step++;
		}
		int y = 0;
		for (int i = divisor.length - 2; i >= 0; i--) {
			remainder[i][0] = subtract[y][0];
			remainder[i][1] = subtract[y][1];
			y++;
		}

		print(divide, empty, answer);
		if (divisor.length - 1 != 1 || remainder[0][0] != 0) {
			print(remainder, empty, rm);
			print(divisor, empty, 3);

		}

	}

	public void print(int[][] dd, int[][] dr, int key) {
		int x = dd.length;
		int y = dr.length;
		int h, h2;
		h = h2 = 0;
		boolean one = false;
		if (key == 0) {
			for (int i = 0; i < x; i++) {
				one = false;
				if (dd[i][0] == 1 || dd[i][0] == -1) {
					one = true;
				} else {
					if (dd[i][0] < 10 && dd[i][0] > -10) {
						h++;
					} else if ((dd[i][0] >= 10 && dd[i][0] < 100) || (dd[i][0] <= -10 && dd[i][0] > -100)) {
						h = h + 2;
					} else if ((dd[i][0] >= 100 && dd[i][0] < 1000) || (dd[i][0] <= -100 && dd[i][0] > -1000)) {
						h = h + 3;
					} else {
						h = h + 4;
					}
				}
				if (dd[i][0] < 0 && i == x - 1) {
					h++;
				}
				if (dd[i][1] == 0 && one == true) {
					h++;
				} else if (dd[i][1] == 0 && one == false) {

				} else if (dd[i][1] == 1) {
					h++;
				} else if (dd[i][1] < 10 && dd[i][1] > 1) {
					h = h + 3;
				} else if (dd[i][1] >= 10 && dd[i][1] < 100) {
					h = h + 4;
				} else if (dd[i][1] >= 100 && dd[i][1] < 1000) {
					h = h + 5;
				} else {
					h = h + 6;
				}
				if (i != x - 1) {
					h++;
				}

			}

			for (int i = 0; i < y; i++) {
				one = false;
				if (dr[i][0] == 1 || dr[i][0] == -1) {
					one = true;
				} else {
					if (dr[i][0] < 10 && dr[i][0] > -10) {
						h2++;
					} else if ((dr[i][0] >= 10 && dr[i][0] < 100) || (dr[i][0] <= -10 && dr[i][0] > -100)) {
						h2 = h2 + 2;
					} else if ((dr[i][0] >= 100 && dr[i][0] < 1000) || (dr[i][0] <= -100 && dr[i][0] > -1000)) {
						h2 = h2 + 3;
					} else {
						h2 = h2 + 4;
					}
				}
				if (dr[i][0] < 0 && i == y - 1) {
					h2++;
				}
				if (dr[i][1] == 0 && one == true) {
					h2++;
				} else if (dr[i][1] == 0 && one == false) {

				} else if (dr[i][1] == 1) {
					h2++;
				} else if (dr[i][1] < 10 && dr[i][1] > 1) {
					h2 = h2 + 3;
				} else if (dr[i][1] >= 10 && dr[i][1] < 100) {
					h2 = h2 + 4;
				} else if (dr[i][1] >= 100 && dr[i][1] < 1000) {
					h2 = h2 + 5;
				} else {
					h2 = h2 + 6;
				}
				if (i != y - 1) {
					h2++;
				}

			}
			System.out.println();
			System.out.print(' ');
			for (int i = 0; i < h2; i++) {
				System.out.print(' ');
			}
			for (int i = 0; i < h; i++) {
				System.out.print('_');
			}
			System.out.println();
			for (int i = y - 1; i >= 0; i--) {
				one = false;
				if (dr[i][0] == 1 || dr[i][0] == -1) {
					one = true;
				}
				if (i == y - 1 && dr[i][0] == -1) {
					System.out.print("-");
				} else if (dr[i][0] != 1 && i == y - 1) {
					System.out.print(dr[i][0]);
				} else if (i != y - 1) {
					if (dr[i][0] > 1) {
						System.out.print("+" + dr[i][0]);
					} else if (dr[i][0] == 1) {
						System.out.print("+");
					} else if (dr[i][0] == -1) {
						System.out.print("-");
					} else {
						System.out.print("-" + (dr[i][0] * -1));
					}
				}
				if (dr[i][1] == 1) {
					System.out.print(letter);
				} else if (dr[i][1] == 0 && one == true) {
					System.out.print("1");
				} else if (dr[i][1] == 0 && one == false) {

				} else {
					System.out.print(letter + "^" + dr[i][1]);
				}
			}

			System.out.print("|");
		}

		if (key == 0 || key == 1 || key == 2 || key == 3) {
			if (key == 1) {
				System.out.println();
				System.out.println();
				System.out.print("Answer: ");
			}
			if (key == 2) {
				if (dd[x-1][0] > 0) {
				System.out.print("+");
				}
				if (dd.length != 1) {
					System.out.print("(");
				}
			}
			for (int i = x - 1; i >= 0; i--) {
				one = false;
				if (dd[i][0] == 1 || dd[i][0] == -1) {
					one = true;
				}
				if (i == x - 1 && dd[i][0] == -1) {
					System.out.print("-");
				} else if (dd[i][0] != 1 && i == x - 1) {
					System.out.print(dd[i][0]);
				} else if (i != x - 1) {
					if (dd[i][0] > 1 || dd[i][0] == 0) {
						System.out.print("+" + dd[i][0]);
					} else if (dd[i][0] == 1) {
						System.out.print("+");
					} else if (dd[i][0] == -1) {
						System.out.print("-");
					} else {
						System.out.print("-" + (dd[i][0] * -1));
					}
				}
				if (dd[i][1] == 1) {
					System.out.print(letter);
				} else if (dd[i][1] == 0 && one == true) {
					System.out.print("1");
				} else if (dd[i][1] == 0 && one == false) {

				} else {
					System.out.print(letter + "^" + dd[i][1]);
				}
			}
			if (key == 2) {
				if (dd.length != 1) {
						System.out.print(")/(");
				}
				else {
				System.out.print("/(");
				}
			} else if (key == 3) {
				System.out.print(")");
			}
		}
		

	}

	public static void main(String[] args) {
	
		
		boolean Pdetect, P2detect, PE, PE2, fVar, drb, dcheck, dcheck2, error, quit;
		Pdetect = P2detect = PE = PE2 = fVar = drb = dcheck = dcheck2 = error = quit = false;
		int spaces, spaces2, closedcount, closedcount2;
		char letter = 'a';
		spaces = spaces2 = closedcount = closedcount2 = 0;
		boolean ddb = true;
		
		PLD one = new PLD();
		int[][] dd2;
		int[][] dr2;
		
		while (!quit) {
		System.out.print("Enter a polynomial dividend (type 'quit' to quit): ");
		Scanner s = new Scanner(System.in);
		String dString = s.nextLine();
		int dl = dString.length();
		if (dString.equalsIgnoreCase("quit")) {
			quit = true;
			break;
		}

		System.out.print("Enter a polynomial divisor: ");
		Scanner s2 = new Scanner(System.in);
		String dvString = s2.nextLine();
		int dvl = dvString.length();

		char[] da = dString.toCharArray();
		char[] dva = dvString.toCharArray();
		char[] alphabet = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
				's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };

		// creates a new array without spaces (if any are inputted)
		for (int i = 0; i < dl; i++) {
			if (da[i] == ' ') {
				spaces = spaces + 1;
			}
		}
		for (int i = 0; i < dvl; i++) {
			if (dva[i] == ' ') {
				spaces2 = spaces2 + 1;
			}
		}

		int L = dl - spaces; // length of dividend without spaces
		int L2 = dvl - spaces2;
		char[] dividend = new char[L];
		char[] divis = new char[L2];

		int count = 0;
		for (int i = 0; i < dl; i++) {
			if (da[i] == ' ') {
				continue;
			} else {
				dividend[count] = da[i];
				count++;
			}
		}
		count = 0;
		for (int i = 0; i < dvl; i++) {
			if (dva[i] == ' ') {
				continue;
			} else {
				divis[count] = dva[i];
				count++;
			}
		}

		for (int i = 0; i < L; i++) {
			if (dividend[i] == '(') {
				Pdetect = true;
				continue;
			}
			if (dividend[i] == ')' && Pdetect == true) {
				P2detect = true;
				Pdetect = false;
			}
			while (P2detect == true) {
				closedcount++;
				P2detect = false;
			}
			if (Character.isDigit(dividend[i]) && dcheck == false && dcheck2 == false) {
				dcheck = true;
			} else if (Character.isDigit(dividend[i]) && dcheck == true) {
				dcheck = false;
				dcheck2 = true;
			} else if (Character.isDigit(dividend[i]) && dcheck == false && dcheck2 == true) {
				System.out.println("Error: polynomial can not contain more than 2 digits in the integer or the exponent");
				error = true;
			} else {
				dcheck = false;
				dcheck2 = false;
			}
		}
		if (error == false) {
			for (int i = 0; i < L2; i++) {
				if (divis[i] == '(') {
					Pdetect = true;
					continue;
				}
				if (divis[i] == ')' && Pdetect == true) {
					P2detect = true;
					Pdetect = false;
				}
				while (P2detect == true) {
					closedcount2++;
					P2detect = false;
				}
			}

			for (int i = 0; i < L; i++) {
				if (i > 0) {
					if (dividend[i] == '(' && dividend[i - 1] == ')' && closedcount >= 2) {
						closedcount--;
						PE = true;
						break;

					} else if (closedcount == 0 || closedcount == 1) {
						PE = false;
						break;
					}
				}
			}

			for (int i = 0; i < L2; i++) {
				if (i > 0) {
					if (divis[i] == '(' && divis[i - 1] == ')' && closedcount2 >= 2) {
						closedcount2--;
						PE2 = true;
						break;
					} else if (closedcount2 == 0 || closedcount2 == 1) {
						PE2 = false;
						break;
					}
				}
			}

			if (PE == true) {
				System.out.println();
				System.out.println("Dividend requires polynomial expansion");
				int[][] dd = one.Expansion(dividend, ddb);
				dd2 = one.simplify(dd, ddb);
			} else {
				System.out.println();
				System.out.println("Dividend does not require polynomial expansion");
				int[][] dd = one.store(dividend, ddb);
				dd2 = one.simplify(dd, ddb);
			}
			if (PE2 == true) {
				System.out.println();
				System.out.println("Divisor requires polynomial expansion");
				int[][] dr = one.Expansion(divis, drb);
				dr2 = one.simplify(dr, drb);
			} else {
				System.out.println();
				System.out.println("Divisor does not require polynomial expansion");
				int[][] dr = one.store(divis, drb);
				dr2 = one.simplify(dr, drb);
			}

			one.PLD(dd2, dr2);

		
			for (int i = 0; i < dl; i++) {
				if (fVar == true) {
					fVar = false;
					break;
				}
				for (int j = 0; j < 26; j++) {
					if (da[i] == alphabet[j]) {
						letter = da[i];
						fVar = true;
						break;
					}
				}
			}
		}
		System.out.println();
		System.out.println();
	}

	}
}
