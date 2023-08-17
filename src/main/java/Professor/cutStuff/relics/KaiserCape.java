package Professor.cutStuff.relics;

import Professor.TheProfessor;
import Professor.relics.AbstractEasyRelic;

import static Professor.MainModfile.makeID;

public class KaiserCape extends AbstractEasyRelic {
    public static final String ID = makeID(KaiserCape.class.getSimpleName());

    public KaiserCape() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheProfessor.Enums.MEDIUM_RUBY_COLOR);
    }
}
