package Professor.cards;

import Professor.actions.InfuseCardsInHandAction;
import Professor.cardmods.DealAOEDamageMod;
import Professor.cards.abstracts.AbstractEasyCard;
import Professor.patches.CustomTags;
import Professor.util.CardArtRoller;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class EmperorUni extends AbstractEasyCard {
    public final static String ID = makeID(EmperorUni.class.getSimpleName());

    public EmperorUni() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = damage = 6;
        baseMagicNumber = magicNumber = 4;
        tags.add(CustomTags.PROF_UNI);
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        addToBot(new InfuseCardsInHandAction(1,  new DealAOEDamageMod(magicNumber)));
    }

    @Override
    public void upp() {
        upgradeDamage(2);
        upgradeMagicNumber(2);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, mix(YELLOW, Color.FIREBRICK), WHITE, mix(YELLOW, Color.FIREBRICK), WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return EmperorUni.class.getSimpleName();
    }
}