package Professor.cards.creations;

import Professor.cards.abstracts.AbstractCreationCard;
import Professor.util.CardArtRoller;
import Professor.util.KeywordManager;
import Professor.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

import static Professor.MainModfile.makeID;

@NoCompendium
@NoPools
public class Luft extends AbstractCreationCard {
    public final static String ID = makeID(Luft.class.getSimpleName());

    public Luft() {
        this(null);
    }

    public Luft(ElementData data) {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        updateElementData(data);
        addCustomKeyword(KeywordManager.LUFT);
    }

    @Override
    public void updateElementData(ElementData data) {
        baseMagicNumber = magicNumber = 2;
        baseSecondMagic = secondMagic = 1;
        if (data != null) {
            baseMagicNumber += data.g;
            magicNumber = baseMagicNumber;
            baseSecondMagic += data.b;
            secondMagic = baseSecondMagic;
        }
    }

    @Override
    public AbstractCard makeCopy() {
        if (data != null) {
            return new Luft(data.cpy());
        }
        return super.makeCopy();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("APPEAR"));
        addToBot(new SFXAction("ATTACK_WHIFF_2"));
        addToBot(new VFXAction(new WhirlwindEffect(), 0.2F));
        //allDmg(AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        addToBot(new DrawCardAction(magicNumber));
        Wiz.applyToSelf(new DrawCardNextTurnPower(p, secondMagic));
        //allDmg(AbstractGameAction.AttackEffect.FIRE);
        /*if (magicNumber > 0) {
            Wiz.applyToEnemy(m, new BurnPower(m, p, magicNumber));
        }*/
    }

    @Override
    public void upp() {
        //upgradeDamage(2);
        upgradeMagicNumber(1);
        upgradeSecondMagic(1);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, SPRING_GREEN, WHITE, SPRING_GREEN, WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return Luft.class.getSimpleName();
    }
}