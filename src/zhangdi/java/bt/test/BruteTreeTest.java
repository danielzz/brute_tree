package zhangdi.java.bt.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import zhangdi.java.bt.AbstractBruteTreeNode;
import zhangdi.java.bt.BruteTree;
import zhangdi.java.bt.BruteTreeFactory;
import zhangdi.java.bt.BruteTreeNode;
import zhangdi.java.bt.ValidationResult;

public class BruteTreeTest {
    
    BruteTree tree;
    
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
        
        public String toString() {
            StringBuffer sb = new StringBuffer(Arrays.toString(this.digits));
            sb.append("\n");
            sb.append(" ");
            for (int i = 0; i < this.currentPosition; i++) {
                sb.append("   ");
            }
            
            sb.append("^");
            
            return sb.toString();
        }
        
    }

    @Before
    public void setUp() throws Exception {
        this.tree = BruteTreeFactory.create(new NumberGuess(null, NumberGuess.input, 0));
    }

    @After
    public void tearDown() throws Exception {
        this.tree = null;
    }

    @Test
    public final void testRootInstanceOfNumberGuess() {
        assertTrue(this.tree.getRoot() instanceof NumberGuess);
    }
    
    @Test
    public final void testInstanceOfBruteTree() {
        assertTrue(this.tree instanceof BruteTree);
    }
    
    @Test
    public final void testSingleThread() {
        this.tree.run();
        Collection<BruteTreeNode> resolutions = this.tree.getSolutions();
        assertNotNull(resolutions);
        assertEquals(1, resolutions.size());
        
        long timeUsed = this.tree.getTimeUsed();
        assertTrue(timeUsed > 0);
        
        System.out.println("Time Used (Single Thread): " + timeUsed);
    }
    
    @Test
    public final void testMultiThread() {
        this.tree.setMultiThreadLevel(1);
        this.tree.run();
        Collection<BruteTreeNode> resolutions = this.tree.getSolutions();
        assertNotNull(resolutions);
        assertEquals(1, resolutions.size());

        long timeUsed = this.tree.getTimeUsed();
        assertTrue(timeUsed > 0);

        System.out.println("Time Used (Multiple Thread, Level 1): " + timeUsed);
    }

}
