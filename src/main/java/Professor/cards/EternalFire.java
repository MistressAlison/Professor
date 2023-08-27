package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.patches.EnterCardGroupPatches;
import Professor.util.CardArtRoller;
import Professor.util.Wiz;
import basemod.abstracts.CustomSavable;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.tempCards.Miracle;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlameParticleEffect;

import static Professor.MainModfile.makeID;

public class EternalFire extends AbstractEasyCard implements CustomSavable<Integer>, EnterCardGroupPatches.OnEnterCardGroupCard {
    public final static String ID = makeID(EternalFire.class.getSimpleName());

    public EternalFire() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 5;
        baseDamage = damage = 10;
        misc = 0;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public void upp() {
        upgradeMagicNumber(2);
    }

    @Override
    public void onRemoveFromMasterDeck() {
        misc += magicNumber;
        baseDamage += magicNumber;
        EternalFire copy = (EternalFire) makeSameInstanceOf();
        AbstractDungeon.player.masterDeck.addToTop(copy);
        CardCrawlGame.sound.play("ATTACK_FIRE");
        for(int i = 0; i < 75; ++i) {// 21
            AbstractDungeon.topLevelEffectsQueue.add(new FlameParticleEffect(Settings.WIDTH/2f, Settings.HEIGHT/2f));// 22
        }
        //AbstractDungeon.topLevelEffectsQueue.add(new JitteryShowCardBrieflyEffect(copy.makeStatEquivalentCopy()));
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, mix(Color.GRAY, AZURE), WHITE, mix(Color.GRAY, AZURE), BLACK, false);
    }

    @Override
    public String cardArtCopy() {
        return Miracle.ID;
    }

    @Override
    public String itemArt() {
        return EternalFire.class.getSimpleName();
    }

    @Override
    public Integer onSave() {
        return misc;
    }

    @Override
    public void onLoad(Integer integer) {
        misc = integer;
        baseDamage += misc;
        damage += misc;
    }

    @Override
    public void onEnter(CardGroup g) {
        if (g == Wiz.adp().exhaustPile) {
            Wiz.adp().exhaustPile.removeCard(this);
            misc += magicNumber;
            baseDamage += magicNumber;
            EternalFire copy = (EternalFire) makeSameInstanceOf();
            addToBot(new MakeTempCardInHandAction(copy, true, true));
        }
    }
}