package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.patches.CustomTags;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import static Professor.MainModfile.makeID;

public class WaterUni extends AbstractEasyCard {
    public final static String ID = makeID(WaterUni.class.getSimpleName());

    public WaterUni() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 6;
        baseSecondMagic = secondMagic = 1;
        tags.add(CustomTags.PROF_UNI);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.applyToEnemy(m, new PoisonPower(m, p, magicNumber));
        Wiz.applyToEnemy(m, new WeakPower(m, secondMagic, false));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
        upgradeSecondMagic(1);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, mix(CYAN, WHITE), WHITE, mix(CYAN, WHITE), WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return WaterUni.class.getSimpleName();
    }
}