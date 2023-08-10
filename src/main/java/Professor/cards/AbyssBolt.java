package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.red.LimitBreak;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;

import static Professor.MainModfile.makeID;

public class AbyssBolt extends AbstractEasyCard {
    public final static String ID = makeID(AbyssBolt.class.getSimpleName());
    private boolean discarded;

    public AbyssBolt() {
        super(ID, -2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.NONE);
        baseDamage = damage = 14;
        selfRetain = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if(discarded || (purgeOnUse && isInAutoplay)) {
            discarded = false;
            addToBot(new VFXAction(new MindblastEffect(Wiz.adp().dialogX, Wiz.adp().dialogY, Wiz.adp().flipHorizontal)));
            addToBot(new DamageAllEnemiesAction(Wiz.adp(), multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
        }
    }

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return discarded || (purgeOnUse && isInAutoplay);
    }

    @Override
    public void triggerOnManualDiscard() {
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(this, false));
        discarded = true;
        Wiz.adp().discardPile.removeCard(this);
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
}