package com.scarlatti.springbatchdemobase;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author Alessandro Scarlatti
 * @since Thursday, 3/28/2019
 */
public class EggReader implements ItemReader<Egg> {

    private List<Egg> eggs = Arrays.asList(
        new Egg(),
        new Egg(true),
        new Egg(),
        new Egg()
    );

    private Iterator<Egg> iterator = eggs.iterator();

    @Override
    public Egg read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (iterator.hasNext()) {
            Egg egg = iterator.next();
            if (egg.getWillFailParse()) {
                throw new RuntimeException("Failed to parse egg.");
            }
            return egg;
        }

        return null;
    }
}
