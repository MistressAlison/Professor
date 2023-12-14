package Professor;

import Professor.cards.Catch;
import Professor.cards.Defend;
import Professor.cards.EmeraldBand;
import Professor.cards.Strike;
import Professor.cards.interfaces.SkillAnimationAttack;
import Professor.relics.MemoriaBracelet;
import Professor.util.TexLoader;
import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.MathUtils;
import com.brashmonkey.spriter.Player;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.CharSelectInfo;

import java.util.ArrayList;
import java.util.List;

import static Professor.MainModfile.*;

public class TheProfessor extends CustomPlayer {
    private static final String[] orbTextures = {
            modID + "Resources/images/char/mainChar/orb/layer1.png",
            modID + "Resources/images/char/mainChar/orb/layer2.png",
            modID + "Resources/images/char/mainChar/orb/layer3.png",
            modID + "Resources/images/char/mainChar/orb/layer4.png",
            modID + "Resources/images/char/mainChar/orb/layer5.png",
            modID + "Resources/images/char/mainChar/orb/layer6.png",
            modID + "Resources/images/char/mainChar/orb/layer1d.png",
            modID + "Resources/images/char/mainChar/orb/layer2d.png",
            modID + "Resources/images/char/mainChar/orb/layer3d.png",
            modID + "Resources/images/char/mainChar/orb/layer4d.png",
            modID + "Resources/images/char/mainChar/orb/layer5d.png",};
    static final String ID = makeID("TheProfessor");
    static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    static final String[] NAMES = characterStrings.NAMES;
    static final String[] TEXT = characterStrings.TEXT;

    private static class AbbyEnergyBubble {
        public static final Texture IMG = TexLoader.getTexture(makeCharacterPath("mainChar/orb/bubble.png"));
        public static final Texture IMG_DARK = TexLoader.getTexture(makeCharacterPath("mainChar/orb/bubbled.png"));
        public static final int SIZE = IMG.getWidth();
        public static final float HALFSIZE = (float)SIZE / 2f;

        private float time = MathUtils.random(0f, 100f);
        private float startX = MathUtils.random(-32f, 160f);
        private float xSway = MathUtils.random(15f, 35f);
        private float swayTime = MathUtils.random(0.8f, 1.8f) * (float)Math.PI;
        private float ySpeed = MathUtils.random(30f, 60f);
        public float scale = MathUtils.random(0.5f, 1f);
        public float x, y;

        public void update(float delta) {
            time += delta;
            x = startX + (float)Math.sin(time * swayTime) * xSway;
            y = (time * ySpeed) % 196f - 32f;
        }
    }

