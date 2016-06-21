package project;

import java.awt.*;

public class Main {

    //TODO populate JMenuBar with option to select a destination to save files
    //TODO - beautify the jTree

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



