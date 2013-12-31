package zhangdi.java.bt.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CountDownLatch;

import zhangdi.java.bt.BruteTree;
import zhangdi.java.bt.BruteTreeNode;
import zhangdi.java.bt.ValidationResult;

public class BruteTreeImpl implements BruteTree {
    
    BruteTreeNode root;
    
    int expectedSolutionCount = 1;
    
    Collection<BruteTreeNode> solutions;
    
    int multiThreadLevel = 0;
    
    boolean running = false;
    
    long begin = 0;
    long end = 0;
    
    public BruteTreeImpl(BruteTreeNode root) {
        if (root == null) {
            throw new NullPointerException("Root node cannot be null.");
        }
        this.root = root;
        this.solutions = new ArrayList<BruteTreeNode>();
    }

    @Override
    public BruteTreeNode getRoot() {
        return this.root;
    }

    @Override
    public void run() {
        if (running) {
            throw new IllegalStateException("It's already running.");
        }
        this.running = true;
        this.begin = System.currentTimeMillis();
        process(root, 0);
        this.end = System.currentTimeMillis();
        this.running = false;
    }

    @Override
    public Collection<BruteTreeNode> getSolutions() {
        return solutions;
    }
    
    @Override
    public void setMultiThreadLevel(int level) {
        if (running) {
            throw new IllegalStateException("Cannot modify this property during run.");
        }
        this.multiThreadLevel = level;
    }
    
    @Override
    public int getMultiThreadLevel() {
        return this.multiThreadLevel;
    }
    
    @Override
    public long getTimeUsed() {
        if (begin == 0) {
            throw new IllegalStateException("Has not been started.");
        }
        if (end == 0) {
            throw new IllegalStateException("Has not been finished.");
        }
        
        return end - begin;
    }
    
    @Override
    public void setExpectedSolutionCount(int count) {
        this.expectedSolutionCount = count;
    }
    
    @Override
    public int getExpectedSolutionCount() {
        return this.expectedSolutionCount;
    }
    
    private void process(BruteTreeNode node, int level) {
        
        if (this.solutions.size() >= this.getExpectedSolutionCount()) {
            return;
        }
        
        ValidationResult result = node.validate();
        
        switch(result) {
        case RESOLVED:
            this.solutions.add(node);
            return;
        case DEAD:
            return;
        case PENDING:
            Collection<BruteTreeNode> children = node.nextLevel();
            if (level < this.multiThreadLevel) {
                CountDownLatch countDown = new CountDownLatch(children.size());
                processWithThreads(children, level+1, countDown);
                try {
                    countDown.await();
                } catch (InterruptedException e) {
                    // do nothing.
                }
            } else {
                for (BruteTreeNode child: children) {
                    process(child, level+1);
                }
            }
            return;
        }
    }
    
    private void processWithThreads(Collection<BruteTreeNode> nodes, int level, CountDownLatch countDown) {
        for (BruteTreeNode node : nodes) {
            processThread(node, level, countDown);
        }
    }
    
    private void processThread(final BruteTreeNode node, final int level, final CountDownLatch countDown) {
        final BruteTreeImpl self = this;
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                self.process(node, level);
                countDown.countDown();
            }
            
        });
        
        thread.start();
    }
    
}
