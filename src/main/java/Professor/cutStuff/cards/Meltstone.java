package Professor.cutStuff.cards;

import Professor.cardmods.UnlockedMod;
import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.CardArtRoller;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class Meltstone extends AbstractEasyCard {
    public final static String ID = makeID(Meltstone.class.getSimpleName());

    public Meltstone() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = damage = 9;
        baseMagicNumber = magicNumber = 1;
        CardModifierManager.addModifier(this, new UnlockedMod());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.FIRE);
        addToBot(new ExhaustAction(magicNumber, false, false, false));
    }

    @Override
    public void upp() {
        upgradeDamage(2+timesUpgraded);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, ORANGE, YELLOW, THREE_QUARTER_GRAY, mix(ORANGE, YELLOW), false);
    }

    @Override
    public String cardArtCopy() {
        return Miracle.ID;
    }

    @Override
    public String itemArt() {
        return Meltstone.class.getSimpleName();
    }
}