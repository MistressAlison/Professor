package Professor.util;

import Professor.potions.LimitBreaker;
import Professor.potions.TabooPotion;
import com.evacipated.cardcrawl.mod.widepotions.WidePotionsMod;

public class WidePotionLoader {
    public static void loadCrossoverContent() {
        WidePotionsMod.whitelistSimplePotion(TabooPotion.POTION_ID);
        WidePotionsMod.whitelistSimplePotion(LimitBreaker.POTION_ID);
    }
}
