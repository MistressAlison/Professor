package Professor.relics;

import Professor.TheProfessor;

import static Professor.MainModfile.makeID;

public class FocusGlass extends AbstractEasyRelic {
    public static final String ID = makeID(FocusGlass.class.getSimpleName());

    public FocusGlass() {
        super(ID, RelicTier.UNCOMMON, LandingSound.CLINK, TheProfessor.Enums.MEDIUM_RUBY_COLOR);
    }
}
