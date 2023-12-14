package Professor.cards.abstracts;

import Professor.MainModfile;
import Professor.TheProfessor;

public abstract class AbstractTokenCard extends AbstractEasyCard {
    public AbstractTokenCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target) {
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
        setOrbTexture(MainModfile.CARD_ENERGY_S_PURPLE, MainModfile.CARD_ENERGY_L_PURPLE);
    }

    public AbstractTokenCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target, CardColor color) {
        super(cardID, cost, type, rarity, target, color);
        if (color == TheProfessor.Enums.MEDIUM_RUBY_COLOR) {
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
            setOrbTexture(MainModfile.CARD_ENERGY_S_PURPLE, MainModfile.CARD_ENERGY_L_PURPLE);
        }
    }
}
