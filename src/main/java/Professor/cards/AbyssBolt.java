package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.patches.EnterCardGroupPatches;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.red.LimitBreak;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;

import static Professor.MainModfile.makeID;

public class AbyssBolt extends AbstractEasyCard implements EnterCardGroupPatches.OnEnterCardGroupCard {
    public final static String ID = makeID(AbyssBolt.class.getSimpleName());
    private boolean justDiscarded;

    public AbyssBolt() {
        super(ID, -2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.NONE);
        baseDamage = damage = 14;
        isMultiDamage = true;
        selfRetain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if(justDiscarded || (purgeOnUse && isInAutoplay)) {
            addToBot(new VFXAction(new MindblastEffect(Wiz.adp().dialogX, Wiz.adp().dialogY, Wiz.adp().flipHorizontal)));
            addToBot(new DamageAllEnemiesAction(Wiz.adp(), multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        }
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return justDiscarded || (purgeOnUse && isInAutoplay);
    }

    @Override
    public void upp() {
        upgradeDamage(4);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, Color.GRAY, WHITE, mix(Color.GRAY, Color.BROWN), Color.GOLD, false);
    }

    @Override
    public String cardArtCopy() {
        return LimitBreak.ID;
    }

    @Override
    public String itemArt() {
        return AbyssBolt.class.getSimpleName();
    }

    @Override
    public float itemScale() {
        return 0.8f;
    }

    @Override
    public void onEnter(CardGroup g) {
        if (g == Wiz.adp().discardPile) {
            if (!justDiscarded) {
                justDiscarded = true;
                addToTop(new NewQueueCardAction(this, null, true, true));
                Wiz.adp().discardPile.removeCard(this);
            } else {
                justDiscarded = false;
            }
        }
    }
}