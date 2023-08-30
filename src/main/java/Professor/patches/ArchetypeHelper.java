package Professor.patches;

import Professor.powers.interfaces.CheatCostPower;
import Professor.powers.interfaces.OnCreateCardPower;
import Professor.powers.interfaces.OnCreateInfusionPower;
import Professor.util.matcher.*;
import basemod.Pair;
import basemod.abstracts.AbstractCardModifier;
import basemod.abstracts.CustomCard;
import basemod.helpers.CardModifierManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.BranchingUpgradesCard;
import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.MultiUpgradeCard;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.ExhaustiveField;
import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.FleetingField;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.Matcher;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.actions.utility.ExhaustAllEtherealAction;
import com.megacrit.cardcrawl.actions.watcher.ChangeStanceAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AccuracyPower;
import com.megacrit.cardcrawl.powers.BarricadePower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.watcher.FreeAttackPower;
import com.megacrit.cardcrawl.powers.watcher.MantraPower;
import com.megacrit.cardcrawl.powers.watcher.MasterRealityPower;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;
import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import javassist.expr.NewExpr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.BiPredicate;

public class ArchetypeHelper {
    private static final HashMap<CtClass, HashMap<Matcher[], Boolean>> performedCtClassChecks = new HashMap<>();
    private static final HashMap<Class<?>, HashMap<Matcher[], Boolean>> performedClassChecks = new HashMap<>();
    private static final HashMap<String, ArrayList<AbstractCard>> cachedUpgrades = new HashMap<>();
    // If we end up in these classes, we know we have gone too far and can stop method call recursion
    public static Class<?>[] bannedMethodChecks = {AbstractDungeon.class, GameActionManager.class, AbstractPlayer.class, AbstractCreature.class, AbstractMonster.class, SpriteBatch.class, FontHelper.class, AbstractCard.class, CustomCard.class};
    // These classes create false positives as they are accidentally included in basegame cards
    public static Class<?>[] bannedClassChecks = {ExhaustAllEtherealAction.class};
    // We want to check objects which extend these
    public static Class<?>[] importantSuperClasses = {AbstractGameAction.class, AbstractPower.class, AbstractOrb.class, AbstractDamageModifier.class, AbstractCardModifier.class};
    // Built from previous array when needed
    public static final ArrayList<CtClass> importantCtSuperClasses = new ArrayList<>();
    // Don't look at methods from classes that start with these
    public static final String[] bannedPaths = {"java", "basemod", "com.badlogic"};
    public static Matcher[] fireMatchers = {
            //Damage
            new Matcher.MethodCallMatcher(AbstractCreature.class, "damage"),
            new Matcher.MethodCallMatcher(AbstractMonster.class, "damage")
    };
    public static Matcher[] iceMatchers = {
            //Block
            new Matcher.MethodCallMatcher(AbstractCreature.class, "addBlock"),
            new Matcher.MethodCallMatcher(AbstractPlayer.class, "addBlock"),

            //Mitigation
            //Barricade is hardcoded
            new Matcher.NewExprMatcher(BarricadePower.class),
            new CompoundMatcher(new OverrideMatcher(AbstractPower.class, "atDamageReceive"), new PowerTypeMatcher(AbstractPower.PowerType.BUFF)),
            new CompoundMatcher(new OverrideMatcher(AbstractPower.class, "atDamageFinalReceive"), new PowerTypeMatcher(AbstractPower.PowerType.BUFF)),
            new CompoundMatcher(new OverrideMatcher(AbstractPower.class, "onAttackedToChangeDamage"), new PowerTypeMatcher(AbstractPower.PowerType.BUFF)),
            new CompoundMatcher(new OverrideMatcher(AbstractPower.class, "modifyBlock"), new PowerTypeMatcher(AbstractPower.PowerType.BUFF)),
            new CompoundMatcher(new OverrideMatcher(AbstractPower.class, "modifyBlockLast"), new PowerTypeMatcher(AbstractPower.PowerType.BUFF)),
            new CompoundMatcher(new OverrideMatcher(AbstractPower.class, "atDamageGive"), new PowerTypeMatcher(AbstractPower.PowerType.DEBUFF)),
            //Compounds cover basegame and get modded ones
            //new Matcher.NewExprMatcher(IntangiblePlayerPower.class),
            //new Matcher.NewExprMatcher(BufferPower.class),
            //new Matcher.NewExprMatcher(ArtifactPower.class),
            //new Matcher.NewExprMatcher(WeakPower.class),
            //new Matcher.NewExprMatcher(DexterityPower.class),
            new Matcher.FieldAccessMatcher(TempHPField.class, "tempHp"),

            //Orb Manip
            new Matcher.NewExprMatcher(FocusPower.class),
            new Matcher.MethodCallMatcher(AbstractOrb.class, "onStartOfTurn"),
            new Matcher.MethodCallMatcher(AbstractOrb.class, "onEndOfTurn"),
            new Matcher.MethodCallMatcher(AbstractPlayer.class, "evokeWithoutLosingOrb"),
            new Matcher.MethodCallMatcher(AbstractPlayer.class, "increaseMaxOrbSlots"),
            new Matcher.MethodCallMatcher(AbstractPlayer.class, "decreaseMaxOrbSlots"),
            new Matcher.MethodCallMatcher(AbstractPlayer.class, "evokeOrb"),
            new Matcher.MethodCallMatcher(AbstractPlayer.class, "removeNextOrb")
    };
    public static Matcher[] boltMatchers = {
            //Energy
            new Matcher.NewExprMatcher(FreeAttackPower.class),
            new Matcher.MethodCallMatcher(AbstractPlayer.class, "gainEnergy"),
            new Matcher.MethodCallMatcher(AbstractCard.class, "setCostForTurn"),
            new Matcher.MethodCallMatcher(AbstractCard.class, "modifyCostForCombat"),
            new Matcher.FieldAccessMatcher(AbstractCard.class, "costForTurn"),
            new Matcher.FieldAccessMatcher(AbstractCard.class, "isCostModified"),

            //Boosts
            new Matcher.NewExprMatcher(Lightning.class),
            new CompoundMatcher(new OverrideMatcher(AbstractPower.class, "atDamageReceive"), new PowerTypeMatcher(AbstractPower.PowerType.DEBUFF)),
            new CompoundMatcher(new OverrideMatcher(AbstractPower.class, "atDamageFinalReceive"), new PowerTypeMatcher(AbstractPower.PowerType.DEBUFF)),
            new CompoundMatcher(new OverrideMatcher(AbstractPower.class, "atDamageGive"), new PowerTypeMatcher(AbstractPower.PowerType.BUFF)),
            new CompoundMatcher(new OverrideMatcher(AbstractPower.class, "atDamageFinalGive"), new PowerTypeMatcher(AbstractPower.PowerType.BUFF)),
            //Compounds cover these basegame powers and get modded ones
            //new Matcher.NewExprMatcher(StrengthPower.class),
            //new Matcher.NewExprMatcher(VigorPower.class),
            //new Matcher.NewExprMatcher(VulnerablePower.class),
            //new Matcher.NewExprMatcher(DoubleDamagePower.class),
            //Accuracy hardcoded becasue oof
            new Matcher.NewExprMatcher(AccuracyPower.class),
            new Matcher.NewExprMatcher(MantraPower.class),
            new Matcher.NewExprMatcher(GainGoldAction.class),

            //Interface
            new InterfaceMatcher(CheatCostPower.class)
    };
    public static Matcher[] windMatchers = {
            //Card Manip
            new Matcher.MethodCallMatcher(AbstractPlayer.class, "draw"),
            new Matcher.MethodCallMatcher(CardGroup.class, "addToHand"),
            new Matcher.MethodCallMatcher(CardGroup.class, "moveToDiscardPile"),
            new Matcher.MethodCallMatcher(CardGroup.class, "moveToExhaustPile"),
            new Matcher.MethodCallMatcher(CardGroup.class, "moveToHand"),
            new Matcher.MethodCallMatcher(CardGroup.class, "moveToDeck"),
            new Matcher.MethodCallMatcher(CardGroup.class, "moveToBottomOfDeck"),
            new Matcher.MethodCallMatcher(CardGroup.class, "addToTop"),
            new Matcher.MethodCallMatcher(CardGroup.class, "addToBottom"),
            new Matcher.MethodCallMatcher(CardGroup.class, "addToRandomSpot"),

            //Card Creation
            new Matcher.NewExprMatcher(MasterRealityPower.class),
            new Matcher.NewExprMatcher(ShowCardAndAddToHandEffect.class),
            new Matcher.NewExprMatcher(ShowCardAndAddToDiscardEffect.class),
            new Matcher.NewExprMatcher(ShowCardAndAddToDrawPileEffect.class),
            new InterfaceMatcher(OnCreateCardPower.class),

            //Hand Size
            new Matcher.FieldAccessMatcher(AbstractPlayer.class, "gameHandSize"),

            //Stance Change
            new Matcher.NewExprMatcher(ChangeStanceAction.class),

            //Infusion Boost
            new InterfaceMatcher(OnCreateInfusionPower.class)
    };
    public static Matcher[] testMatcher = {

    };
    public static Matcher[] testMatcher2 = {

    };
    public static String[] orbCodes = {"[E]","[R]","[B]","[G]","[W]"};
    public static boolean debug = false;

