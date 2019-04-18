package com.subsystem;
import java.util.concurrent.*;

public class DictionaryQueue {
    ConcurrentLinkedQueue<Envelope> clq;

        public DictionaryQueue()
        {
            clq = new ConcurrentLinkedQueue<Envelope>();
        }

        public void push(Envelope envelope)
        {
            clq.add(envelope);
        }

        public Envelope pop()
        {
            Envelope envelope = clq.peek();

            return envelope;
        }

        public boolean hasItems()
        {
            return !clq.isEmpty();
        }
}
