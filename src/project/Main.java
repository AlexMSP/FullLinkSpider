package project;

import java.awt.*;

public class Main {

    //TODO - save links to file
    //TODO - nice menu with some options
    //TODO - beautify the jTree
    //TODO - some statistics maybe? -- external links?


    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            IModel myModel = new MyModel();
            IView myView = new MyView();

            IPresenter myPresenter = new MyPresenter();
            myPresenter.setModel(myModel);
            myPresenter.setView(myView);

            myPresenter.run();
        });
    }
}



