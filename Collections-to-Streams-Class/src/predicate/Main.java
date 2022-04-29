package predicate;

public class Main {

	public static void main(String[] args) {
		
		Predicate<String> p = s -> s.length() < 20;
		
		String s = "Hello, World!";
		boolean b = p.test(s);
		System.out.println("String s is less than 20 chars: " + b);
	}

}
