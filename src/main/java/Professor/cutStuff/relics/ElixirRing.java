package Professor.cutStuff.relics;

import Professor.TheProfessor;
import Professor.relics.AbstractEasyRelic;

import static Professor.MainModfile.makeID;

public class ElixirRing extends AbstractEasyRelic {
    public static final String ID = makeID(ElixirRing.class.getSimpleName());

    public ElixirRing() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheProfessor.Enums.MEDIUM_RUBY_COLOR);
    }
}
