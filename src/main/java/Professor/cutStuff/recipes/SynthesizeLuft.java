//package Professor.cutStuff.recipes;
//
//import Professor.actions.BeginSynthesisAction;
//import Professor.cards.abstracts.AbstractCreationCard;
//import Professor.cards.abstracts.AbstractRecipeCard;
//import Professor.cutStuff.creations.Luft;
//import Professor.util.CardArtRoller;
//import com.megacrit.cardcrawl.cards.tempCards.Miracle;
//import com.megacrit.cardcrawl.characters.AbstractPlayer;
//import com.megacrit.cardcrawl.monsters.AbstractMonster;
//
//import static Professor.MainModfile.makeID;
//
//public class SynthesizeLuft extends AbstractRecipeCard {
//    public final static String ID = makeID(SynthesizeLuft.class.getSimpleName());
//
//    public SynthesizeLuft() {
//        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
//        baseMagicNumber = magicNumber = 2;
//        cardsToPreview = new Luft();
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
//        return new CardArtRoller.ReskinInfo(ID, SPRING_GREEN, WHITE, SPRING_GREEN, WHITE, false);
//    }
//
//    @Override
//    public String cardArtCopy() {
//        return Miracle.ID;
//    }
//
//    @Override
//    public String itemArt() {
//        return "Luft";
//    }
//
//    @Override
//    public int getValance() {
//        return magicNumber;
//    }
//
//    @Override
//    public AbstractCreationCard getCreation(int red, int blue, int yellow, int green) {
//        return new Luft(new AbstractCreationCard.ElementData(red, blue, yellow, green));
//    }
//}