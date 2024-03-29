package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import Professor.vfx.ColoredSmokeBombEffect;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class TabooDrop extends AbstractEasyCard {
    public final static String ID = makeID(TabooDrop.class.getSimpleName());

    public TabooDrop() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ALL_ENEMY);
        baseDamage = damage = 7;
        //baseMagicNumber = magicNumber = 1;
        baseBlock = block = 7;
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.forAllMonstersLiving(mon -> addToBot(new VFXAction(new ColoredSmokeBombEffect(mon.hb.cX, mon.hb.cY, Color.PURPLE))));
        blck();
        allDmg(AbstractGameAction.AttackEffect.POISON);
        //Wiz.forAllMonstersLiving(mon -> addToBot(new ApplyPowerActionWithFollowup(new ApplyPowerAction(mon, p, new StrengthPower(mon, -this.magicNumber)), new ApplyPowerAction(mon, p, new GainStrengthPower(mon, this.magicNumber)))));
        //Wiz.forAllMonstersLiving(mon -> Wiz.applyToEnemy(mon, new WeakPower(mon, magicNumber, false)));
    }

    @Override
    public void upp() {
        upgradeDamage(3);
        upgradeBlock(3);
        //upgradeMagicNumber(1);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, PHLOX, BLACK, PHLOX, BLACK, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return TabooDrop.class.getSimpleName();
    }
}