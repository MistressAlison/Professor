package Professor.cards.creations;

import Professor.cards.abstracts.AbstractCreationCard;
import Professor.util.CardArtRoller;
import Professor.util.KeywordManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

@NoCompendium
@NoPools
public class NA extends AbstractCreationCard {
    public final static String ID = makeID(NA.class.getSimpleName());

    private static final int BASE_COST = 5;

    public NA() {
        this(null);
    }

    public NA(ElementData data) {
        super(ID, BASE_COST, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ALL_ENEMY);
        updateElementData(data);
        addCustomKeyword(KeywordManager.NA);
        setDisplayRarity(CardRarity.RARE);
    }

    @Override
    public void updateElementData(ElementData data) {
        baseDamage = damage = 50;
        isMultiDamage = true;
        if (data != null) {
            this.data = data;
            int newcost = BASE_COST;
            if (data.r >= 2) {
                newcost--;
            }
            if (data.b >= 2) {
                newcost--;
            }
            if (data.y >= 2) {
                newcost--;
            }
            if (data.g >= 2) {
                newcost--;
            }
            if (newcost != BASE_COST) {
                upgradeBaseCost(newcost);
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        if (data != null) {
            return new NA(data.cpy());
        }
        return super.makeCopy();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.FIRE);
    }

    @Override
    public void upp() {
        upgradeDamage(20);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, BLACK, WHITE, BLACK, WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return NA.class.getSimpleName();
    }
}