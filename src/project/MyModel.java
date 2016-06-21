package project;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.util.Enumeration;

public class MyModel implements IModel {

    DefaultTreeModel treeModel;

    public DefaultTreeModel getTreeModel() {
        return treeModel;
    }

    public void setTreeModel(DefaultTreeModel treeModel) {
        this.treeModel = treeModel;
    }

    @Override
    public DefaultMutableTreeNode tryAddNode(DefaultMutableTreeNode parent, String href) {

        String parentHref = (String) parent.getUserObject();
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(href);

        boolean showInsertNewNode = !containsHref((DefaultMutableTreeNode) treeModel.getRoot(), href) &&
                href.startsWith(parentHref);
        if (showInsertNewNode) {
            parent.add(newNode);
            return newNode;
        } else {
            return null;
        }
    }

    private boolean containsHref(DefaultMutableTreeNode root, String href) {
        @SuppressWarnings("unchecked")
        Enumeration<DefaultMutableTreeNode> e = root.depthFirstEnumeration();
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node = e.nextElement();
            if (node.toString().equalsIgnoreCase(href)) {
                return true;
            }
        }
        return false;
    }
}
