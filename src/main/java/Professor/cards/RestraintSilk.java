package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.powers.ExposedPower;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;

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
            addToBot(new SFXAction("POWER_SHACKLE", 0.05f));
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