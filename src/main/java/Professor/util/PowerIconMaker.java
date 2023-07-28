package Professor.util;

import Professor.MainModfile;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PowerIconMaker {
    public static void setIcons(AbstractPower p, Texture t) {
        Texture small = TextureScaler.rescale(t, 48, 48);
        Texture large = TextureScaler.rescale(t, 128, 128);
        p.region48 = new TextureAtlas.AtlasRegion(small, 0, 0, small.getWidth(), small.getHeight());
        p.region128 = new TextureAtlas.AtlasRegion(large, 0, 0, large.getWidth(), large.getHeight());
    }

    public static void setIcons(AbstractPower p, String itemCode) {
        setIcons(p, TexLoader.getTexture(MainModfile.makeImagePath("items/"+ itemCode +".png")));
    }
}
