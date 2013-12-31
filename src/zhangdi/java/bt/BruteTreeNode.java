package zhangdi.java.bt;

import java.util.Collection;

public interface BruteTreeNode {
    /**
     * Validate the current node to verify if it's a
     * solution of the problem.
     * 
     * @return true if it's a solution, otherwise false
     */
    ValidationResult validate();
    
    /**
     * Goes to the next level of the candidates.
     * @return a collection of the next level candidates
     */
    Collection<BruteTreeNode> nextLevel();
    
    BruteTreeNode getParent();
    
    void setParent(BruteTreeNode parent);
    
    void addChild(BruteTreeNode node);
    
    Collection<BruteTreeNode> getChildren();
}