    public TheProfessor(String name, PlayerClass setClass) {
        super(name, setClass, new CustomEnergyOrb(orbTextures, makeCharacterPath("mainChar/orb/vfx.png"), new float[] {40f, 60f, 80f, 40f, 0f}) {
            private FrameBuffer fbo = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false, false);
            private Texture mask = TexLoader.getTexture(makeCharacterPath("mainChar/orb/mask.png"));
            private ArrayList<AbbyEnergyBubble> bubbles = new ArrayList<>();
            private float ORB_IMG_SCALE = 1.15F * Settings.scale;

            @Override
            public void updateOrb(int energyCount) {
                super.updateOrb(energyCount);
                float delta = Gdx.graphics.getDeltaTime() * (energyCount == 0 ? 0.25f : 1f);
                if (bubbles.size() == 0)
                    for (int i = 0; i < 16; i++)
                        bubbles.add(new AbbyEnergyBubble());
                for (AbbyEnergyBubble bubble : bubbles)
                    bubble.update(delta);
            }

            @Override
            public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y) {
                sb.end();
                fbo.begin();
                Gdx.gl.glClearColor(0, 0, 0, 0);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
                Gdx.gl.glColorMask(true, true, true, true);
                sb.begin();
                sb.setColor(Color.WHITE);
                sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                if (enabled)
                    for(int i = 0; i < energyLayers.length; ++i)
                        sb.draw(energyLayers[i], current_x - 64f, current_y - 64f, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, angles[i], 0, 0, 128, 128, false, false);
                else
                    for(int i = 0; i < noEnergyLayers.length; ++i)
                        sb.draw(noEnergyLayers[i], current_x - 64.0F, current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, angles[i], 0, 0, 128, 128, false, false);
                for (AbbyEnergyBubble b : bubbles)
                    sb.draw(enabled ? AbbyEnergyBubble.IMG : AbbyEnergyBubble.IMG_DARK, current_x - 64f + b.x, current_y - 64f + b.y, AbbyEnergyBubble.HALFSIZE, AbbyEnergyBubble.HALFSIZE, AbbyEnergyBubble.SIZE, AbbyEnergyBubble.SIZE, ORB_IMG_SCALE * b.scale, ORB_IMG_SCALE * b.scale, 0, 0, 0, AbbyEnergyBubble.SIZE, AbbyEnergyBubble.SIZE, false, false);
                sb.setBlendFunction(0, GL20.GL_SRC_ALPHA);
                sb.setColor(Color.WHITE);
                sb.draw(mask, current_x - 256, current_y - 256, 256, 256, 512, 512, ORB_IMG_SCALE, ORB_IMG_SCALE, 0, 0, 0, 512, 512, false, false);
                sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                sb.end();
                fbo.end();
                sb.begin();
                TextureRegion drawTex = new TextureRegion(fbo.getColorBufferTexture());
                drawTex.flip(false, true);
                sb.draw(drawTex, -Settings.VERT_LETTERBOX_AMT, -Settings.HORIZ_LETTERBOX_AMT);
                sb.draw(baseLayer, current_x - 64.0F, current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0, 0, 0, 128, 128, false, false);
            }
        }, new CustomSpriterAnimation(
                modID + "Resources/images/char/mainChar/OJCharacter.scml"));
        Player.PlayerListener listener = new CustomAnimationListener(this);
        ((CustomSpriterAnimation)this.animation).myPlayer.addListener(listener);
        initializeClass(null,
                SHOULDER1,
                SHOULDER2,
                CORPSE,
                getLoadout(), 10.0F, -10.0F, 220.0F, 240.0F, new EnergyManager(3)); // 20.0F, -10.0F, 166.0F, 327.0F


        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 240.0F * Settings.scale);
    }

    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                65,
                65,
                0,
                99,
                5, this, getStartingRelics(),
                getStartingDeck(), false);
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Strike.ID);
        retVal.add(Catch.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(Defend.ID);
        retVal.add(EmeraldBand.ID);
        return retVal;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(MemoriaBracelet.ID);
        return retVal;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_POISON2", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false);
    }

    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_POISON2";
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 4;
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return Enums.MEDIUM_RUBY_COLOR;
    }

    @Override
    public Color getCardTrailColor() {
        return MainModfile.MEDIUM_RUBY_COLOR.cpy();
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new Catch();
    }

    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return NAMES[1];
    }

    @Override
    public AbstractPlayer newInstance() {
        return new TheProfessor(name, chosenClass);
    }

    @Override
    public Color getCardRenderColor() {
        return MainModfile.MEDIUM_RUBY_COLOR.cpy();
    }

    @Override
    public Color getSlashAttackColor() {
        return MainModfile.MEDIUM_RUBY_COLOR.cpy();
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.FIRE};
    }

    @Override
    public Texture getCutsceneBg() {
        return ImageMaster.loadImage("ProfessorResources/images/panels/bkg.png");
    }

    @Override
    public List<CutscenePanel> getCutscenePanels() {
        List<CutscenePanel> panels = new ArrayList<>();
        panels.add(new CutscenePanel("ProfessorResources/images/panels/HeartPanel.png", "UNLOCK_PING"));
        return panels;
    }

    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }

    @Override
    public String getVampireText() {
        return TEXT[2];
    }

    public static class Enums {
        @SpireEnum
        public static AbstractPlayer.PlayerClass THE_PROFESSOR;
        @SpireEnum(name = "MEDIUM_RUBY_COLOR")
        public static AbstractCard.CardColor MEDIUM_RUBY_COLOR;
        @SpireEnum(name = "MEDIUM_RUBY_COLOR")
        @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    @Override
    public void onVictory() {
        super.onVictory();
        playAnimation("happy");
    }

    @Override
    public void useCard(AbstractCard c, AbstractMonster monster, int energyOnUse) {
        super.useCard(c, monster, energyOnUse);
        switch (c.type) {
            case ATTACK:
                RandomChatterHelper.showChatter(RandomChatterHelper.getAttackText(), cardTalkProbability, enableCardBattleTalkEffect);
                if (c instanceof SkillAnimationAttack) {
                    playAnimation("skill");
                } else {
                    playAnimation("attack");
                }
                break;
            case POWER:
                RandomChatterHelper.showChatter(RandomChatterHelper.getPowerText(), cardTalkProbability, enableCardBattleTalkEffect);
                playAnimation("happy");
                break;
            default:
                RandomChatterHelper.showChatter(RandomChatterHelper.getSkillText(), cardTalkProbability, enableCardBattleTalkEffect);
                playAnimation("skill");
                break;
        }
    }

    public void damage(DamageInfo info) {
        boolean hadBlockBeforeSuper = this.currentBlock > 0;
        super.damage(info);
        boolean hasBlockAfterSuper = this.currentBlock > 0;
        boolean tookNoDamage = this.lastDamageTaken == 0;
        if (hadBlockBeforeSuper && (hasBlockAfterSuper || tookNoDamage)) {
            RandomChatterHelper.showChatter(RandomChatterHelper.getBlockedDamageText(), damagedTalkProbability, enableDamagedBattleTalkEffect);
            playAnimation("happy");
        } else {
            if (info.owner != null && info.type != DamageInfo.DamageType.THORNS && info.output > 0) {
                if (info.output >= 15) {
                    RandomChatterHelper.showChatter(RandomChatterHelper.getHeavyDamageText(), damagedTalkProbability, enableDamagedBattleTalkEffect);
                } else {
                    RandomChatterHelper.showChatter(RandomChatterHelper.getLightDamageText(), damagedTalkProbability, enableDamagedBattleTalkEffect);
                }
            } else if (info.type == DamageInfo.DamageType.THORNS && info.output > 0) {
                RandomChatterHelper.showChatter(RandomChatterHelper.getFieldDamageText(), damagedTalkProbability, enableDamagedBattleTalkEffect);
            }
            playAnimation("hurt");
        }
    }

    public CustomSpriterAnimation getAnimation() {
        return (CustomSpriterAnimation) this.animation;
    }

    public void playAnimation(String name) {
        ((CustomSpriterAnimation)this.animation).myPlayer.setAnimation(name);
    }

    public void stopAnimation() {
        CustomSpriterAnimation anim = (CustomSpriterAnimation) this.animation;
        int time = anim.myPlayer.getAnimation().length;
        anim.myPlayer.setTime(time);
        anim.myPlayer.speed = 0;
    }

    public void resetToIdleAnimation() {
        playAnimation("idle");
    }

    @Override
    public void playDeathAnimation() {
        RandomChatterHelper.showChatter(RandomChatterHelper.getKOText(), preTalkProbability, enablePreBattleTalkEffect); // I don't think this works
        playAnimation("ko");
    }

    @Override
    public void heal(int healAmount) {
        if (healAmount > 0) {
            if (RandomChatterHelper.showChatter(RandomChatterHelper.getHealingText(), damagedTalkProbability, enableDamagedBattleTalkEffect)){ //Technically changes your hp, lol
                playAnimation("happy");
            }
        }
        super.heal(healAmount);
    }

    @Override
    public void preBattlePrep() {
        playAnimation("idle");
        super.preBattlePrep();
        boolean bossFight = false;
        for (AbstractMonster mons : AbstractDungeon.getMonsters().monsters) {
            if (mons.type == AbstractMonster.EnemyType.BOSS) {
                bossFight = true;
                break;
            }
        }
        if (AbstractDungeon.getCurrRoom().eliteTrigger || bossFight) {
            RandomChatterHelper.showChatter(RandomChatterHelper.getBossFightText(), preTalkProbability, enablePreBattleTalkEffect);
        } else {
            if (AbstractDungeon.player.currentHealth < AbstractDungeon.player.maxHealth*0.5f) {
                RandomChatterHelper.showChatter(RandomChatterHelper.getLowHPBattleStartText(), preTalkProbability, enablePreBattleTalkEffect);
            } else {
                RandomChatterHelper.showChatter(RandomChatterHelper.getBattleStartText(), preTalkProbability, enablePreBattleTalkEffect);
            }
        }
    }

    public float[] _lightsOutGetCharSelectXYRI() {
        return new float[] {
                1783*Settings.scale, 765*Settings.scale, 500f, 1.5f,
                60*Settings.scale, 699*Settings.scale, 300f, 1.2f,
                //311*Settings.scale, 656*Settings.scale, 200f, 1.0f,
                545*Settings.scale, 633*Settings.scale, 150f, 1.0f,
                700*Settings.scale, 627*Settings.scale, 100f, 1.0f
        };
    }

    public Color[] _lightsOutGetCharSelectColor() {
        return new Color[] {
                Color.WHITE,
                Color.WHITE,
                //Color.WHITE,
                Color.WHITE,
                Color.WHITE
        };
    }
}
