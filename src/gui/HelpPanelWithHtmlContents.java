package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import mgr.HelpPanelContentBuilder;
import mgr.HelpPanelContentModel;

public class HelpPanelWithHtmlContents extends JPanel {

    private JTextPane htmlPane;
    private HelpPanelContentModel theModel;
    private HelpPanelContentBuilder theBuilder = new HelpPanelContentBuilder();
    private JButton[] btns;
    private int currentBtnIndex = 0;

    public HelpPanelWithHtmlContents() {
        this.setPreferredSize(new Dimension(500, 300));

        htmlPane = new JTextPane();
        htmlPane.setContentType("text/html");
        htmlPane.setEditable(false); // Ensure the text pane is not editable

        JScrollPane jspHtml = new JScrollPane(htmlPane);

        theModel = theBuilder.retrievePagesFromDir("html/Welcome");
        int pageCount = theModel.pageCount();

        if (pageCount > 1) {
            btns = new JButton[pageCount + 2];
            for (int i = 0; i < pageCount + 2; i++) {
                if (i == 0) {
                    btns[i] = new JButton("Prev");
                } else if (i == pageCount + 1) {
                    btns[i] = new JButton("Next");
                } else {
                    btns[i] = new JButton("" + i);
                }
                btns[i].addActionListener(new NavigationHandler());
            }
            updateButtonsState();
        }

        showPage(currentBtnIndex);

        JScrollPane scrollPane = new JScrollPane(htmlPane);
        scrollPane.setPreferredSize(new Dimension(500, 300));
        this.add(scrollPane);
        if (btns != null) {
            for (JButton btn : btns) {
                this.add(btn);
            }
        }
    }

    private void updateButtonsState() {
        for (int i = 0; i < btns.length; i++) {
            btns[i].setEnabled(true);
        }
        btns[0].setEnabled(currentBtnIndex > 0); // Prev button
        btns[btns.length - 1].setEnabled(currentBtnIndex < theModel.pageCount() - 1); // Next button
    }

    private void showPage(int index) {
        htmlPane.setText(theModel.getPageByIndex(index));
    }

    class NavigationHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getActionCommand().equals("Next")) {
                if (currentBtnIndex < theModel.pageCount() - 1) {
                    currentBtnIndex++;
                    showPage(currentBtnIndex);
                }
            } else if (e.getActionCommand().equals("Prev")) {
                if (currentBtnIndex > 0) {
                    currentBtnIndex--;
                    showPage(currentBtnIndex);
                }
            } else {
                int pageIndex = Integer.parseInt(e.getActionCommand()) - 1;
                if (pageIndex != currentBtnIndex) {
                    currentBtnIndex = pageIndex;
                    showPage(currentBtnIndex);
                }
            }
            updateButtonsState();
        }
    }
}
