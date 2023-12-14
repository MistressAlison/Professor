package Professor.cards.abstracts;

import Professor.MainModfile;
import com.megacrit.cardcrawl.cards.AbstractCard;

public abstract class AbstractRecipeCard extends AbstractEasyCard {
    public boolean triggered;
    public AbstractRecipeCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(cardID, cost, type, rarity, target);
        switch (type) {
            case ATTACK:
                this.setBackgroundTexture(MainModfile.ATTACK_S_ART_BLUE, MainModfile.ATTACK_L_ART_BLUE);
                break;
            case POWER:
                this.setBackgroundTexture(MainModfile.POWER_S_ART_BLUE, MainModfile.POWER_L_ART_BLUE);
                break;
            default:
                this.setBackgroundTexture(MainModfile.SKILL_S_ART_BLUE, MainModfile.SKILL_L_ART_BLUE);
                break;
        }
        setOrbTexture(MainModfile.CARD_ENERGY_S_BLUE, MainModfile.CARD_ENERGY_L_BLUE);
    }

    public AbstractRecipeCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target, CardColor color) {
        super(cardID, cost, type, rarity, target, color);
        switch (type) {
            case ATTACK:
                this.setBackgroundTexture(MainModfile.ATTACK_S_ART_BLUE, MainModfile.ATTACK_L_ART_BLUE);
                break;
            case POWER:
                this.setBackgroundTexture(MainModfile.POWER_S_ART_BLUE, MainModfile.POWER_L_ART_BLUE);
                break;
            default:
                this.setBackgroundTexture(MainModfile.SKILL_S_ART_BLUE, MainModfile.SKILL_L_ART_BLUE);
                break;
        }
        setOrbTexture(MainModfile.CARD_ENERGY_S_BLUE, MainModfile.CARD_ENERGY_L_BLUE);
    }

    public abstract int getValance();

    public abstract AbstractCreationCard getCreation(int red, int blue, int yellow, int green);
}
