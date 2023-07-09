public class HelloNumbers{
	public static int sum(int n){
		if (n == 0){
			return n;
		} else {
			return n + sum(n-1);
		}
	}
	public static void main(String[] args){
		int x = 0;
		while (x < 10){
			System.out.print(sum(x) + " ");
			x = x + 1;
		}
	}
}
