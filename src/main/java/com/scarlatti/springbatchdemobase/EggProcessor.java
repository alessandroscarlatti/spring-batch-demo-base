package com.scarlatti.springbatchdemobase;

import org.springframework.batch.item.ItemProcessor;

/**
 * @author Alessandro Scarlatti
 * @since Thursday, 3/28/2019
 */
public class EggProcessor implements ItemProcessor<Egg, Egg> {
    @Override
    public Egg process(Egg egg) throws Exception {
        egg.setCooked(true);
        return egg;
    }
}
