package Professor.cards.creations;

import Professor.cards.abstracts.AbstractCreationCard;
import Professor.damageMods.StaggerDamage;
import Professor.util.CardArtRoller;
import Professor.util.KeywordManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.evacipated.cardcrawl.mod.stslib.damagemods.BindingHelper;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Collections;

import static Professor.MainModfile.makeID;

@NoCompendium
@NoPools
public class LautePlajig extends AbstractCreationCard {
    public final static String ID = makeID(LautePlajig.class.getSimpleName());

    public LautePlajig() {
        this(null);
    }

    public LautePlajig(ElementData data) {
        super(ID, 2, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ALL_ENEMY);
        updateElementData(data);
        addCustomKeyword(KeywordManager.LAUTE_PLAJIG);
        setDisplayRarity(CardRarity.UNCOMMON);
    }

    @Override
    public void updateElementData(ElementData data) {
        baseDamage = damage = 4; // Damage
        baseMagicNumber = magicNumber = 1; // Stagger
        baseSecondMagic = secondMagic = 4; // Hits
        if (data != null) {
            baseDamage += data.y;
            damage = baseDamage;
            baseMagicNumber += data.r;
            magicNumber = baseMagicNumber;
        }
        //Worst: Deal 4x4, 1x4 Stagger = 16, 4
        //Best: Deal 6x4, 3x4 Stagger = 24, 12

    }

    @Override
    public AbstractCard makeCopy() {
        if (data != null) {
            return new LautePlajig(data.cpy());
        }
        return super.makeCopy();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0 ; i < secondMagic ; i ++) {
            addToBot(BindingHelper.makeAction(Collections.singletonList(new StaggerDamage(magicNumber)), new AttackDamageRandomEnemyAction(this, AbstractGameAction.AttackEffect.LIGHTNING)));
        }
    }

    @Override
    public void upp() {
        //upgradeDamage(2);
        upgradeSecondMagic(1);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, YELLOW, WHITE, YELLOW, WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return LautePlajig.class.getSimpleName();
    }
}