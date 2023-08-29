package Professor.cards.creations;

import Professor.cards.abstracts.AbstractCreationCard;
import Professor.util.CardArtRoller;
import Professor.util.KeywordManager;
import Professor.util.Wiz;
import Professor.vfx.BigExplosionVFX;
import Professor.vfx.BurnToAshEffect;
import Professor.vfx.DirectedParticleEffect;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import static Professor.MainModfile.makeID;

@NoCompendium
@NoPools
public class LautePlajig extends AbstractCreationCard {
    public final static String ID = makeID(LautePlajig.class.getSimpleName());

    public LautePlajig() {
        this(null);
    }

    public LautePlajig(ElementData data) {
        super(ID, 2, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY);
        updateElementData(data);
        addCustomKeyword(KeywordManager.LAUTE_PLAJIG);
        setDisplayRarity(CardRarity.UNCOMMON);
    }

    @Override
    public void updateElementData(ElementData data) {
        baseDamage = damage = 15; // Damage
        baseMagicNumber = magicNumber = 2; // Vuln
        if (data != null) {
            baseDamage += 5*data.y;
            damage = baseDamage;
            //baseMagicNumber += data.r;
            //magicNumber = baseMagicNumber;
        }
        //Worst: Deal 15
        //Best: Deal 25

    }

    @Override
    public AbstractCard makeCopy() {
        if (data != null) {
            return new LautePlajig(data.cpy());
        }
        return super.makeCopy();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            addToBot(new AbstractGameAction() {
                {
                    duration = startDuration = 0.75f;
                }
                @Override
                public void update() {
                    if (duration == startDuration) {
                        CardCrawlGame.sound.play("ORB_PLASMA_CHANNEL", 0.05F);
                        for (AbstractCard c : p.hand.group) {
                            c.superFlash(Color.GOLD.cpy());
                            for (int i = 0 ; i < 10 ; i++) {
                                float xo = MathUtils.random(-125.0F, 125.0F) * Settings.scale;
                                float yo = MathUtils.random(-125.0F, 125.0F) * Settings.scale;
                                Vector2 dir = new Vector2(m.hb.cX -c.hb.cX+xo, Settings.HEIGHT-yo - c.hb.cY+yo+250*Settings.scale);
                                dir.nor();
                                dir.scl(1500f * Settings.scale * MathUtils.random(1.0F, 1.25F));
                                AbstractDungeon.effectList.add(new DirectedParticleEffect(Color.GOLD.cpy(), c.hb.cX+xo, c.hb.cY+yo, dir.x, dir.y));
                            }
                        }
                    }
                    tickDuration();
                }
            });
            addToBot(new SFXAction("THUNDERCLAP", 0.05F));
            addToBot(new VFXAction(new LightningEffect(m.drawX, m.drawY), 0.15F));
            addToBot(new BigExplosionVFX(m));
            dmg(m, AbstractGameAction.AttackEffect.NONE, true);
        }
    }

    @Override
    public void applyPowers() {
        int base = baseDamage;
        baseDamage += magicNumber * Wiz.adp().hand.group.stream().filter(c -> c != this).count();
        super.applyPowers();
        baseDamage = base;
        isDamageModified = baseDamage != damage;
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        int base = baseDamage;
        baseDamage += magicNumber * Wiz.adp().hand.group.stream().filter(c -> c != this).count();
        super.calculateCardDamage(mo);
        baseDamage = base;
        isDamageModified = baseDamage != damage;
    }

    @Override
    public void upp() {
        //upgradeDamage(4);
        upgradeMagicNumber(1);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, YELLOW, WHITE, pastel(GOLDEN_YELLOW), WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return LautePlajig.class.getSimpleName();
    }
}