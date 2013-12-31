package zhangdi.java.bt;

import java.util.Collection;

public interface ProblemRule<Problem> {
    ValidationResult validate(Problem problem);
    
    Collection<Problem> nextLevel(Problem problem);
}