    public static void prepTest() {
        performedClassChecks.clear();
        performedCtClassChecks.clear();
        debug = !debug;
    }

    public static boolean isFire(AbstractCard card) {
        //Damage, lol
        if (card.hasTag(CustomTags.PROF_FIRE)) {
            return true;
        }
        if (card.hasTag(CustomTags.PROF_NOT_FIRE)) {
            return false;
        }
        if (testCard(card, fireMatchers)) {
            return true;
        }
        return false;
    }

    public static boolean isIce(AbstractCard card) {
        //Block, Weak, Intangible, Temp HP
        if (card.hasTag(CustomTags.PROF_ICE)) {
            return true;
        }
        if (card.hasTag(CustomTags.PROF_NOT_ICE)) {
            return false;
        }
        if (cardCheck(card, (c,l) -> c.selfRetain || l.stream().anyMatch(u ->  u.selfRetain))) {
            return true;
        }
        if (hasKeyword(card, GameDictionary.UNPLAYABLE.NAMES)) {
            return true;
        }
        if (testCard(card, iceMatchers)) {
            return true;
        }
        if (hasKeyword(card, GameDictionary.LOCK_ON.NAMES)) {
            return true;
        }
        return false;
    }

    public static boolean isBolt(AbstractCard card) {
        //Energy, Exhaust, Vulnerable
        if (card.hasTag(CustomTags.PROF_BOLT)) {
            return true;
        }
        if (card.hasTag(CustomTags.PROF_NOT_BOLT)) {
            return false;
        }
        if (cardCheck(card, (c,l) -> c.exhaust || ExhaustiveField.ExhaustiveFields.baseExhaustive.get(c) > -1 || FleetingField.fleeting.get(c) || l.stream().anyMatch(u ->  u.exhaust || ExhaustiveField.ExhaustiveFields.baseExhaustive.get(u) > -1 || FleetingField.fleeting.get(u)))) {
            return true;
        }
        if (Arrays.stream(orbCodes).anyMatch(s -> CardModifierManager.onCreateDescription(card, card.rawDescription).contains(s))) {
            return true;
        }
        if (testCard(card, boltMatchers)) {
            return true;
        }
        /*if (debug) {
            if (testCard(card, testMatcher2)) {
                return true;
            }
        } else {
            if (testCard(card, testMatcher)) {
                return true;
            }
        }*/
        return false;
    }

