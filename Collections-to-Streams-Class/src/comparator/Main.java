package comparator;

import java.util.function.Function;

public class Main {

	public static void main(String[] args) {
		
		/*
		 * We can use Lambda expressions instead of anonymous classes to create an instance of
		 * a functional interface.
		 */
		
		// Comparator on Strings by length
		Comparator<String> comp = (s1, s2) -> s1.length() - s2.length();
		
		// Several comparators on People
		Comparator<Person> compAge = (p1, p2) -> p1.getAge() - p2.getAge();
		Comparator<Person> compFirstName = (p1, p2) -> p1.getFirstName().compareTo(p2.getFirstName());
		Comparator<Person> compLastName = (p1, p2) -> p1.getLastName().compareTo(p2.getLastName());
		
		/*
		 * There are some similarities with the pattern above that can be abstracted out.  Notice how
		 * in the Comparator we are taking two People objects and comparing them based on one of their
		 * instance variable.
		 * 
		 * Instead of writing comparators in this way, we could define a Function object (which is also
		 * a functional interface) and pass that as an argument to the Comparator we create.  We will
		 * need to implement a static method in the Comparator class to do this.
		 * 
		 */
		Function<Person, Integer> f1 = p -> p.getAge();
		Function<Person, String> f2 = p -> p.getFirstName();
		Function<Person, String> f3 = p -> p.getLastName();
		
		Comparator<Person> compPersonAge1 = Comparator.comparingBad(f1);
		// Note we can use the lambda expression itself as an input.
		Comparator<Person> compPersonAge2 = Comparator.comparingBad(p -> p.getAge());
		// Since the lambda expression is just an application of the getAge method, we can use a method
		// reference instead. It is meant to ease readability.
		Comparator<Person> compPersonAge3 = Comparator.comparingBad(Person::getAge);
		
		/*
		 * The first comparing method we wrote referenced above, named comparingBad, is not too useful because
		 * its input is a Function that takes in a Person and returns an Integer.  Thus, we cannot 
		 * pass in f2, f3, or their corresponding method references to the comparingBad method.
		 * 
		 * We must make a more generic method, comparingBetter, that will take in a Function that takes 
		 * a Person and returns a Comparable.
		 */
		Comparator<Person> compPersonAge4  = Comparator.comparingBetter(Person::getAge);
		Comparator<Person> compPersonFName = Comparator.comparingBetter(Person::getFirstName);
		Comparator<Person> compPersonLName = Comparator.comparingBetter(Person::getLastName);
		
		/*
		 * The second comparing method we wrote referenced above, named comparingBetter, is still not
		 * as useful as it could be.  It must take in a Function that takes in a Person and returns
		 * some Comparable.  If we don't have a Person but still want to use the comparingBetter method,
		 * our logic breaks down.  For instance, we could NOT make a Comparator that does the same thing
		 * as the comp object we made above.
		 * 
		 * We, again, must make a more generic method, comparing, that will take in a Function that takes
		 * a generic T and returns a Comparable.
		 */
		Comparator<String> compString = Comparator.comparing(String::length);
		Comparator<Person> compPersonAge5 = Comparator.comparing(Person::getAge);

		
		/*
		 * We would like a Comparator to be able to compare on more than one field.  For instance,
		 * if we compare two Person objects on age, but their age is equal, we would like to compare
		 * them on lastName afterwards.  We can do this by creating a new default method called
		 * thenComparing.
		 */
		Comparator<Person> compAgeThenLName = Comparator.comparing(Person::getAge)
				                                        .thenComparing(Person::getLastName);
		
		
	}

}
