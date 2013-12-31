package zhangdi.java.bt;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GenericBruteTreeNode<Rule extends ProblemRule<Problem>, Problem> extends AbstractBruteTreeNode {
    
    Rule rule;
    Problem problem;
    
    public GenericBruteTreeNode(Rule rule, Problem problem) {
        super(null);
        this.rule = rule;
        this.problem = problem;
    }

    @Override
    public ValidationResult validate() {
        return rule.validate(problem);
    }

    @Override
    public Collection<BruteTreeNode> nextLevel() {
        Collection<Problem> candidates = this.rule.nextLevel(problem);
        
        List<BruteTreeNode> children = new ArrayList<BruteTreeNode>();
        for (Problem p : candidates) {
            GenericBruteTreeNode<Rule, Problem> child = new GenericBruteTreeNode<Rule, Problem>(this.rule, p);
            child.setParent(this);
            children.add(child);
        }
        
        return children;
    }
    
    public Problem getProblem() {
        return this.problem;
    }
    
    public String toString() {
        return this.problem.toString();
    }
}
