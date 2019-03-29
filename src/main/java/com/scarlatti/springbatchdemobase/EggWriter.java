package com.scarlatti.springbatchdemobase;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * @author Alessandro Scarlatti
 * @since Thursday, 3/28/2019
 */
public class EggWriter implements ItemWriter<Egg> {

    @Override
    public void write(List<? extends Egg> items) throws Exception {
        System.out.println("Chunk: " + items);
    }
}
