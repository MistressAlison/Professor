//package Professor.cutStuff.recipes;
//
//import Professor.actions.BeginSynthesisAction;
//import Professor.cards.abstracts.AbstractCreationCard;
//import Professor.cards.abstracts.AbstractRecipeCard;
//import Professor.cutStuff.creations.ExplosiveUni;
//import Professor.patches.CustomTags;
//import Professor.util.CardArtRoller;
//import com.badlogic.gdx.graphics.Color;
//import com.megacrit.cardcrawl.cards.tempCards.Miracle;
//import com.megacrit.cardcrawl.characters.AbstractPlayer;
//import com.megacrit.cardcrawl.monsters.AbstractMonster;
//
//import static Professor.MainModfile.makeID;
//
//public class SynthesizeExplosiveUni extends AbstractRecipeCard {
//    public final static String ID = makeID(SynthesizeExplosiveUni.class.getSimpleName());
//
//    public SynthesizeExplosiveUni() {
//        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
//        baseMagicNumber = magicNumber = 2;
//        cardsToPreview = new ExplosiveUni();
//        tags.add(CustomTags.PROF_UNI);
//    }
//
//    @Override
//    public void use(AbstractPlayer p, AbstractMonster m) {
//        addToBot(new BeginSynthesisAction(this));
//    }
//
//    @Override
//    public void upp() {
//        upgradeMagicNumber(1);
//    }
//
//    @Override
//    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
//        return new CardArtRoller.ReskinInfo(ID, Color.BROWN, WHITE, Color.BROWN, WHITE, false);
//    }
//
//    @Override
//    public String cardArtCopy() {
//        return Miracle.ID;
//    }
//
//    @Override
//    public String itemArt() {
//        return "ExplosiveUni";
//    }
//
//    @Override
//    public int getValance() {
//        return magicNumber;
//    }
//
//    @Override
//    public AbstractCreationCard getCreation(int red, int blue, int yellow, int green) {
//        return new ExplosiveUni(new AbstractCreationCard.ElementData(red, blue, yellow, green));
//    }
//}