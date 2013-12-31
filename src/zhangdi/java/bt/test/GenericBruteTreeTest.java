package zhangdi.java.bt.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import zhangdi.java.bt.BruteTree;
import zhangdi.java.bt.BruteTreeFactory;
import zhangdi.java.bt.BruteTreeNode;
import zhangdi.java.bt.GenericBruteTreeNode;
import zhangdi.java.bt.ProblemRule;
import zhangdi.java.bt.ValidationResult;

public class GenericBruteTreeTest {
    
    public static class DigitGuessProblem {
        int[] digits = null;
        int currentGuessing = 0;
        
        public DigitGuessProblem() {
            this.digits = new int[8];
            Arrays.fill(digits, 0);
        }
        
        public DigitGuessProblem(int[] values) {
            this.digits = Arrays.copyOf(values, 8);
        }
        
        public int[] getDigits() {
            return this.digits;
        }
        
        public void setDigit(int position, int value) {
            this.digits[position] = value;
        }
        
        public int getCurrentGuessing() {
            return this.currentGuessing;
        }
        
        public void setCurrentGuessing(int position) {
            this.currentGuessing = position;
        }
        
        public String toString() {
            return Arrays.toString(digits);
        }
    }
    
    public static class DigitGuessProblemRule implements ProblemRule<DigitGuessProblem>{
        
        static final int[] expected = new int[]{12,23,1,27,9,7,18,13};

        @Override
        public ValidationResult validate(DigitGuessProblem problem) {
            
            System.out.println("Current Problem: " + problem);
            if (Arrays.equals(expected, problem.getDigits())) {
                return ValidationResult.RESOLVED;
            }
            
            if (problem.getCurrentGuessing() >= expected.length) {
                return ValidationResult.DEAD;
            }
            
            if (hasDuplicatedDigit(problem)) {
                return ValidationResult.DEAD;
            }
            
            return ValidationResult.PENDING;
        }

        @Override
        public Collection<DigitGuessProblem> nextLevel(DigitGuessProblem problem) {
            List<DigitGuessProblem> list = new ArrayList<DigitGuessProblem>();
            int[] digits = problem.getDigits();
            int value = digits[problem.getCurrentGuessing()];
            if (value != 0) {
                DigitGuessProblem p = new DigitGuessProblem(digits);
                p.setCurrentGuessing(problem.getCurrentGuessing()+1);
                list.add(p);
            } else {
                for (int i = 1; i <= 30; i++) {
                    DigitGuessProblem p = new DigitGuessProblem(digits);
                    p.setCurrentGuessing(problem.getCurrentGuessing()+1);
                    p.digits[problem.getCurrentGuessing()] = i;
                    list.add(p);
                }
            }
            return list;
        }

        private boolean hasDuplicatedDigit(DigitGuessProblem problem) {
            int[] digits = problem.getDigits();
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

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public final void testGenericBruteTreeNode() {
        DigitGuessProblemRule rule = new DigitGuessProblemRule();
        DigitGuessProblem problem = new DigitGuessProblem(new int[]{0,23,0,0,0,0,18,13});
        
        GenericBruteTreeNode<DigitGuessProblemRule, DigitGuessProblem> root = new GenericBruteTreeNode<DigitGuessProblemRule, DigitGuessProblem>(rule, problem);
        
        BruteTree tree = BruteTreeFactory.create(root);
        tree.setMultiThreadLevel(2);
        tree.run();
        
        Collection<BruteTreeNode> resolutions = tree.getSolutions();
        
        System.out.println(resolutions);
    }

}
