package Professor.cards;

import Professor.cards.abstracts.AbstractEasyCard;
import Professor.util.CardArtRoller;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;

import static Professor.MainModfile.makeID;

public class GoldenCrown extends AbstractEasyCard {
    public final static String ID = makeID(GoldenCrown.class.getSimpleName());

    public GoldenCrown() {
        super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
        FleetingField.fleeting.set(this, true);
        tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new SelectCardsInHandAction(1, CardCrawlGame.languagePack.getUIString("DualWieldAction").TEXT[0], l -> {
            for (AbstractCard c : l) {
                addToTop(new VFXAction(new ShowCardAndObtainEffect(c.makeSameInstanceOf(), Settings.WIDTH/2f, Settings.HEIGHT/2f)));
            }
        }));
    }

    @Override
    public void upp() {}

    @Override
    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
        return new CardArtRoller.ReskinInfo(ID, Color.GOLDENROD, WHITE, Color.GOLDENROD, WHITE, false);
    }

    @Override
    public String cardArtCopy() {
        return VoidCard.ID;
    }

    @Override
    public String itemArt() {
        return GoldenCrown.class.getSimpleName();
    }
}