package project;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public interface IModel {
    DefaultTreeModel getTreeModel();
    void setTreeModel(DefaultTreeModel treeModel);

    DefaultMutableTreeNode tryAddNode(DefaultMutableTreeNode parent, String href);
}