    public static boolean isWind(AbstractCard card) {
        //Healing, Ethereal, Draw
        if (card.hasTag(CustomTags.PROF_WIND)) {
            return true;
        }
        if (card.hasTag(CustomTags.PROF_NOT_WIND)) {
            return false;
        }
        if (card.hasTag(AbstractCard.CardTags.HEALING)) {
            return true;
        }
        if (testCard(card, windMatchers)) {
            return true;
        }
        if (triggersOnDraw(card) || triggersOnDiscard(card)) {
            return true;
        }
        return false;
    }

    public static boolean hasKeyword(AbstractCard card, String[] keywords) {
        return Arrays.stream(keywords).anyMatch(s -> card.keywords.contains(s));
    }

    private static ArrayList<AbstractCard> getCardStates(AbstractCard card) {
        if (cachedUpgrades.containsKey(card.cardID)) {
            return cachedUpgrades.get(card.cardID);
        }
        ArrayList<AbstractCard> cardsToCheck = new ArrayList<>();
        //Grab an unmodified copy of the card.
        AbstractCard baseCheck = CardLibrary.getCard(card.cardID);
        if (baseCheck == null) {
            //If we don't have this in the library, use a blank copy.
            baseCheck = card.makeCopy();
        } else {
            //Make a copy to ensure we never modify the card library version.
            baseCheck = baseCheck.makeCopy();
        }
        cardsToCheck.add(baseCheck);
        if (card instanceof BranchingUpgradesCard) {
            //If this is branching upgrade card we need both the upgrade paths
            try {
                AbstractCard normalCheck = card.makeCopy();
                ((BranchingUpgradesCard) normalCheck).setUpgradeType(BranchingUpgradesCard.UpgradeType.NORMAL_UPGRADE);
                normalCheck.upgrade();
                cardsToCheck.add(normalCheck);
                AbstractCard branchCheck = card.makeCopy();
                ((BranchingUpgradesCard) branchCheck).setUpgradeType(BranchingUpgradesCard.UpgradeType.BRANCH_UPGRADE);
                branchCheck.upgrade();
                cardsToCheck.add(branchCheck);
            } catch (Exception ignored) {}
        } else if (card instanceof MultiUpgradeCard) {
            //Else if this is a multi upgrade card we need to check each individual upgrade.
            //We cant use the normal method of upgrading as upgrades can have dependencies, but we can force each upgrade index and test that way
            //Notably, we need to check the upgrades of the baseCheck in case something modified the amount of upgrades of the main card
            try {
                for (int i = 0 ; i < ((MultiUpgradeCard) baseCheck).getUpgrades().size() ; i++) {
                    AbstractCard upgradeTest = card.makeCopy();
                    ((MultiUpgradeCard)upgradeTest).getUpgrades().get(i).upgrade();
                    cardsToCheck.add(upgradeTest);
                }
            } catch (Exception ignored) {}
        } else {
            //Else its very simple
            try {
                AbstractCard upgradeCheck = card.makeCopy();
                upgradeCheck.upgrade();
                cardsToCheck.add(upgradeCheck);
            } catch (Exception ignored) {}
        }
        cachedUpgrades.put(card.cardID, cardsToCheck);
        return cardsToCheck;
    }

