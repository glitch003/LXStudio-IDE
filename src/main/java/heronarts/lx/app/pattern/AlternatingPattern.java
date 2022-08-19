package heronarts.lx.app.pattern;

import heronarts.lx.LX;
import heronarts.lx.LXCategory;
import heronarts.lx.color.ColorParameter;
import heronarts.lx.color.LXColor;
import heronarts.lx.parameter.DiscreteParameter;
import heronarts.lx.pattern.LXPattern;

@LXCategory("Combo FG")
public class AlternatingPattern extends LXPattern {
    public final ColorParameter oddColor = new ColorParameter("Color 1", 0xff000000)
            .setDescription("Color of the odd pixels");


    public final ColorParameter evenColor = new ColorParameter("Color 2", 0xffffffff)
            .setDescription("Color of the even pixels");

    public final DiscreteParameter stripeLength =
            new DiscreteParameter("Length", 1, 10)
                    .setDescription("Number of pixels to go before switching colors");

    public AlternatingPattern(LX lx) {
        super(lx);
        addParameter("stripeLength", this.stripeLength);
        addParameter("oddColor", this.oddColor);
        addParameter("evenColor", this.evenColor);
    }

    @Override
    public void run(double deltaMs) {
        int stripeLength = this.stripeLength.getValuei();
        int oddColor = this.oddColor.calcColor();
        int evenColor = this.evenColor.calcColor();
        for (int i = 0; i < colors.length; ++i) {
            int phase = (i / stripeLength) % 2;
            this.colors[i] = (phase == 0) ? oddColor : evenColor;
        }
    }
}
