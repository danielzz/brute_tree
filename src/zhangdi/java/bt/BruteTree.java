package zhangdi.java.bt;

import java.util.Collection;

public interface BruteTree {
    BruteTreeNode getRoot();
    
    void run();
    
    Collection<BruteTreeNode> getSolutions();
    
    void setMultiThreadLevel(int level);
    
    int getMultiThreadLevel();
    
    void setExpectedSolutionCount(int count);
    
    int getExpectedSolutionCount();
    
    long getTimeUsed();
}
