//package Professor.ui;
//
//import Professor.patches.ArchetypeHelper;
//import Professor.vfx.ParticleEffect;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.megacrit.cardcrawl.cards.AbstractCard;
//import com.megacrit.cardcrawl.core.Settings;
//import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
//import com.megacrit.cardcrawl.helpers.Hitbox;
//import com.megacrit.cardcrawl.helpers.ImageMaster;
//import com.megacrit.cardcrawl.helpers.MathHelper;
//import com.megacrit.cardcrawl.vfx.BobEffect;
//
//public class SynthesisSlot {
//    public float cX = 0.0F;
//    public float cY = 0.0F;
//    public float tX;
//    public float tY;
//    protected float angle;
//    public AbstractCard card;
//    protected BobEffect bobEffect;
//    private float vfxTimer;
//    public Hitbox hb;
//    public boolean isFire, isIce, isBolt, isWind;
//    private boolean snapped;
//    private final float snapThreshold = 15f * Settings.scale;
//
//    public SynthesisSlot(float x, float y) {
//        this.hb = new Hitbox(96.0F * Settings.scale, 96.0F * Settings.scale);
//        this.bobEffect = new BobEffect(3.0F * Settings.scale, 3.0F);
//        this.cX = x;
//        this.cY = y;
//    }
//
//    public void setTarget(float x, float y) {
//        this.tX = x;
//        this.tY = y;
//        this.hb.move(this.tX, this.tY);
//    }
//
//    public void update() {
//        updateAnimation();
//        if (card != null) {
//            card.target_y = cY + this.bobEffect.y / 8.0F;
//            card.target_x = cX;
//            card.targetAngle = 0;
//            if (snapped) {
//                card.current_x = card.target_x;
//                card.current_y = card.target_y;
//            }
//            card.update();
//            card.hb.update();
//            if (!snapped && Math.abs(card.current_x-cX) <= snapThreshold && Math.abs(card.current_y-cY) <= snapThreshold) {
//                snapped = true;
//            }
//            if (card.hb.hovered && !SynthesisPanel.hovered && !SynthesisPanel.processing) {
//                card.targetDrawScale = 0.75f;
//            } else {
//                card.targetDrawScale = 0.2f;
//            }
//        }
//    }
//
//    public void updateAnimation() {
//        this.bobEffect.update();
//        this.cX = MathHelper.orbLerpSnap(this.cX, this.tX);
//        this.cY = MathHelper.orbLerpSnap(this.cY, this.tY);
//        if (snapped) {
//            this.cX = tX;
//            this.cY = tY;
//        }
//        this.angle += Gdx.graphics.getDeltaTime() * 10.0F;
//        if (this.card != null) {
//            this.vfxTimer -= Gdx.graphics.getDeltaTime();
//            if (vfxTimer < 0) {
//                if (isFire) {
//                    AbstractDungeon.effectsQueue.add(new ParticleEffect(Color.RED.cpy(), card.hb.cX, card.hb.cY));
//                    vfxTimer += 0.05f;
//                }
//                if (isIce) {
//                    AbstractDungeon.effectsQueue.add(new ParticleEffect(Color.CYAN.cpy(), card.hb.cX, card.hb.cY));
//                    vfxTimer += 0.05f;
//                }
//                if (isBolt) {
//                    AbstractDungeon.effectsQueue.add(new ParticleEffect(Color.GOLD.cpy(), card.hb.cX, card.hb.cY));
//                    vfxTimer += 0.05f;
//                }
//                if (isWind) {
//                    AbstractDungeon.effectsQueue.add(new ParticleEffect(Color.GREEN.cpy(), card.hb.cX, card.hb.cY));
//                    vfxTimer += 0.05f;
//                }
//            }
//        }
//    }
//
//    public void render(SpriteBatch sb) {
//        if (this.card != null) {
//            card.render(sb);
//        } else {
//            sb.draw(ImageMaster.ORB_SLOT_2, this.cX - 48.0F - this.bobEffect.y / 8.0F, this.cY - 48.0F + this.bobEffect.y / 8.0F, 48.0F, 48.0F, 96.0F, 96.0F, 1, 1, 0.0F, 0, 0, 96, 96, false, false);
//            sb.draw(ImageMaster.ORB_SLOT_1, this.cX - 48.0F + this.bobEffect.y / 8.0F, this.cY - 48.0F - this.bobEffect.y / 8.0F, 48.0F, 48.0F, 96.0F, 96.0F, 1, 1, this.angle, 0, 0, 96, 96, false, false);
//        }
//        this.hb.render(sb);
//    }
//
//    public boolean isEmpty() {
//        return card == null;
//    }
//
//    public void addCard(AbstractCard card) {
//        this.card = card;
//        isFire = ArchetypeHelper.isFire(card);
//        isIce = ArchetypeHelper.isIce(card);
//        isBolt = ArchetypeHelper.isBolt(card);
//        isWind = ArchetypeHelper.isWind(card);
//    }
//
//    public boolean isHovered() {
//        return !isEmpty() && card.hb.hovered;
//    }
//
//    public boolean isSnapped() {
//        return snapped;
//    }
//}
