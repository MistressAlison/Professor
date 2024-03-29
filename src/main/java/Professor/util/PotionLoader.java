package Professor.util;

import Professor.TheProfessor;
import Professor.cards.abstracts.AbstractEasyCard;
import Professor.potions.LimitBreaker;
import Professor.potions.TabooPotion;
import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;

public class PotionLoader {
    public static void loadContent() {
        BaseMod.addPotion(TabooPotion.class, new Color(95/255f,46/255f,209/255f,1), new Color(40/255f,20/255f,107/255f,1), null, TabooPotion.POTION_ID, TheProfessor.Enums.THE_PROFESSOR);
        BaseMod.addPotion(LimitBreaker.class, Color.GOLD.cpy(), Color.GOLDENROD.cpy(), AbstractEasyCard.lighten(Color.GOLD.cpy()), LimitBreaker.POTION_ID, TheProfessor.Enums.THE_PROFESSOR);
    }
}
