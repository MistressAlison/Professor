package Professor.cards;

import Professor.actions.EasyXCostAction;
import Professor.cards.abstracts.AbstractEasyCard;
import Professor.patches.DelayedExhaustPatches;
import Professor.powers.BroomPower;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;

import static Professor.MainModfile.makeID;

public class MysteriousBroom extends AbstractEasyCard {
    public final static String ID = makeID(MysteriousBroom.class.getSimpleName());

    public MysteriousBroom() {
        super(ID, -1, CardType.SKILL, CardRarity.RARE, CardTarget.NONE);
        baseMagicNumber = magicNumber = 4;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new EasyXCostAction(this, (base, params) -> {
            int effect = base;
            for (int i : params) {
                effect += i;
            }
            if (effect >= magicNumber) {
                Wiz.applyToSelfTop(new BroomPower(p));
                addToTop(new SkipEnemiesTurnAction());
                addToTop(new VFXAction(new WhirlwindEffect(new Color(1.0F, 0.9F, 0.4F, 1.0F), true)));
                addToTop(new SFXAction("ATTACK_WHIFF_2", 0.2f));
                DelayedExhaustPatches.DelayedExhaustField.delayedExhaust.set(this, true);
            }
            if (effect > 0) {
                addToTop(new ExhaustAction(effect, false, true, true));
            }
            return true;
        }));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(-1);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, BLACK, WHITE, RED, mix(Color.GOLD, Color.GRAY), false);
    }

    @Override
    public String cardArtCopy() {
        return Miracle.ID;
    }

    @Override
    public String itemArt() {
        return MysteriousBroom.class.getSimpleName();
    }
}