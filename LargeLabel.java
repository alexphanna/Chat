import java.awt.*;
public class LargeLabel extends Panel {
    public LargeLabel(int width, int height) {
        setSize(width, height);
        setLayout(new GridLayout(getHeight() / 25, 1));
        for (int i = 0; i < getHeight() / 25; i++) {
            add(new Label());
        }
    }
    public void print(String text) {
        for (Component component : getComponents()) {
            if (((Label)component).getText().length() == 0) {
                ((Label)component).setText(text);
                return;
            }
        }
        for (int i = 0; i < getComponents().length - 1; i++) ((Label)getComponent(i)).setText(((Label)getComponent(i + 1)).getText());
        ((Label)getComponent(getComponents().length - 1)).setText(text);
    }
}