    public static boolean cardCheck(AbstractCard card, BiPredicate<AbstractCard, ArrayList<AbstractCard>> p) {
        boolean ret = false;
        try {
            ret = p.test(card, getCardStates(card));
        } catch (Exception ignored) {}
        return ret;
    }

    public static boolean overridesMethod(Object o, Class<?> clazz, String method, Class<?>... paramtypez) {
        try {
            return !o.getClass().getMethod(method, paramtypez).getDeclaringClass().equals(clazz);
        } catch (NoSuchMethodException ignored) {}
        return false;
    }

    public static boolean triggersOnDraw(AbstractCard card) {
        if (overridesMethod(card, AbstractCard.class, "triggerWhenDrawn")) {
            return true;
        }
        for (AbstractCardModifier mod : CardModifierManager.modifiers(card)) {
            if (overridesMethod(mod, AbstractCardModifier.class, "onDrawn", AbstractCard.class)) {
                return true;
            }
        }
        return false;
    }

    public static boolean triggersOnDiscard(AbstractCard card) {
        if (overridesMethod(card, AbstractCard.class, "triggerOnManualDiscard")) {
            return true;
        }
        return false;
    }

    public static ArrayList<Pair<CtClass, CtMethod>> methodStack = new ArrayList<>();
    public static boolean testCard(AbstractCard card, Matcher... matchers) {
        ArrayList<Class<?>> classes = new ArrayList<>();
        classes.add(card.getClass());
        for (AbstractCardModifier mod : CardModifierManager.modifiers(card)) {
            classes.add(mod.getClass());
        }
        for (AbstractDamageModifier dmod : DamageModifierManager.modifiers(card)) {
            classes.add(dmod.getClass());
        }
        ClassPool pool = Loader.getClassPool();
        if (importantCtSuperClasses.isEmpty()) {
            for (Class<?> clazz : importantSuperClasses) {
                try {
                    importantCtSuperClasses.add(pool.get(clazz.getName()));
                } catch (NotFoundException ignored) {}
            }
        }
        for (Class<?> clazz : classes) {
            if (!performedClassChecks.containsKey(clazz)) {
                performedClassChecks.put(clazz, new HashMap<>());
            }
            if (performedClassChecks.get(clazz).containsKey(matchers)) {
                if (performedClassChecks.get(clazz).get(matchers)) {
                    return true;
                }
            } else {
                try {
                    CtClass ctClass = pool.getCtClass(clazz.getName());
                    methodStack.clear();
                    if (classTester(ctClass, matchers)) {
                        performedClassChecks.get(clazz).put(matchers, true);
                        return true;
                    }
                } catch (NotFoundException ignored) {}
                performedClassChecks.get(clazz).put(matchers, false);
            }
        }
        return false;
    }

