/*
package Professor.patches;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.ModInfo;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpireRawPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.FieldAccess;
import org.clapper.util.classutil.*;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class MultiTypePatches {
    private static AbstractCard cardRef;
    private static AbstractCard.CardType typeRef;
    public static AbstractCard.CardType cardHack(AbstractCard card, AbstractCard.CardType originalType) {
        cardRef = card;
        if (typeRef != null) {
            if (cardRef.type != typeRef) {
                if (typeRef == AbstractCard.CardType.ATTACK && CardModifierManager.getExtraDescriptors(cardRef).contains(AbstractCard.TEXT[0])) {
                    originalType = cardRef.type;
                }
            }
            typeRef = null;
            cardRef = null;
        }
        return originalType;
    }
    public static AbstractCard.CardType typeHack(AbstractCard.CardType originalType) {
        typeRef = originalType;
        if (cardRef != null) {
            if (cardRef.type != typeRef) {
                if (typeRef == AbstractCard.CardType.ATTACK && CardModifierManager.getExtraDescriptors(cardRef).contains(AbstractCard.TEXT[0])) {
                    originalType = cardRef.type;
                }
            }
            typeRef = null;
            cardRef = null;
        }
        return originalType;
    }

    @SpirePatch2(clz = CardCrawlGame.class, method = SpirePatch.CONSTRUCTOR)
    public static class MultiTypeFix {
        @SpireRawPatch
        public static void patch(CtBehavior ctBehavior) throws NotFoundException {
            ClassFinder finder = new ClassFinder();
            finder.add(new File(Loader.STS_JAR));

            for (ModInfo modInfo : Loader.MODINFOS) {
                if (modInfo.jarURL != null) {
                    try {
                        finder.add(new File(modInfo.jarURL.toURI()));
                    } catch (URISyntaxException ignored) {
                    }
                }
            }

            ClassFilter filter = new AndClassFilter(
                    new NotClassFilter(new InterfaceOnlyClassFilter()),
                    new ClassModifiersClassFilter(Modifier.PUBLIC),
                    new OrClassFilter(
                            new SubclassClassFilter(AbstractCard.class),
                            new SubclassClassFilter(AbstractPower.class),
                            new SubclassClassFilter(AbstractRelic.class)
                    )
            );

            ArrayList<ClassInfo> foundClasses = new ArrayList<>();
            finder.findClasses(foundClasses, filter);

            for (ClassInfo classInfo : foundClasses) {
                CtClass ctClass = ctBehavior.getDeclaringClass().getClassPool().get(classInfo.getClassName());
                for (CtMethod ctm : ctClass.getMethods()) {
                    try {
                        ctm.instrument(new ExprEditor() {
                            @Override
                            public void edit(FieldAccess f) throws CannotCompileException {
                                String className = f.getClassName();
                                if (className.equals(AbstractCard.class.getName()) && f.getFieldName().equals("type") && f.isReader()) {
                                    f.replace("$_ = Professor.patches.MultiTypePatches.cardHack($0, $proceed());");
                                } else if (className.equals(AbstractCard.CardType.class.getName()) && f.getFieldName().equals("ATTACK") && f.isReader()) {
                                    f.replace("$_ = Professor.patches.MultiTypePatches.typeHack($proceed());");
                                }
                            }
                        });
                    } catch (CannotCompileException ignored) {}
                }
            }
        }
    }
}
*/
