package Professor;

import Professor.cardmods.AbstractInfusion;
import Professor.cardmods.DealDamageMod;
import Professor.cardmods.GainBlockMod;
import Professor.cards.AncientUni;
import Professor.cards.cardvars.*;
import Professor.cards.interfaces.GlowAdjacentCard;
import Professor.icons.IconContainer;
import Professor.patches.ArchetypeHelper;
import Professor.patches.GlowChangePatch;
import Professor.powers.BracedPower;
import Professor.powers.FocusedPower;
import Professor.powers.StaggerPower;
import Professor.powers.UnstablePower;
import Professor.powers.interfaces.InfusionTriggerPower;
import Professor.powers.interfaces.OnUpgradePower;
import Professor.relics.AbstractEasyRelic;
import Professor.relics.LocketOfDevotion;
import Professor.relics.MemoriaBracelet;
import Professor.ui.ModCardRenderer;
import Professor.ui.SynthesisPanel;
import Professor.util.*;
import Professor.vfx.ShaderTest;
import basemod.*;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardBorderGlowManager;
import basemod.helpers.CardModifierManager;
import basemod.helpers.RelicType;
import basemod.helpers.ScreenPostProcessorManager;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.mod.stslib.icons.CustomIconHelper;
import com.evacipated.cardcrawl.modthespire.Loader;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Acrobatics;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiCond;
import imgui.type.ImBoolean;
import imgui.type.ImFloat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Properties;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class MainModfile implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber, PostInitializeSubscriber, PostUpdateSubscriber, AddAudioSubscriber, OnPlayerTurnStartSubscriber, StartGameSubscriber, ImGuiSubscriber {

    public static final String modID = "Professor";
    public static final Logger logger = LogManager.getLogger(MainModfile.class.getName());

    public static String makeID(String idText) {
        return modID + ":" + idText;
    }

    public static final Color MEDIUM_RUBY_COLOR = CardHelper.getColor(160, 62, 98);

    public static final String SHOULDER1 = modID + "Resources/images/char/mainChar/shoulder2.png";
    public static final String SHOULDER2 = modID + "Resources/images/char/mainChar/shoulder.png";
    public static final String CORPSE = modID + "Resources/images/char/mainChar/corpse.png";
    public static final String ATTACK_S_ART = modID + "Resources/images/512/bg_attack_red.png";
    public static final String SKILL_S_ART = modID + "Resources/images/512/bg_skill_red.png";
    public static final String POWER_S_ART = modID + "Resources/images/512/bg_power_red.png";
    public static final String ATTACK_L_ART = modID + "Resources/images/1024/bg_attack_red.png";
    public static final String SKILL_L_ART = modID + "Resources/images/1024/bg_skill_red.png";
    public static final String POWER_L_ART = modID + "Resources/images/1024/bg_power_red.png";
    public static final String ATTACK_S_ART_BLUE = modID + "Resources/images/512/bg_attack_blue.png";
    public static final String SKILL_S_ART_BLUE = modID + "Resources/images/512/bg_skill_blue.png";
    public static final String POWER_S_ART_BLUE = modID + "Resources/images/512/bg_power_blue.png";
    public static final String ATTACK_L_ART_BLUE = modID + "Resources/images/1024/bg_attack_blue.png";
    public static final String SKILL_L_ART_BLUE = modID + "Resources/images/1024/bg_skill_blue.png";
    public static final String POWER_L_ART_BLUE = modID + "Resources/images/1024/bg_power_blue.png";
    public static final String ATTACK_S_ART_PURPLE = modID + "Resources/images/512/bg_attack_purple.png";
    public static final String SKILL_S_ART_PURPLE = modID + "Resources/images/512/bg_skill_purple.png";
    public static final String POWER_S_ART_PURPLE = modID + "Resources/images/512/bg_power_purple.png";
    public static final String ATTACK_L_ART_PURPLE = modID + "Resources/images/1024/bg_attack_purple.png";
    public static final String SKILL_L_ART_PURPLE = modID + "Resources/images/1024/bg_skill_purple.png";
    public static final String POWER_L_ART_PURPLE = modID + "Resources/images/1024/bg_power_purple.png";
    private static final String CARD_ENERGY_S = modID + "Resources/images/512/energy.png";
    private static final String TEXT_ENERGY = modID + "Resources/images/512/text_energy.png";
    private static final String CARD_ENERGY_L = modID + "Resources/images/1024/energy.png";
    private static final String CHARSELECT_BUTTON = modID + "Resources/images/charSelect/charButton.png";
    private static final String CHARSELECT_PORTRAIT = modID + "Resources/images/charSelect/charBG.png";

    public static final String BADGE_IMAGE = modID + "Resources/images/Badge.png";

    public static UIStrings uiStrings;
    public static String[] TEXT;
    public static String[] EXTRA_TEXT;
    private static final String AUTHOR = "Mistress Alison";

    // Mod-settings settings. This is if you want an on/off savable button
    public static SpireConfig professorConfig;

    public static final String RENDER_ELEMENTS_OFF_CHARACTER = "renderElementsOffCharacter";
    public static boolean renderElementsOffCharacter = false;

    public static final String ELEMENT_ICON_SIZE = "elementIconSize";
    public static float elementIconSize = 1f;

    public static final String ENABLE_UNSTABLE_SHADER = "enableUnstableShader";
    public static boolean enableUnstableShader = true;

    public static final String CHEAT_MATCH_AND_KEEP = "cheatMatchAndKeep";
    public static boolean cheatMatchAndKeep = false;

    public static final String ENABLE_CARD_BATTLE_TALK_SETTING = "enableCardBattleTalk";
    public static boolean enableCardBattleTalkEffect = false;

    public static final String CARD_BATTLE_TALK_PROBABILITY_SETTING = "cardTalkProbability";
    public static int cardTalkProbability = 10; //Out of 100

    public static final String ENABLE_DAMAGED_BATTLE_TALK_SETTING = "enableDamagedBattleTalk";
    public static boolean enableDamagedBattleTalkEffect = false;

    public static final String DAMAGED_BATTLE_TALK_PROBABILITY_SETTING = "damagedTalkProbability";
    public static int damagedTalkProbability = 20; //Out of 100

    public static final String ENABLE_PRE_BATTLE_TALK_SETTING = "enablePreBattleTalk";
    public static boolean enablePreBattleTalkEffect = false;

    public static final String PRE_BATTLE_TALK_PROBABILITY_SETTING = "preTalkProbability";
    public static int preTalkProbability = 50; //Out of 100

    public MainModfile() {
        BaseMod.subscribe(this);

        BaseMod.addColor(TheProfessor.Enums.MEDIUM_RUBY_COLOR, MEDIUM_RUBY_COLOR, MEDIUM_RUBY_COLOR, MEDIUM_RUBY_COLOR,
                MEDIUM_RUBY_COLOR, MEDIUM_RUBY_COLOR, MEDIUM_RUBY_COLOR, MEDIUM_RUBY_COLOR,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);

        Properties professorDefaultSettings = new Properties();
        professorDefaultSettings.setProperty(ENABLE_UNSTABLE_SHADER, Boolean.toString(enableUnstableShader));
        professorDefaultSettings.setProperty(RENDER_ELEMENTS_OFF_CHARACTER, Boolean.toString(renderElementsOffCharacter));
        professorDefaultSettings.setProperty(ELEMENT_ICON_SIZE, Float.toString(elementIconSize));
        professorDefaultSettings.setProperty(CHEAT_MATCH_AND_KEEP, Boolean.toString(cheatMatchAndKeep));
        try {
            professorConfig = new SpireConfig("ProfessorMod", "ProfessorModConfig", professorDefaultSettings);
            enableUnstableShader = professorConfig.getBool(ENABLE_UNSTABLE_SHADER);
            renderElementsOffCharacter = professorConfig.getBool(RENDER_ELEMENTS_OFF_CHARACTER);
            elementIconSize = professorConfig.getFloat(ELEMENT_ICON_SIZE);
            cheatMatchAndKeep = professorConfig.getBool(CHEAT_MATCH_AND_KEEP);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String makePath(String resourcePath) {
        return modID + "Resources/" + resourcePath;
    }

    public static String makeImagePath(String resourcePath) {
        return modID + "Resources/images/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return modID + "Resources/images/relics/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return modID + "Resources/images/powers/" + resourcePath;
    }

    public static String makeCardPath(String resourcePath) {
        return modID + "Resources/images/cards/" + resourcePath;
    }

    public static void initialize() {
        MainModfile thismod = new MainModfile();
    }

    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new TheProfessor(TheProfessor.characterStrings.NAMES[1], TheProfessor.Enums.THE_PROFESSOR),
                CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, TheProfessor.Enums.THE_PROFESSOR);
        PotionLoader.loadContent();
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(modID)
                .packageFilter(AbstractEasyRelic.class)
                .any(AbstractEasyRelic.class, (info, relic) -> {
                    if (relic.color == null) {
                        BaseMod.addRelic(relic, RelicType.SHARED);
                    } else {
                        BaseMod.addRelicToCustomPool(relic, relic.color);
                    }
                    if (!info.seen) {
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                    }
                });
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new Info());
        BaseMod.addDynamicVariable(new SecondMagicNumber());
        BaseMod.addDynamicVariable(new SecondDamage());
        BaseMod.addDynamicVariable(new SecondBlock());
        BaseMod.addDynamicVariable(new DynvarInterfaceManager());

        CustomIconHelper.addCustomIcon(IconContainer.FireIcon.get());
        CustomIconHelper.addCustomIcon(IconContainer.IceIcon.get());
        CustomIconHelper.addCustomIcon(IconContainer.BoltIcon.get());
        CustomIconHelper.addCustomIcon(IconContainer.WindIcon.get());

        new AutoAdd(modID)
                .packageFilter(modID+".cards")
                .setDefaultSeen(true)
                .cards();
    }

    private void loadLangKeywords(String language) {
        Gson gson = new Gson();
        Keyword[] keywords = null;
        try {
            String json = Gdx.files.internal(modID + "Resources/localization/eng/Keywordstrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
            keywords = gson.fromJson(json, Keyword[].class);
        } catch (GdxRuntimeException e) {
            if (!e.getMessage().startsWith("File not found:")) {
                throw e;
            }
        }

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(modID.toLowerCase(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                if(keyword.NAMES.length > 0 && !keyword.ID.isEmpty()) {
                    KeywordManager.setupKeyword(keyword.ID, keyword.NAMES[0]);
                }
            }
        }
    }

    @Override
    public void receiveEditKeywords() {
        String language = Settings.language.name().toLowerCase();
        loadLangKeywords("eng");
        if (!language.equals("eng")) {
            loadLangKeywords(language);
        }
    }

    private void loadLangStrings(String language) {
        String path = modID+"Resources/localization/" + language + "/";

        tryLoadStringsFile(CardStrings.class,path + "Cardstrings.json");
        tryLoadStringsFile(RelicStrings.class, path + "Relicstrings.json");
        tryLoadStringsFile(CharacterStrings.class, path + "Charstrings.json");
        tryLoadStringsFile(PowerStrings.class, path + "Powerstrings.json");
        tryLoadStringsFile(CardStrings.class, path + "CardModstrings.json");
        tryLoadStringsFile(CardStrings.class, path + "Chatterstrings.json");
        tryLoadStringsFile(UIStrings.class, path + "UIstrings.json");
        tryLoadStringsFile(PotionStrings.class, path +"Potionstrings.json");
        tryLoadStringsFile(UIStrings.class, path + "CardAugmentstrings.json");
    }

    private void tryLoadStringsFile(Class<?> stringType, String filepath) {
        try {
            BaseMod.loadCustomStringsFile(stringType, filepath);
        } catch (GdxRuntimeException e) {
            // Ignore file not found
            if (!e.getMessage().startsWith("File not found:")) {
                throw e;
            }
        }
    }

    @Override
    public void receiveEditStrings() {
        String language = Settings.language.name().toLowerCase();
        loadLangStrings("eng");
        if (!language.equals("eng")) {
            loadLangStrings(language);
        }
    }

    @Override
    public void receivePostInitialize() {
        uiStrings = CardCrawlGame.languagePack.getUIString(makeID("ModConfigs"));
        EXTRA_TEXT = uiStrings.EXTRA_TEXT;
        TEXT = uiStrings.TEXT;
        // Create the Mod Menu
        ModPanel settingsPanel = new ModPanel();

        // Load the Mod Badge
        Texture badgeTexture = TexLoader.getTexture(BADGE_IMAGE);
        BaseMod.registerModBadge(badgeTexture, EXTRA_TEXT[0], AUTHOR, EXTRA_TEXT[1], settingsPanel);

        //Add Powers
        BaseMod.addPower(FocusedPower.class, FocusedPower.POWER_ID);
        BaseMod.addPower(UnstablePower.class, UnstablePower.POWER_ID);
        BaseMod.addPower(BracedPower.class, BracedPower.POWER_ID);
        BaseMod.addPower(StaggerPower.class, StaggerPower.POWER_ID);

        //Wide Potions
        if (Loader.isModLoaded("widepotions")) {
            WidePotionLoader.loadCrossoverContent();
        }

        //Chimera Cards
        if (Loader.isModLoaded("CardAugments")) {
            ChimeraHelper.registerAugments();
            ChimeraHelper.applyBans();
        }

        //Add Config stuff
        float currentYposition = 740f;
        float sliderOffset = 50.0F + FontHelper.getWidth(FontHelper.charDescFont, TEXT[1], 1.0F / Settings.scale);
        float spacingY = 55f;

        //IUIElements
        ModLabeledToggleButton enableUnstableShaderButton = new ModLabeledToggleButton(TEXT[2],360.0f, currentYposition - 10f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                professorConfig.getBool(ENABLE_UNSTABLE_SHADER), settingsPanel, (label) -> {}, (button) -> {
            professorConfig.setBool(ENABLE_UNSTABLE_SHADER, button.enabled);
            enableUnstableShader = button.enabled;
            try {professorConfig.save();} catch (IOException e) {e.printStackTrace();}
        });
        currentYposition -= spacingY;

        ModLabeledToggleButton renderOffCharacterButton = new ModLabeledToggleButton(TEXT[0],360.0f, currentYposition - 10f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                professorConfig.getBool(RENDER_ELEMENTS_OFF_CHARACTER), settingsPanel, (label) -> {}, (button) -> {
            professorConfig.setBool(RENDER_ELEMENTS_OFF_CHARACTER, button.enabled);
            renderElementsOffCharacter = button.enabled;
            try {professorConfig.save();} catch (IOException e) {e.printStackTrace();}
        });
        currentYposition -= spacingY;

        ModLabel iconSizeLabel = new ModLabel(TEXT[1], 400.0F, currentYposition, Settings.CREAM_COLOR, FontHelper.charDescFont, settingsPanel, modLabel -> {});
        ModMinMaxSlider IconSizeSlider = new ModMinMaxSlider("", 400.0F + sliderOffset, currentYposition + 7.0F, 0.5F, 1.0F, professorConfig.getFloat(ELEMENT_ICON_SIZE), "%.2f", settingsPanel, slider -> {
            professorConfig.setString(ELEMENT_ICON_SIZE, Float.toString(slider.getValue()));
            elementIconSize = slider.getValue();
            try {professorConfig.save();} catch (IOException e) {e.printStackTrace();}
        });
        currentYposition -= spacingY;

        ModCardRenderer exampleCard1 = new ModCardRenderer(new AncientUni(), 550f, 400f, 0.7f);
        ModCardRenderer exampleCard2 = new ModCardRenderer(new Acrobatics(), 850f, 400f, 0.7f);

        settingsPanel.addUIElement(renderOffCharacterButton);
        settingsPanel.addUIElement(iconSizeLabel);
        settingsPanel.addUIElement(IconSizeSlider);
        settingsPanel.addUIElement(enableUnstableShaderButton);
        settingsPanel.addUIElement(exampleCard1);
        settingsPanel.addUIElement(exampleCard2);

        //Other Setup stuff
        processElements();
        CardBorderGlowManager.addGlowInfo(new CardBorderGlowManager.GlowInfo() {
            private final Color c = Color.RED.cpy();
            @Override
            public boolean test(AbstractCard card) {
                if (Wiz.adp().hoveredCard instanceof GlowAdjacentCard && ((GlowAdjacentCard) Wiz.adp().hoveredCard).glowAdjacent(card)) {
                    if (GlowChangePatch.GlowCheckField.lastChecked.get(card) != Wiz.adp().hoveredCard) {
                        GlowChangePatch.GlowCheckField.lastChecked.set(card, Wiz.adp().hoveredCard);
                        if (((GlowAdjacentCard) Wiz.adp().hoveredCard).flashAdjacent(card)) {
                            card.superFlash(((GlowAdjacentCard) Wiz.adp().hoveredCard).getGlowColor(card));
                        }
                    }
                    return true;
                }
                GlowChangePatch.GlowCheckField.lastChecked.set(card, Wiz.adp().hoveredCard);
                return false;
            }

            @Override
            public Color getColor(AbstractCard card) {
                if (Wiz.adp().hoveredCard instanceof GlowAdjacentCard) {
                    return ((GlowAdjacentCard) Wiz.adp().hoveredCard).getGlowColor(card);
                }
                return c;
            }

            @Override
            public String glowID() {
                return makeID("AdjacentGlow");
            }
        });

        if (shaderActive.get()) {
            ScreenPostProcessor postProcessor = new ShaderTest();
            ScreenPostProcessorManager.addPostProcessor(postProcessor);
        }
    }

    public static boolean runTest = false;
    public static boolean colorLog = true;
    public static boolean crash = false;

    private static void processElements() {
        logger.info("Performing elemental analysis:");
        ArrayList<AbstractCard> fireCards = new ArrayList<>();
        ArrayList<AbstractCard> iceCards = new ArrayList<>();
        ArrayList<AbstractCard> boltCards = new ArrayList<>();
        ArrayList<AbstractCard> windCards = new ArrayList<>();
        ArrayList<AbstractCard> elementlessCards = new ArrayList<>();
        HashMap<AbstractCard.CardColor, Integer> seenColors = new HashMap<>();
        int cardsSeen = 0;
        long time = -System.currentTimeMillis();
        for (String s : CardLibrary.cards.keySet()) {
            AbstractCard c = CardLibrary.cards.get(s);
            seenColors.put(c.color, seenColors.getOrDefault(c.color, 0)+1);
            boolean hasElement = false;
            if (ArchetypeHelper.isFire(c)) {
                fireCards.add(c);
                hasElement = true;
            }
            if (ArchetypeHelper.isIce(c)) {
                iceCards.add(c);
                hasElement = true;
            }
            if (ArchetypeHelper.isBolt(c)) {
                boltCards.add(c);
                hasElement = true;
            }
            if (ArchetypeHelper.isWind(c)) {
                windCards.add(c);
                hasElement = true;
            }
            if (!hasElement) {
                elementlessCards.add(c);
            }
            cardsSeen++;
        }
        time += System.currentTimeMillis();
        if (crash) {
            throw new RuntimeException("Processing Time: "+time/1000f+"s");
        }
        fireCards.sort(Comparator.comparing(o -> o.name));
        iceCards.sort(Comparator.comparing(o -> o.name));
        boltCards.sort(Comparator.comparing(o -> o.name));
        windCards.sort(Comparator.comparing(o -> o.name));
        elementlessCards.sort(Comparator.comparing(o -> o.name));
        logger.info("Global Card Data");
        logger.info("Processing Time: "+time/1000f+"s");
        logger.info("Fire cards: "+fireCards.size()+"/"+cardsSeen);
        logger.info("Ice cards: "+iceCards.size()+"/"+cardsSeen);
        logger.info("Bolt cards: "+boltCards.size()+"/"+cardsSeen);
        logger.info("Wind cards: "+windCards.size()+"/"+cardsSeen);
        logger.info("Elementless cards: "+elementlessCards.size()+"/"+cardsSeen);
        if (colorLog) {
            for (AbstractCard.CardColor c : seenColors.keySet()) {
                logger.info("");
                logger.info("Card Color "+c+" Data");
                logger.info("Fire cards: "+fireCards.stream().filter(card -> card.color == c).count()+"/"+seenColors.get(c));
                logger.info("Ice cards: "+iceCards.stream().filter(card -> card.color == c).count()+"/"+seenColors.get(c));
                logger.info("Bolt cards: "+boltCards.stream().filter(card -> card.color == c).count()+"/"+seenColors.get(c));
                logger.info("Wind cards: "+windCards.stream().filter(card -> card.color == c).count()+"/"+seenColors.get(c));
                logger.info("Elementless cards: "+elementlessCards.stream().filter(card -> card.color == c).count()+"/"+seenColors.get(c));
            }
        }


        if (runTest) {
            logger.info("Performing ban swap");
            ArchetypeHelper.prepTest();

            ArrayList<AbstractCard> fireCards2 = new ArrayList<>();
            ArrayList<AbstractCard> iceCards2 = new ArrayList<>();
            ArrayList<AbstractCard> boltCards2 = new ArrayList<>();
            ArrayList<AbstractCard> windCards2 = new ArrayList<>();
            ArrayList<AbstractCard> elementlessCards2 = new ArrayList<>();
            time = -System.currentTimeMillis();
            for (String s : CardLibrary.cards.keySet()) {
                AbstractCard c = CardLibrary.cards.get(s);
                boolean hasElement = false;
                if (ArchetypeHelper.isFire(c)) {
                    fireCards2.add(c);
                    hasElement = true;
                }
                if (ArchetypeHelper.isIce(c)) {
                    iceCards2.add(c);
                    hasElement = true;
                }
                if (ArchetypeHelper.isBolt(c)) {
                    boltCards2.add(c);
                    hasElement = true;
                }
                if (ArchetypeHelper.isWind(c)) {
                    windCards2.add(c);
                    hasElement = true;
                }
                if (!hasElement) {
                    elementlessCards2.add(c);
                }
            }
            time += System.currentTimeMillis();
            fireCards2.sort(Comparator.comparing(o -> o.name));
            iceCards2.sort(Comparator.comparing(o -> o.name));
            boltCards2.sort(Comparator.comparing(o -> o.name));
            windCards2.sort(Comparator.comparing(o -> o.name));
            elementlessCards2.sort(Comparator.comparing(o -> o.name));
            logger.info("Changed Global Card Data");
            logger.info("Processing Time: "+time/1000f+"s");
            logger.info("Fire cards: "+fireCards2.size()+"/"+cardsSeen);
            logger.info("Ice cards: "+iceCards2.size()+"/"+cardsSeen);
            logger.info("Bolt cards: "+boltCards2.size()+"/"+cardsSeen);
            logger.info("Wind cards: "+windCards2.size()+"/"+cardsSeen);
            logger.info("Elementless cards: "+elementlessCards2.size()+"/"+cardsSeen);
            ArrayList<AbstractCard> checker = new ArrayList<>(fireCards);
            checker.removeAll(fireCards2);
            logger.info("Lost Fire cards: "+checker.size()+" - "+checker);
            checker = new ArrayList<>(fireCards2);
            checker.removeAll(fireCards);
            logger.info("Gained Fire cards: "+checker.size()+" - "+checker);

            checker = new ArrayList<>(iceCards);
            checker.removeAll(iceCards2);
            logger.info("Lost Ice cards: "+checker.size()+" - "+checker);
            checker = new ArrayList<>(iceCards2);
            checker.removeAll(iceCards);
            logger.info("Gained Ice cards: "+checker.size()+" - "+checker);

            checker = new ArrayList<>(boltCards);
            checker.removeAll(boltCards2);
            logger.info("Lost Bolt cards: "+checker.size()+" - "+checker);
            checker = new ArrayList<>(boltCards2);
            checker.removeAll(boltCards);
            logger.info("Gained Bolt cards: "+checker.size()+" - "+checker);

            checker = new ArrayList<>(windCards);
            checker.removeAll(windCards2);
            logger.info("Lost Wind cards: "+checker.size()+" - "+checker);
            checker = new ArrayList<>(windCards2);
            checker.removeAll(windCards);
            logger.info("Gained Wind cards: "+checker.size()+" - "+checker);

            checker = new ArrayList<>(elementlessCards);
            checker.removeAll(elementlessCards2);
            logger.info("Lost Elementless cards: "+checker.size()+" - "+checker);
            checker = new ArrayList<>(elementlessCards2);
            checker.removeAll(elementlessCards);
            logger.info("Gained Elementless cards: "+checker.size()+" - "+checker);
        }
    }

    public static Color getRainbowColor() {
        return new Color(
                (MathUtils.cosDeg((float)(System.currentTimeMillis() / 10L % 360L)) + 1.25F) / 2.3F,
                (MathUtils.cosDeg((float)((System.currentTimeMillis() + 1000L) / 10L % 360L)) + 1.25F) / 2.3F,
                (MathUtils.cosDeg((float)((System.currentTimeMillis() + 2000L) / 10L % 360L)) + 1.25F) / 2.3F,
                1.0f);
    }

    public static float time = 0f;
    @Override
    public void receivePostUpdate() {
        time += Gdx.graphics.getRawDeltaTime();
    }

    @Override
    public void receiveAddAudio() {
        BaseMod.addAudio(CustomSounds.SYNTH_START_KEY, CustomSounds.SYNTH_START_PATH);
        BaseMod.addAudio(CustomSounds.SYNTH_START_KEY2, CustomSounds.SYNTH_START_PATH2);
        BaseMod.addAudio(CustomSounds.SYNTH_END_KEY, CustomSounds.SYNTH_END_PATH);
        BaseMod.addAudio(CustomSounds.SYNTH_MIX_KEY, CustomSounds.SYNTH_MIX_PATH);
    }

    @Override
    public void receiveOnPlayerTurnStart() {
        SynthesisPanel.performSynthesis();
    }

    @Override
    public void receiveStartGame() {
        SynthesisPanel.clear();
    }

    public static void infusionTrigger(AbstractInfusion infusion, int directAmount, int relicAmount) {
        if (infusion instanceof DealDamageMod) {
            MemoriaBracelet.onDamageInfusionTrigger(relicAmount);
            LocketOfDevotion.onInfusionTrigger(relicAmount);
        } else if (infusion instanceof GainBlockMod) {
            MemoriaBracelet.onBlockInfusionTrigger(relicAmount);
        }
        for (AbstractPower p : Wiz.adp().powers) {
            if (p instanceof InfusionTriggerPower) {
                ((InfusionTriggerPower) p).infusionTrigger(infusion, directAmount);
            }
        }
    }

    public static void onUpgradeTrigger(AbstractCard c) {
        if (Wiz.adp() != null && Wiz.isInCombat() && Wiz.isCombatCard(c)) {
            for (AbstractPower p : Wiz.adp().powers) {
                if (p instanceof OnUpgradePower) {
                    ((OnUpgradePower) p).onUpgrade(c);
                }
            }
        }
    }

    public static void postUpgradeTrigger(AbstractCard c) {
        for (AbstractCardModifier m : CardModifierManager.modifiers(c)) {
            if (m instanceof AbstractInfusion) {
                ((AbstractInfusion) m).postUpgrade(c);
            }
        }
    }

    public static final ImBoolean shaderActive = new ImBoolean(false);
    public static final ImFloat MAGIC_SENSITIVITY = new ImFloat(10f);
    public static final ImFloat MAGIC_COLOR = new ImFloat(0.5f);

    @Override
    public void receiveImGui() {
        ImVec2 windowPos = ImGui.getMainViewport().getPos();
        ImGui.setNextWindowPos(windowPos.x + 10, windowPos.y + 300, ImGuiCond.FirstUseEver);
        ImGui.setNextWindowSize(200, 200, ImGuiCond.FirstUseEver);

        ImGui.newLine();
        ImGui.selectable("Shader Active", shaderActive);

        ImGui.newLine();
        ImGui.sliderFloat("Magic Sensitivity", MAGIC_SENSITIVITY.getData(), 0f, 25f);
        ImGui.sliderFloat("Magic Color", MAGIC_COLOR.getData(), 0f, 1f);
    }
}
