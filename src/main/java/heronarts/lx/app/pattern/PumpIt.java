/**
 * Copyright 2022- Mark C. Slee, Heron Arts LLC
 * <p>
 * This file is part of the LX Studio software library. By using
 * LX, you agree to the terms of the LX Studio Software License
 * and Distribution Agreement, available at: http://lx.studio/license
 * <p>
 * Please note that the LX license is not open-source. The license
 * allows for free, non-commercial use.
 * <p>
 * HERON ARTS MAKES NO WARRANTY, EXPRESS, IMPLIED, STATUTORY, OR
 * OTHERWISE, AND SPECIFICALLY DISCLAIMS ANY WARRANTY OF
 * MERCHANTABILITY, NON-INFRINGEMENT, OR FITNESS FOR A PARTICULAR
 * PURPOSE, WITH RESPECT TO THE SOFTWARE.
 *
 * @author Mark C. Slee <mark@heronarts.com>
 */

package heronarts.lx.app.pattern;

import com.google.gson.JsonObject;
import heronarts.lx.LX;
import heronarts.lx.LXCategory;
import heronarts.lx.LXSerializable;
import heronarts.lx.color.LXColor;
import heronarts.lx.model.LXModel;
import heronarts.lx.model.LXPoint;
import heronarts.lx.modulator.LXWaveshape;
import heronarts.lx.modulator.SinLFO;
import heronarts.lx.modulator.VariableLFO;
import heronarts.lx.parameter.CompoundParameter;
import heronarts.lx.pattern.LXPattern;
import heronarts.lx.utils.LXUtils;
import heronarts.lx.app.FixtureMap;

import java.util.List;
import java.util.Map;

@LXCategory("GLORB")
public class PumpIt extends LXPattern {

    //    private static final LXWaveshape[] WAVESHAPES = {
//            LXWaveshape.SIN,
//            LXWaveshape.TRI,
//            LXWaveshape.UP,
//            LXWaveshape.DOWN
//    };
//    public final VariableLFO motion = new VariableLFO("Motion", WAVESHAPES);
    public final CompoundParameter rate = new CompoundParameter("Rate", 3000, 20000, 100);
    public final SinLFO motion = new SinLFO(0, 1, rate);

    public PumpIt(LX lx) {
        super(lx);
//        this.motion.clockMode.setValue(ClockMode.SYNC);
        addParameter("rate", this.rate);
        startModulator(this.motion);
    }

    @Override
    protected void run(double deltaMs) {

//        System.out.println("model.children.length" + model.children.length);
//        System.out.println("motion: " + motion);
        for (Map.Entry<String, List<String>> fixtureNames : FixtureMap.groups.entrySet()) {
            for (String fixtureName : fixtureNames.getValue()) {
                int totalPointsInChildren = 0;

                for (Integer childIndex : FixtureMap.fixtures.get(fixtureName)) {
                    LXModel child = model.children[childIndex];
                    totalPointsInChildren += child.points.length;
                }
                double turnOn = LXUtils.lerp(0, totalPointsInChildren, this.motion.getValue());
//                System.out.println("turnOn: " + turnOn);
                for (Integer childIndex : FixtureMap.fixtures.get(fixtureName)) {
                    LXModel child = model.children[childIndex];
                    for (LXPoint p : child.points) {
                        if (turnOn-- > 0) {
                            colors[p.index] = LXColor.WHITE;
                        } else {
                            colors[p.index] = LXColor.BLACK;
                        }
                    }
                }
            }
        }
//        for (LXPoint p : model.children[2].points) {
//            colors[p.index] = LXColor.gray(motion);
//        }
    }


    /**
     * Implement this method from the UIDeviceControls interface to build a custom UI for
     * your pattern device, rather than relying upon the in-built default UI.
     */
//    @Override
//    public void buildDeviceControls(UI ui, UIDevice uiDevice, Ants pattern) {
//        uiDevice.setContentWidth(COL_WIDTH);
//        addColumn(uiDevice, "Custom",
//                newIntegerBox(pattern.number),
//                controlLabel(ui, "Number"),
//                newDropMenu(pattern.string),
//                controlLabel(ui, "String"),
//                newKnob(pattern.knob)
//        );
//    }

}
