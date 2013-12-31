package zhangdi.java.bt;

import zhangdi.java.bt.impl.BruteTreeImpl;

public class BruteTreeFactory {
    public static BruteTree create(BruteTreeNode root) {
        return new BruteTreeImpl(root);
    }
}
