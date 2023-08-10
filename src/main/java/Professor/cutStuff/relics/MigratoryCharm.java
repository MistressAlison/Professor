package Professor.cutStuff.relics;

import Professor.TheProfessor;
import Professor.relics.AbstractEasyRelic;

import static Professor.MainModfile.makeID;

public class MigratoryCharm extends AbstractEasyRelic {
    public static final String ID = makeID(MigratoryCharm.class.getSimpleName());

    public MigratoryCharm() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheProfessor.Enums.MEDIUM_RUBY_COLOR);
    }
}
