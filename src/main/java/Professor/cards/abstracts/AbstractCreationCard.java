package Professor.cards.abstracts;

import Professor.MainModfile;

public abstract class AbstractCreationCard extends AbstractEasyCard {
    public ElementData data;
    public static class ElementData {
        public int r, b, y, g;

        public ElementData(int r, int b, int y, int g) {
            this.r = r;
            this.b = b;
            this.y = y;
            this.g = g;
        }

        public ElementData cpy() {
            return new ElementData(r, b, y, g);
        }
    }

    public AbstractCreationCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(cardID, cost, type, rarity, target);
        switch (type) {
            case ATTACK:
                this.setBackgroundTexture(MainModfile.ATTACK_S_ART_PURPLE, MainModfile.ATTACK_L_ART_PURPLE);
                break;
            case POWER:
                this.setBackgroundTexture(MainModfile.POWER_S_ART_PURPLE, MainModfile.POWER_L_ART_PURPLE);
                break;
            default:
                this.setBackgroundTexture(MainModfile.SKILL_S_ART_PURPLE, MainModfile.SKILL_L_ART_PURPLE);
                break;
        }
    }

    public AbstractCreationCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target, CardColor color) {
        super(cardID, cost, type, rarity, target, color);
        switch (type) {
            case ATTACK:
                this.setBackgroundTexture(MainModfile.ATTACK_S_ART_PURPLE, MainModfile.ATTACK_L_ART_PURPLE);
                break;
            case POWER:
                this.setBackgroundTexture(MainModfile.POWER_S_ART_PURPLE, MainModfile.POWER_L_ART_PURPLE);
                break;
            default:
                this.setBackgroundTexture(MainModfile.SKILL_S_ART_PURPLE, MainModfile.SKILL_L_ART_PURPLE);
                break;
        }
    }

    public abstract void updateElementData(ElementData data);
}
