//package Professor.cutStuff.recipes;
//
//import Professor.actions.BeginSynthesisAction;
//import Professor.cards.abstracts.AbstractCreationCard;
//import Professor.cards.abstracts.AbstractRecipeCard;
//import Professor.cutStuff.creations.CrystalIceBomb;
//import Professor.util.CardArtRoller;
//import com.megacrit.cardcrawl.cards.tempCards.Miracle;
//import com.megacrit.cardcrawl.characters.AbstractPlayer;
//import com.megacrit.cardcrawl.monsters.AbstractMonster;
//
//import static Professor.MainModfile.makeID;
//
//public class SynthesizeCrystalIceBomb extends AbstractRecipeCard {
//    public final static String ID = makeID(SynthesizeCrystalIceBomb.class.getSimpleName());
//
//    public SynthesizeCrystalIceBomb() {
//        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
//        baseMagicNumber = magicNumber = 2;
//        cardsToPreview = new CrystalIceBomb();
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
//        return new CardArtRoller.ReskinInfo(ID, CYAN, WHITE, CYAN, WHITE, false);
//    }
//
//    @Override
//    public String cardArtCopy() {
//        return Miracle.ID;
//    }
//
//    @Override
//    public String itemArt() {
//        return "CrystalIceBomb";
//    }
//
//    @Override
//    public int getValance() {
//        return magicNumber;
//    }
//
//    @Override
//    public AbstractCreationCard getCreation(int red, int blue, int yellow, int green) {
//        return new CrystalIceBomb(new AbstractCreationCard.ElementData(red, blue, yellow, green));
//    }
//}