package Professor.cutStuff.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.cutStuff.powers.ExposedPower;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.EntangleEffect;

import static Professor.MainModfile.makeID;

public class RestraintSilk extends AbstractEasyCard {
    public final static String ID = makeID(RestraintSilk.class.getSimpleName());

    public RestraintSilk() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseMagicNumber = magicNumber = 3;
        exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.forAllMonstersLiving(mon -> {
            addToBot(new VFXAction(new EntangleEffect(p.hb.cX, p.hb.cY, mon.hb.cX, mon.hb.cY)));
            addToBot(new SFXAction("POWER_ENTANGLED", 0.05f));
            Wiz.applyToEnemy(mon, new ExposedPower(mon, magicNumber));
            Wiz.applyToEnemy(mon, new VulnerablePower(mon, magicNumber, false));
        });
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, Color.GRAY, WHITE, Color.GRAY, WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return RestraintSilk.class.getSimpleName();
    }
}