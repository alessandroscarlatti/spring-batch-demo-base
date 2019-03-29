package com.scarlatti.springbatchdemobase;

import org.springframework.batch.core.SkipListener;

/**
 * @author Alessandro Scarlatti
 * @since Thursday, 3/28/2019
 */
public class EggCookingStepListener implements SkipListener<Egg, Egg> {

    @Override
    public void onSkipInRead(Throwable t) {
        System.out.println("EggCookingStepListener.onSkipInRead() t = [" + t + "]");
//        throw new RuntimeException("Error saving exception to DB.");
    }

    @Override
    public void onSkipInWrite(Egg item, Throwable t) {
        System.out.println("EggCookingStepListener.onSkipInWrite() item = [" + item + "], t = [" + t + "]");
    }

    @Override
    public void onSkipInProcess(Egg item, Throwable t) {
        System.out.println("EggCookingStepListener.onSkipInProcess() item = [" + item + "], t = [" + t + "]");
    }
}
