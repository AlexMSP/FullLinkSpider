package project;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.io.IOException;
import java.net.MalformedURLException;

public class MyPresenter implements IPresenter {

    IView view;
    IModel model;

    @Override
    public IModel getModel() {
        return model;
    }

    @Override
    public void setModel(IModel model) {
        this.model = model;
    }

    @Override
    public IView getView() {
        return view;
    }

    @Override
    public void setView(IView view) {
        this.view = view;
    }

    @Override
    public void run() {
        model.setTreeModel(new DefaultTreeModel(null));
        view.setPresenter(this);
        view.updateViewFromModel();
        view.open();
    }

    @Override
    public void download() {
        String siteURL = view.getSiteURL();

        DefaultMutableTreeNode root = new DefaultMutableTreeNode(siteURL);
        model.getTreeModel().setRoot(root);

        String finalSiteURL = siteURL;
        view.disableFetchButton();
        SwingUtilities.invokeLater(() -> {
            downloadHtml(root, finalSiteURL, 0, 3);
            view.expandTree();
            JOptionPane.showMessageDialog(null, "Finished retrieving links!");
            view.enableFetchButton();
        });
    }

    @Override
    public void saveLinksToFile() {
        //TODO
    }

    private void downloadHtml(DefaultMutableTreeNode root, String siteURL, int currentDepth, int maxDepth) {
        if (currentDepth > maxDepth) {
            return;
        }
        try {
            Document document = Jsoup.connect(siteURL).get();
            Elements links = document.select("a[href]");

            for (Element link : links) {
                String href = link.attr("abs:href");

                DefaultMutableTreeNode newNode = model.tryAddNode(root, href);

                if (newNode != null) {
                    downloadHtml(newNode, href, ++currentDepth, maxDepth);
                }
            }
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (MalformedURLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
