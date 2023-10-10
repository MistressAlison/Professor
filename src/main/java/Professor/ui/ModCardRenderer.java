package Professor.ui;

import basemod.IUIElement;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;

public class ModCardRenderer implements IUIElement {
    public AbstractCard card;
    public float x;
    public float y;
    public float scale;

    public ModCardRenderer(AbstractCard card, float xPos, float yPos, float scale) {
        this.card = card;
        this.x = xPos * Settings.scale;
        this.y = yPos * Settings.scale;
        this.scale = scale;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        card.renderInLibrary(spriteBatch);
    }

    @Override
    public void update() {
        card.current_x = x;
        card.current_y = y;
        card.target_x = x;
        card.target_y = y;
        card.drawScale = scale;
        card.targetDrawScale = scale;
        card.update();
    }

    @Override
    public int renderLayer() {
        return 3;
    }

    @Override
    public int updateOrder() {
        return 0;
    }

    public void set(float xPos, float yPos) {
        this.x = xPos * Settings.scale;// 64
        this.y = yPos * Settings.scale;// 65
    }// 66

    public void setX(float xPos) {
        this.x = xPos * Settings.scale;// 70
    }// 71

    public void setY(float yPos) {
        this.y = yPos * Settings.scale;// 75
    }// 76

    public float getX() {
        return this.x / Settings.scale;// 80
    }

    public float getY() {
        return this.y / Settings.scale;// 85
    }
}
