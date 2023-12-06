package Professor.vfx;

import Professor.MainModfile;
import basemod.BaseMod;
import basemod.helpers.ScreenPostProcessorManager;
import basemod.interfaces.ImGuiSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.interfaces.PostUpdateSubscriber;
import basemod.interfaces.ScreenPostProcessor;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import imgui.ImGui;
import imgui.ImVec2;
import imgui.flag.ImGuiCond;
import imgui.type.ImBoolean;
import imgui.type.ImFloat;

import java.nio.charset.StandardCharsets;

@SpireInitializer
public class ShaderTest implements ImGuiSubscriber, PostInitializeSubscriber, PostUpdateSubscriber, ScreenPostProcessor {
    public static ShaderProgram sp;
    public static float time;
    public static ShaderTest singleton;
    private static boolean isActive;

    public static final ImBoolean shaderActive = new ImBoolean(false);
    public static final ImFloat MAGIC_SENSITIVITY = new ImFloat(10f);
    public static final ImFloat MAGIC_COLOR = new ImFloat(0.5f);

    public ShaderTest() {
        BaseMod.subscribe(this);
    }

    public static void initialize() {
        singleton = new ShaderTest();
    }

    @Override
    public void receivePostInitialize() {
        sp = new ShaderProgram(SpriteBatch.createDefaultShader().getVertexShaderSource(), Gdx.files.internal(MainModfile.makePath("shaders/comic.frag")).readString(String.valueOf(StandardCharsets.UTF_8)));
    }

    @Override
    public void receivePostUpdate() {
        time += Gdx.graphics.getRawDeltaTime();
        if (shaderActive.get() && !isActive) {
            isActive = true;
            ScreenPostProcessorManager.addPostProcessor(singleton);
        } else if (!shaderActive.get() && isActive) {
            isActive = false;
            ScreenPostProcessorManager.removePostProcessor(singleton);
        }
    }

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

    @Override
    public void postProcess(SpriteBatch sb, TextureRegion textureRegion, OrthographicCamera orthographicCamera) {
        time += Gdx.graphics.getRawDeltaTime();
        sb.setColor(Color.WHITE);
        sb.setBlendFunction(GL20.GL_ONE, GL20.GL_ZERO);
        ShaderProgram back = sb.getShader();
        sb.setShader(sp);
        sp.setUniformf("x_time", time);
        sp.setUniformf("u_mouse", InputHelper.mX, InputHelper.mY);
        sp.setUniformf("MAGIC_SENSITIVITY", MAGIC_SENSITIVITY.get());
        sp.setUniformf("MAGIC_COLOR", MAGIC_COLOR.get());
        sb.draw(textureRegion, 0, 0);
        sb.setShader(back);
    }
}
