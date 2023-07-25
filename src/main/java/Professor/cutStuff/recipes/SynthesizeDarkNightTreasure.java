//package Professor.cutStuff.recipes;
//
//import Professor.actions.BeginSynthesisAction;
//import Professor.cards.abstracts.AbstractCreationCard;
//import Professor.cards.abstracts.AbstractRecipeCard;
//import Professor.cutStuff.creations.DarkNightTreasure;
//import Professor.util.CardArtRoller;
//import com.badlogic.gdx.graphics.Color;
//import com.megacrit.cardcrawl.cards.tempCards.Miracle;
//import com.megacrit.cardcrawl.characters.AbstractPlayer;
//import com.megacrit.cardcrawl.monsters.AbstractMonster;
//
//import static Professor.MainModfile.makeID;
//
//public class SynthesizeDarkNightTreasure extends AbstractRecipeCard {
//    public final static String ID = makeID(SynthesizeDarkNightTreasure.class.getSimpleName());
//
//    public SynthesizeDarkNightTreasure() {
//        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
//        baseMagicNumber = magicNumber = 2;
//        cardsToPreview = new DarkNightTreasure();
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
//        return new CardArtRoller.ReskinInfo(ID, mix(Color.PURPLE, Color.GRAY), WHITE, mix(Color.PURPLE, Color.GRAY), WHITE, false);
//    }
//
//    @Override
//    public String cardArtCopy() {
//        return Miracle.ID;
//    }
//
//    @Override
//    public String itemArt() {
//        return "DarkNightTreasure";
//    }
//
//    @Override
//    public int getValance() {
//        return magicNumber;
//    }
//
//    @Override
//    public AbstractCreationCard getCreation(int red, int blue, int yellow, int green) {
//        return new DarkNightTreasure(new AbstractCreationCard.ElementData(red, blue, yellow, green));
//    }
//}