    public static boolean classTester(CtClass ctClass, Matcher... matchers) {
        if (!performedCtClassChecks.containsKey(ctClass)) {
            performedCtClassChecks.put(ctClass, new HashMap<>());
        }
        if (performedCtClassChecks.get(ctClass).containsKey(matchers)) {
            return performedCtClassChecks.get(ctClass).get(matchers);
        }
        ArrayList<CtMethod> foundMethods = methodFinder(ctClass);
        for (CtMethod currentMethod : foundMethods) {
            if (methodTester(currentMethod, matchers)) {
                performedCtClassChecks.get(ctClass).put(matchers, true);
                return true;
            }
        }
        performedCtClassChecks.get(ctClass).put(matchers, false);
        return false;
    }

    public static ArrayList<CtMethod> methodFinder(CtClass ctClass) {
        ctClass.defrost();
        ArrayList<CtMethod> foundMethods = new ArrayList<>(Arrays.asList(ctClass.getDeclaredMethods()));
        if (importantCtSuperClasses.stream().anyMatch(ctc -> ctClass.subclassOf(ctc) && !ctc.getClassFile2().getName().equals(AbstractOrb.class.getName()))) {
            CtClass currentCtClass = ctClass;
            while (true) {
                try {
                    CtClass superCtClass = currentCtClass.getSuperclass();
                    for (CtMethod m : superCtClass.getDeclaredMethods()) {
                        if (Arrays.stream(bannedMethodChecks).noneMatch(clazz -> m.getDeclaringClass().getName().equals(clazz.getName()))) {
                            if (Arrays.stream(bannedPaths).noneMatch(s -> m.getDeclaringClass().getName().startsWith(s))) {
                                foundMethods.add(m);
                            }
                        }
                    }
                    currentCtClass = superCtClass;
                } catch (Exception ignored) {
                    break;
                }
            }
        }
        return foundMethods;
    }

    public static boolean methodTester(CtMethod check, Matcher... matchers) {
        Pair<CtClass, CtMethod> key = new Pair<>(check.getDeclaringClass(), check);
        if (runTest(check, matchers)) {
            return true;
        }
        if (!methodStack.contains(key)) {
            methodStack.add(key);
            for (CtMethod next : getReferencedMethods(check)) {
                if (methodTester(next, matchers)) {
                    return true;
                }
            }
            for (CtClass next : getReferencedClasses(check)) {
                if (classTester(next, matchers)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean runTest(CtMethod ctMethodToPatch, Matcher... matchers) {
        for (Matcher matcher : matchers) {
            if (matcher instanceof CompoundMatcher) {
                ((CompoundMatcher) matcher).reset();
            }
            BasicMatchChecker editor = new BasicMatchChecker(matcher);
            try {
                ctMethodToPatch.getDeclaringClass().defrost();
                ctMethodToPatch.instrument(editor);// 24
                if (editor.didMatch()) {
                    return true;
                }
            } catch (CannotCompileException | RuntimeException ignored) {}
        }
        return false;
    }

    public static ArrayList<CtMethod> getReferencedMethods(CtMethod method) {
        ArrayList<CtMethod> foundMethods = new ArrayList<>();
        try {
            method.getDeclaringClass().defrost();
            method.instrument(new ExprEditor() {
                @Override
                public void edit(MethodCall m) {
                    try {
                        CtMethod newMethod = m.getMethod();
                        if (Arrays.stream(bannedMethodChecks).noneMatch(clazz -> newMethod.getDeclaringClass().getName().equals(clazz.getName()))) {
                            if (Arrays.stream(bannedPaths).noneMatch(s -> newMethod.getDeclaringClass().getName().startsWith(s))) {
                                foundMethods.add(newMethod);
                            }
                        }
                    } catch (NotFoundException | RuntimeException ignored) {}
                }
            });
        } catch (CannotCompileException ignored) {}
        return foundMethods;
    }

    public static ArrayList<CtClass> getReferencedClasses(CtMethod method) {
        ArrayList<CtClass> foundClasses = new ArrayList<>();
        try {
            method.getDeclaringClass().defrost();
            method.instrument(new ExprEditor() {
                @Override
                public void edit(NewExpr e) {
                    try {
                        CtClass ctExprClass = e.getConstructor().getDeclaringClass();
                        if (Arrays.stream(bannedClassChecks).noneMatch(clazz -> ctExprClass.getClassFile2().getName().equals(clazz.getName()))) {
                            if (importantCtSuperClasses.stream().anyMatch(ctExprClass::subclassOf)) {
                                foundClasses.add(ctExprClass);
                            }
                        }
                    } catch (NotFoundException | RuntimeException ignored) {}
                }
            });
        } catch (CannotCompileException ignored) {}
        return foundClasses;
    }
}
