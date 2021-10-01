// Skeleton of the Polynomial ADT

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Polynomial {

    // Create an empty polynomial
    public Polynomial() {
        polynomial = new TreeMap<>(Collections.reverseOrder());
    }

    // Create a single-term polynomial
    public Polynomial(int coef, int exp) {
        polynomial = new TreeMap<>(Collections.reverseOrder());
        polynomial.put(exp, coef);
    }

    // Add opnd to 'this' polynomial; 'this' is returned
    public Polynomial add(Polynomial opnd) {
        for (var entry : opnd.getPolynomialEntries()) {
            polynomial.compute(entry.getKey(), (k, v) -> (
                    (v == null) ? entry.getValue() : v + entry.getValue()
            ));
        }
        return this;
    }

    // Subtract opnd from 'this' polynomial; 'this' is returned
    public Polynomial sub(Polynomial opnd) {
        for (var entry : opnd.getPolynomialEntries()) {
            polynomial.compute(entry.getKey(), (k, v) -> (
                    (v == null) ? entry.getValue() : v - entry.getValue()
            ));
        }
        return this;
    }

    // Print the terms of 'this' polynomial in decreasing order of exponents.
    // No pair of terms can share the same exponent in the printout.
    public void print() {
        for (var entry : polynomial.entrySet()) {
            if (entry.getValue() != 0) {
                System.out.print(entry.getValue() + " " + entry.getKey() + " ");
            }
        }
    }

    private final TreeMap<Integer, Integer> polynomial;

    public Set<Map.Entry<Integer, Integer>> getPolynomialEntries() {
        return polynomial.entrySet();
    }
}
