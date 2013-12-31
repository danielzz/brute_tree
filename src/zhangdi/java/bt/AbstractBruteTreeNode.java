package zhangdi.java.bt;

import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractBruteTreeNode implements BruteTreeNode {
    
    BruteTreeNode parent;
    Collection<BruteTreeNode> children;
    
    public AbstractBruteTreeNode() {
        this(null);
    }
    
    public AbstractBruteTreeNode(BruteTreeNode parent) {
        this.parent = parent;
        this.children = new ArrayList<BruteTreeNode>();
        if (this.parent != null) {
            this.parent.addChild(this);
        }
    }

    @Override
    abstract public ValidationResult validate();

    @Override
    abstract public Collection<BruteTreeNode> nextLevel();

    @Override
    public BruteTreeNode getParent() {
        return parent;
    }

    @Override
    public void setParent(BruteTreeNode parent) {
        this.parent = parent;
        if (this.parent != null) {
            this.parent.addChild(this);
        }
    }
    
    @Override
    public void addChild(BruteTreeNode node) {
        this.children.add(node);
    }
    
    @Override
    public Collection<BruteTreeNode> getChildren() {
        return this.children;
    }
}
