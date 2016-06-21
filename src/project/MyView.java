package project;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MyView extends JFrame implements IView {

    IPresenter presenter;
    private JLabel jLabel;
    private JTextField jTextField;
    private JButton jButton;
    private JTree jTree;
    private JScrollPane jScrollPane;
    private JMenuBar jMenubar;
    private JButton jSaveBtn;

    public MyView() {
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new FlowLayout());

        jLabel = new JLabel("site URL: ");
        jTextField = new JTextField("http://www.ulbsibiu.ro", 13);
        jButton = new JButton("fetch links");
        jButton.addActionListener(e -> getPresenter().download());
        jSaveBtn = new JButton("save links to file");
        jSaveBtn.addActionListener(e -> getPresenter().saveLinksToFile());

        jTree = new JTree();
        jTree.setModel(null);
        jTree.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int selRow = jTree.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = jTree.getPathForLocation(e.getX(), e.getY());
                if (selRow != -1) {
                    if (SwingUtilities.isRightMouseButton(e)) {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            desktop.browse(new URI(selPath.getLastPathComponent().toString()));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } catch (URISyntaxException e1) {
                            e1.printStackTrace();
                        }
                    }

                }
            }
        });
        jScrollPane = new JScrollPane(jTree);
        jScrollPane.setPreferredSize(new Dimension(600, 400));
        jScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);

        jMenubar = new JMenuBar();

        getContentPane().add(jLabel);
        getContentPane().add(jTextField);
        getContentPane().add(jButton);
        getContentPane().add(jSaveBtn);
        getContentPane().add(jScrollPane);
        setJMenuBar(jMenubar);
    }

    private void expandTree(JTree jTree) {
        for (int i = 0; i < jTree.getRowCount(); i++) {
            jTree.expandRow(i);
        }
    }

    @Override
    public IPresenter getPresenter() {
        return presenter;
    }

    @Override
    public void setPresenter(IPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void updateViewFromModel() {
        jTree.setModel(getPresenter().getModel().getTreeModel());
    }

    @Override
    public void open() {
        setSize(800, 600);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    @Override
    public void close() {
        dispose();
    }

    @Override
    public void updateModelFromView() {
        getPresenter().getModel().setTreeModel((DefaultTreeModel) jTree.getModel());
    }

    @Override
    public String getSiteURL() {
        return jTextField.getText();
    }

    @Override
    public void expandTree() {
        expandTree(jTree);
    }

    @Override
    public void disableFetchButton() {
        jButton.setEnabled(false);
    }

    @Override
    public void enableFetchButton() {
        jButton.setEnabled(true);
    }
}
