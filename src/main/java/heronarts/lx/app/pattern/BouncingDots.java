package heronarts.lx.app.pattern;

import heronarts.lx.LX;
import heronarts.lx.LXCategory;
import heronarts.lx.app.FixtureMap;
import heronarts.lx.color.ColorParameter;
import heronarts.lx.color.LXColor;
import heronarts.lx.color.LinkedColorParameter;
import heronarts.lx.model.LXModel;
import heronarts.lx.model.LXPoint;
import heronarts.lx.modulator.SinLFO;
import heronarts.lx.parameter.CompoundParameter;
import heronarts.lx.parameter.DiscreteParameter;
import heronarts.lx.parameter.FunctionalParameter;
import heronarts.lx.parameter.LXParameter;
import heronarts.lx.pattern.LXPattern;


import java.util.List;


@LXCategory("Edge FG")
public class BouncingDots extends LXPattern {
    public final DiscreteParameter dotWidth =
            new DiscreteParameter("Width", 5, 1, 300)
                    .setDescription("Dot width");

    protected final CompoundParameter rate = (CompoundParameter)
            new CompoundParameter("Rate", .25, .01, 2)
                    .setExponent(2)
                    .setUnits(LXParameter.Units.HERTZ)
                    .setDescription("Rate of the rotation");

    protected final SinLFO phase = new SinLFO(0, 1, new FunctionalParameter() {
        public double getValue() {
            return 1000 / rate.getValue();
        }
    });

    public final ColorParameter color = new ColorParameter("Color", 0xff000000)
            .setDescription("Color of the dots");


    public BouncingDots(LX lx) {
        super(lx);
        startModulator(this.phase);
        addParameter("color", this.color);
        addParameter("rate", this.rate);
        addParameter("width", this.dotWidth);
    }

    public void run(double deltaMs) {
        float phase = this.phase.getValuef();

        int dotColor = this.color.calcColor();
        int dotWidth = this.dotWidth.getValuei();
        // get spirals
        for (String fixtureName : FixtureMap.getSpirals()) {
            List<Integer> allIndices = FixtureMap.fixtures.get(fixtureName);
            int totalPointsInChildren = 0;

            for (Integer childIndex : allIndices) {
                LXModel child = model.children[childIndex];
                totalPointsInChildren += child.points.length;
            }

            int target = (int) (totalPointsInChildren * phase);
            int i = 0;

            for (Integer childIndex : allIndices) {
                LXModel child = model.children[childIndex];
                totalPointsInChildren += child.points.length;

                for (LXPoint point : child.points) {
                    int noHigherThan = target + dotWidth / 2;
                    int tooLow = noHigherThan - dotWidth;
                    if (i <= tooLow || i > noHigherThan) {
                        colors[point.index] = LXColor.BLACK;
                    } else {
                        colors[point.index] = dotColor;
                    }
                    i++;
                }
            }
        }
    }
}
