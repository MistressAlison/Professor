package Professor.util;

import Professor.potion.TabooPotion;
import com.evacipated.cardcrawl.mod.widepotions.WidePotionsMod;

public class WidePotionLoader {
    public static void loadCrossoverContent() {
        WidePotionsMod.whitelistSimplePotion(TabooPotion.POTION_ID);
    }
}
