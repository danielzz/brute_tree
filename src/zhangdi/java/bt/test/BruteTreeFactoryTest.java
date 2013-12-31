package zhangdi.java.bt.test;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import zhangdi.java.bt.BruteTree;
import zhangdi.java.bt.BruteTreeFactory;
import zhangdi.java.bt.BruteTreeNode;
import zhangdi.java.bt.ValidationResult;

public class BruteTreeFactoryTest {
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public final void testCreateRootNotNull() throws NullPointerException {
        thrown.expect(NullPointerException.class);
        BruteTreeFactory.create(null);
    }
    
    @Test
    public final void testCreateInstanceOf() {
        assertTrue(BruteTreeFactory.create(new BruteTreeNode() {

            @Override
            public ValidationResult validate() {
                return null;
            }

            @Override
            public Collection<BruteTreeNode> nextLevel() {
                return null;
            }

            @Override
            public BruteTreeNode getParent() {
                return null;
            }

            @Override
            public void setParent(BruteTreeNode parent) {
            }

            @Override
            public void addChild(BruteTreeNode node) {
                
            }

            @Override
            public Collection<BruteTreeNode> getChildren() {
                return null;
            }
            
        }) instanceof BruteTree);
    }

}
