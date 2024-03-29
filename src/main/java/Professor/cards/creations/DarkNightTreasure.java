package Professor.cards.creations;

import Professor.cards.abstracts.AbstractCreationCard;
import Professor.util.CardArtRoller;
import Professor.util.KeywordManager;
import Professor.util.Wiz;
import basemod.patches.com.megacrit.cardcrawl.dungeons.AbstractDungeon.NoPools;
import basemod.patches.com.megacrit.cardcrawl.screens.compendium.CardLibraryScreen.NoCompendium;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.CollectorCurseEffect;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;

import static Professor.MainModfile.makeID;

@NoCompendium
@NoPools
public class DarkNightTreasure extends AbstractCreationCard {
    public final static String ID = makeID(DarkNightTreasure.class.getSimpleName());

    public DarkNightTreasure() {
        this(null);
    }

    public DarkNightTreasure(ElementData data) {
        super(ID, 1, CardType.SKILL, CardRarity.SPECIAL, CardTarget.ALL_ENEMY);
        updateElementData(data);
        addCustomKeyword(KeywordManager.DARK_NIGHT_TREASURE);
        setDisplayRarity(CardRarity.RARE);
    }

    @Override
    public void updateElementData(ElementData data) {
        baseMagicNumber = magicNumber = 20;
        if (data != null) {
            this.data = data;
            baseMagicNumber += 5*data.b;
            magicNumber = baseMagicNumber;
        }
        //Worst: 20 or less
        //Best: 30 or less
    }

    @Override
    public AbstractCard makeCopy() {
        if (data != null) {
            return new DarkNightTreasure(data.cpy());
        }
        return super.makeCopy();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SFXAction("INTIMIDATE"));
        //addToBot(new VFXAction(p, new IntimidateEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.5F));
        addToBot(new VFXAction(p, new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.PURPLE_RELIC_COLOR.cpy(), ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));
        Wiz.forAllMonstersLiving(mon -> {
            if (mon.currentHealth <= magicNumber) {
                addToBot(new SFXAction("MONSTER_COLLECTOR_DEBUFF"));
                addToBot(new VFXAction(new CollectorCurseEffect(mon.hb.cX, mon.hb.cY), 1.0F));
                addToBot(new VFXAction(new ExplosionSmallEffect(mon.hb.cX, mon.hb.cY)));
                addToBot(new InstantKillAction(mon));
            }
        });
    }

    @Override
    public void upp() {
        upgradeMagicNumber(5);
    }

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, mix(PURPLE, Color.GRAY), WHITE, mix(PURPLE, Color.GRAY), BLACK, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return DarkNightTreasure.class.getSimpleName();
    }
}