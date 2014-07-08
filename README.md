brute_tree
==========

A tree-like brute force algorithm implementation.

Example
=======
```
    public static class NumberGuess extends AbstractBruteTreeNode {
        
        static final int[] answer = new int[]{4,5,6,2,1,7};
        static final int[] input = new int[]{0,0,6,0,0,0};
        
        int[] digits = null;
        int currentPosition = 0;
        
        public NumberGuess(NumberGuess parent, int[] given, int currentPosition) {
            super(parent);
            this.digits = Arrays.copyOf(given, given.length);
            this.currentPosition = currentPosition;
        }

        @Override
        public ValidationResult validate() {
            if (Arrays.equals(digits, answer)) {
                return ValidationResult.RESOLVED;
            }
            
            if (hasDuplicatedDigit(digits)) {
                return ValidationResult.DEAD;
            }
            
            if (currentPosition > 5) {
                return ValidationResult.DEAD;
            }
            
            return ValidationResult.PENDING;
        }

        @Override
        public Collection<BruteTreeNode> nextLevel() {
            List<BruteTreeNode> list = new ArrayList<BruteTreeNode>();
            int value = this.digits[currentPosition];
            if (value != 0) {
                NumberGuess ng = new NumberGuess(this, digits, currentPosition+1);
                list.add(ng);
            } else {
                for (int i = 1; i <= 30; i++) {
                    NumberGuess ng = new NumberGuess(this, digits, currentPosition+1);
                    ng.digits[currentPosition] = i;
                    list.add(ng);
                }
            }
            return list;
        }
        
        private boolean hasDuplicatedDigit(int[] digits) {
            int length = digits.length;
            for (int i = 0; i < length; i++) {
                int current = digits[i];
                if (current == 0) {
                    continue;
                }
                for (int j = i+1; j < length; j++) {
                    int next = digits[j];
                    if (current == next) {
                        return true;
                    }
                }
            }
            return false;
        }
    }
    
    BruteTree tree = BruteTreeFactory.create(new NumberGuess(null, NumberGuess.input, 0));
    tree.run();
    Collection<BruteTreeNode> solutions = tree.getSolutions(); // Results are stored in a collection of BruteTreeNode.
```

API
===
```
/**
 * Create a BruteTree from a root node. A BruteTreeNode is
 * generally a problem description class.
 */
BruteTree BruteTreeFactory.create(BruteTreeNode root);

interface BruteTreeNode {
    /**
     * Return the validation result based on the current state.
     * Return DEAD if it's impossible to resolve
     * Return PENDING if it still need to proceed
     * Return RESOLVED if current state is a resolution of the problem
     */
    ValidationResult validate();
    
    /**
     * Move to the next level of the problem.
     */
    Collection<BruteTreeNode> nextLevel();
}
```

An abstract class can be used for creating a BrueTreeNode instance class. See example for details.

Alternatively, you can also use a generic API if you like Java generics.

...
public class GenericBruteTreeNode<Rule extends ProblemRule<Problem>, Problem> extends AbstractBruteTreeNode

public interface ProblemRule<Problem> {
    ValidationResult validate(Problem problem);
    
    Collection<Problem> nextLevel(Problem problem);
}
...

This separates the rule of the problem and problem itself. You can read the test class for generic API for more details.
