package comparator;

import java.util.function.Function;

/**
 * From live coding session 1 
 * @author tqualiano
 *
 * @param <T>
 */

@FunctionalInterface
public interface Comparator<T>{
	
	public int compare(T t1, T t2);
	
	/*
	 * This method is bad because it is not generic enough.  It cannot compare two people
	 * based on a string for instance.
	 */
	public static Comparator<Person> comparingBad(Function<Person, Integer> f){
		return (p1, p2) -> f.apply(p1) - f.apply(p2);
	}
	
	/*
	 * Note that a Comparable is an Interface that imposes a total ordering on the objects of
	 * each class that implements it.
	 * 
	 * Still not perfect though, it only takes People objects!
	 */
	public static Comparator<Person> comparingBetter(Function<Person, Comparable> f){
		return (p1, p2) -> f.apply(p1).compareTo(f.apply(p2));
	}
	
	/*
	 * Most generic comparing method.
	 * 
	 * What does the method signature mean?
	 * public - access modifier
	 * static - static modifier - this method does not need to be called on an instance 
	 * <> - denotes Type parameters
	 * T - first type parameter
	 * U extends Comparable<> - U can be any type that extends Comparable<> 
	 * Comparable<? super U> - matches a comparable of any type that is a super type of U
	 * 
	 */
	public static <T, U extends Comparable<? super U>> Comparator<T> comparing(Function<? super T, ? extends U> f){
		return (t1, t2) -> f.apply(t1).compareTo(f.apply(t2));
	}
	
	/*
	 * If this Comparator's compare method does not return zero, use it.  Otherwise compare on
	 * the parameters compare method
	 */
	public default Comparator<T> thenComparing(Comparator<T> comp){
		return (t1, t2) -> this.compare(t1, t2) != 0 ? this.compare(t1, t2) : comp.compare(t1, t2);
	}
	
	/*
	 * The first thenComparing does not have a function as available input.  So method references
	 * cannot be passed.  This is solved below.
	 */
	public default Comparator<T> thenComparing(Function<T, Comparable> f){
		Comparator<T> comp = Comparator.comparing(f);
		return thenComparing(comp);
	}
}
