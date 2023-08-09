package Professor.cutStuff.cards;

import CardAugments.util.Wiz;
import Professor.cards.abstracts.AbstractEasyCard;
import Professor.patches.ArchetypeHelper;
import Professor.patches.CustomTags;
import Professor.util.CardArtRoller;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;

import static Professor.MainModfile.makeID;

public class GoldUni extends AbstractEasyCard {
    public final static String ID = makeID(GoldUni.class.getSimpleName());

    public GoldUni() {
        super(ID, 3, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = damage = 24;
        baseMagicNumber = magicNumber = 4;
        tags.add(CustomTags.PROF_UNI);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            addToBot(new VFXAction(new VerticalImpactEffect(m.hb.cX + m.hb.width / 4.0F, m.hb.cY - m.hb.height / 4.0F)));
        }
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int lastMagic = magicNumber;
        int basebase = baseDamage;
        baseDamage += magicNumber * countCards();
        super.calculateCardDamage(mo);
        baseDamage = basebase;
        isDamageModified = baseDamage != damage;
        if (lastMagic != magicNumber) {
            super.calculateCardDamage(mo);
        }
    }

    @Override
    public void applyPowers() {
        int lastMagic = magicNumber;
        int basebase = baseDamage;
        baseDamage += magicNumber * countCards();
        super.applyPowers();
        baseDamage = basebase;
        isDamageModified = baseDamage != damage;
        if (lastMagic != magicNumber) {
            super.applyPowers();
        }
    }

    private int countCards() {
        return (int) Wiz.cardsPlayedThisCombat().stream().filter(ArchetypeHelper::isBolt).count();
    }

    @Override
    public void upp() {
        upgradeDamage(8);
        upgradeMagicNumber(1);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, Color.GOLD, WHITE, Color.GOLD, BLACK, false);
    }

    @Override
    public String cardArtCopy() {
        return Miracle.ID;
    }

    @Override
    public String itemArt() {
        return GoldUni.class.getSimpleName();
    }
}