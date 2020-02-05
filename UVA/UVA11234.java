package UVa;

import java.util.*;
import java.io.*;
import java.text.*;

public class UVa11234 {
	static class Node {
		Node left, right;
		char c;
	}

	static boolean lc(char c) {
		return c >= 'a' && c <= 'z';
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		PrintWriter pw = new PrintWriter(System.out);
		int t = sc.nextInt();
		while (t-- > 0) {
			char[] arr = sc.next().toCharArray();
			Stack<Node> st = new Stack<Node>();
			for (int i = 0; i < arr.length; i++) {
				if (lc(arr[i])) {
					Node node = new Node();
					node.c = arr[i];
					st.add(node);
				} else {
					Node root = new Node();
					root.c = arr[i];
					Node right = st.pop();
					Node left = st.pop();
					root.right = right;
					root.left = left;
					st.add(root);
				}
			}
			Node start = st.pop();
			Queue<Node> q = new LinkedList<Node>();
			q.add(start);
			StringBuilder sb = new StringBuilder();
			while (!q.isEmpty()) {
				Node cur = q.poll();
				sb.append(cur.c);
				if (cur.left != null)
					q.add(cur.left);
				if (cur.right != null)
					q.add(cur.right);
			}
			pw.println(sb.reverse());
		}
		pw.close();
	}

	static class Scanner {
		StringTokenizer st;
		BufferedReader br;

		public Scanner(InputStream s) {
			br = new BufferedReader(new InputStreamReader(s));
		}

		public Scanner(FileReader r) {
			br = new BufferedReader(r);
		}

		public String next() throws IOException {
			while (st == null || !st.hasMoreTokens())
				st = new StringTokenizer(br.readLine());
			return st.nextToken();
		}

		public int nextInt() throws IOException {
			return Integer.parseInt(next());
		}

		public long nextLong() throws IOException {
			return Long.parseLong(next());
		}

		public String nextLine() throws IOException {
			return br.readLine();
		}

		public double nextDouble() throws IOException {
			String x = next();
			StringBuilder sb = new StringBuilder("0");
			double res = 0, f = 1;
			boolean dec = false, neg = false;
			int start = 0;
			if (x.charAt(0) == '-') {
				neg = true;
				start++;
			}
			for (int i = start; i < x.length(); i++)
				if (x.charAt(i) == '.') {
					res = Long.parseLong(sb.toString());
					sb = new StringBuilder("0");
					dec = true;
				} else {
					sb.append(x.charAt(i));
					if (dec)
						f *= 10;
				}
			res += Long.parseLong(sb.toString()) / f;
			return res * (neg ? -1 : 1);
		}

		public boolean ready() throws IOException {
			return br.ready();
		}

	}
}
