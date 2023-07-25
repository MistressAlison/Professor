package Professor.cards.abstracts;

public abstract class AbstractCreationCard extends AbstractTokenCard {
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
    }

    public AbstractCreationCard(String cardID, int cost, CardType type, CardRarity rarity, CardTarget target, CardColor color) {
        super(cardID, cost, type, rarity, target, color);
    }

    public abstract void updateElementData(ElementData data);
}
