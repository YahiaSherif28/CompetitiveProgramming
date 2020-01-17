import java.util.*;

//import java.io.*;
import java.text.*;

public class PalindromizationDiv1 {

//	static HashMap<Character, Integer> erase, add;
	static int[][] change;
	static char[] word;

	static int[][] dp;

	public static int dp(int l, int r) {
		if (l >= r)
			return 0;

		if (dp[l][r] != -1)
			return dp[l][r];
		int ans = (int) 1e9;
		// change then erase
		if (change[word[l] - 'a'][26] != (int) 1e9)
			ans = Math.min(ans, dp(l + 1, r) + change[word[l] - 'a'][26]);

		if (change[word[r] - 'a'][26] != (int) 1e9)
			ans = Math.min(ans, dp(l, r - 1) + change[word[r] - 'a'][26]);

		// change left add right
		for (char c = 'a'; c <= 'z'; c++) {
			if (change[26][c - 'a'] == (int) 1e9)
				continue;
			if (change[word[l] - 'a'][c - 'a'] == (int) 1e9)
				continue;
			ans = Math.min(ans, change[word[l] - 'a'][c - 'a'] + change[26][c - 'a'] + dp(l + 1, r));
		}
		// change right add left
		for (char c = 'a'; c <= 'z'; c++) {
			if (change[26][c - 'a'] == (int) 1e9)
				continue;
			if (change[word[r] - 'a'][c - 'a'] == (int) 1e9)
				continue;
			ans = Math.min(ans, change[word[r] - 'a'][c - 'a'] + change[26][c - 'a'] + dp(l, r - 1));
		}
		// change right & left
		for (char c = 'a'; c <= 'z'; c++) {
			if (change[word[r] - 'a'][c - 'a'] == (int) 1e9)
				continue;
			if (change[word[l] - 'a'][c - 'a'] == (int) 1e9)
				continue;
			ans = Math.min(ans, change[word[r] - 'a'][c - 'a'] + change[word[l] - 'a'][c - 'a'] + dp(l + 1, r - 1));

		}
		return dp[l][r] = ans;

	}

	public static int getMinimumCost(String s, String[] arr) {
		change = new int[27][27];
		dp = new int[s.length() + 1][s.length() + 1];
		for (int[] x : dp)
			Arrays.fill(x, -1);
		word = s.toCharArray();
		for (int[] x : change)
			Arrays.fill(x, (int) 1e9);
		for (int i = 0; i < 27; i++)
			change[i][i] = 0;
		for (String x : arr) {
			StringTokenizer st = new StringTokenizer(x);
			String type = st.nextToken();
			if (type.equals("add")) {
				char c = st.nextToken().charAt(0);
				int w = Integer.parseInt(st.nextToken());
				change[26][c - 'a'] = w;

			} else if (type.equals("erase")) {
				char c = st.nextToken().charAt(0);
				int w = Integer.parseInt(st.nextToken());
				change[c - 'a'][26] = w;

			} else {
				char c1 = st.nextToken().charAt(0);
				char c2 = st.nextToken().charAt(0);
				change[c1 - 'a'][c2 - 'a'] = Math.min(change[c1 - 'a'][c2 - 'a'], Integer.parseInt(st.nextToken()));
			}
		}
		for (int k = 0; k < 27; k++)
			for (int i = 0; i < 27; i++)
				for (int j = 0; j < 27; j++)
					if (change[i][j] > change[i][k] + change[k][j]) {
						change[i][j] = change[i][k] + change[k][j];
					}
		return dp(0, s.length() - 1) == (int) 1e9 ? -1 : dp(0, s.length() - 1);

	}

	public static void main(String[] args) {
//		Scanner sc = new Scanner(System.in);
//		PrintWriter pw = new PrintWriter(System.out);
//		String s = "caaab";
//		String[] arr = {"change b a 100000", "change c a 100000", "change c d 50000", "change b e 50000", "erase d 50000", "erase e 49999"};
//		pw.println(getMinimumCost(s, arr));
//		for(int i=0;i<27;i++) {
//			for(int j=0;j<27;j++)
//				pw.print((change[i][j]==(int)1e9?-1:change[i][j])+" ");
//			pw.println();
//		}
//		pw.println(change['e'-'a'][26]);
//		pw.println(dp(0, s.length()-1));
//		pw.println(erase);
//		pw.println(add);
//		pw.close();
	}

}
