//package Professor.cutStuff.recipes;
//
//import Professor.actions.BeginSynthesisAction;
//import Professor.cards.abstracts.AbstractCreationCard;
//import Professor.cards.abstracts.AbstractRecipeCard;
//import Professor.cutStuff.creations.DawnGrimoire;
//import Professor.util.CardArtRoller;
//import com.megacrit.cardcrawl.cards.tempCards.Miracle;
//import com.megacrit.cardcrawl.characters.AbstractPlayer;
//import com.megacrit.cardcrawl.monsters.AbstractMonster;
//
//import static Professor.MainModfile.makeID;
//
//public class SynthesizeDawnGrimoire extends AbstractRecipeCard {
//    public final static String ID = makeID(SynthesizeDawnGrimoire.class.getSimpleName());
//
//    public SynthesizeDawnGrimoire() {
//        super(ID, 1, CardType.SKILL, CardRarity.RARE, CardTarget.SELF);
//        baseMagicNumber = magicNumber = 3;
//        cardsToPreview = new DawnGrimoire();
//        tags.add(CardTags.HEALING);
//    }
//
//    @Override
//    public void use(AbstractPlayer p, AbstractMonster m) {
//        addToBot(new BeginSynthesisAction(this));
//    }
//
//    @Override
//    public void upp() {
//        upgradeBaseCost(0);
//    }
//
//    @Override
//    public CardArtRoller.ReskinInfo reskinInfo(String ID) {
//        return new CardArtRoller.ReskinInfo(ID, RED, WHITE, RED, BLACK, false);
//    }
//
//    @Override
//    public String cardArtCopy() {
//        return Miracle.ID;
//    }
//
//    @Override
//    public String itemArt() {
//        return "DawnGrimoire";
//    }
//
//    @Override
//    public int getValance() {
//        return magicNumber;
//    }
//
//    @Override
//    public AbstractCreationCard getCreation(int red, int blue, int yellow, int green) {
//        return new DawnGrimoire(new AbstractCreationCard.ElementData(red, blue, yellow, green));
//    }
//}