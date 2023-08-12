package Professor.cards.creations;

import Professor.cards.abstracts.AbstractCreationCard;
import Professor.util.CardArtRoller;
import Professor.util.KeywordManager;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
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
        if (data != null) {
            baseMagicNumber += data.g;
            magicNumber = baseMagicNumber;
        }
        //Worst: 2 draw
        //Best: 4 draw
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
        addToBot(new DrawCardAction(magicNumber, new AbstractGameAction() {
            @Override
            public void update() {
                for (AbstractCard c : DrawCardAction.drawnCards) {
                    if (c.canUpgrade()) {
                        c.upgrade();
                    }
                }
                this.isDone = true;
            }
        }));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(1);
        //upgradeSecondMagic(1);
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