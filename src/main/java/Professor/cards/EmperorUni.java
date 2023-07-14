package Professor.cards;

import Professor.actions.ApplyCardModifierAction;
import Professor.actions.InfuseCardsInHandAction;
import Professor.actions.ModifyCardsInHandAction;
import Professor.cardmods.DealAOEDamageMod;
import Professor.cards.abstracts.AbstractEasyCard;
import Professor.patches.CustomTags;
import Professor.util.CardArtRoller;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static Professor.MainModfile.makeID;

public class EmperorUni extends AbstractEasyCard {
    public final static String ID = makeID(EmperorUni.class.getSimpleName());

    public EmperorUni() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseDamage = damage = 8;
        baseMagicNumber = magicNumber = 3;
        tags.add(CustomTags.PROF_UNI);
        exhaust = true;
        isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        allDmg(AbstractGameAction.AttackEffect.SLASH_DIAGONAL);
        addToBot(new InfuseCardsInHandAction(p.hand.size(), new DealAOEDamageMod(magicNumber)));
    }

    @Override
    public void upp() {
        upgradeDamage(3);
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