package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Shockwave;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.FreeAttackPower;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;

import static Professor.MainModfile.makeID;

public class DemonClaws extends AbstractEasyCard {
    public final static String ID = makeID(DemonClaws.class.getSimpleName());

    public DemonClaws() {
        super(ID, 2, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = damage = 7;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            this.addToBot(new VFXAction(new ClashEffect(m.hb.cX, m.hb.cY), 0.1F));
        }
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE, true));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE, true));
        Wiz.applyToSelf(new FreeAttackPower(p, 1));
    }

    @Override
    public void upp() {
        upgradeDamage(2);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, Color.GRAY, WHITE, Color.FIREBRICK, BLACK, false);
    }

    @Override
    public String cardArtCopy() {
        return Shockwave.ID;
    }

    @Override
    public String itemArt() {
        return DemonClaws.class.getSimpleName();
    }

    @Override
    public float itemScale() {
        return 0.8f;
    }
}