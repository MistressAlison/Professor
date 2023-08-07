package Professor.relics;

import Professor.TheProfessor;

import static Professor.MainModfile.makeID;

public class HotBloodedBandana extends AbstractEasyRelic {
    public static final String ID = makeID(HotBloodedBandana.class.getSimpleName());

    public HotBloodedBandana() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheProfessor.Enums.MEDIUM_RUBY_COLOR);
    }
}
