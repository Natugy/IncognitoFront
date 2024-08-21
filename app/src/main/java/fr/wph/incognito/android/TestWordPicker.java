package fr.wph.incognito.android;

import java.util.ArrayList;
import java.util.Random;

import fr.wph.incognito.core.api.WordPicker;
import fr.wph.incognito.core.api.WordTuple;

public class TestWordPicker implements WordPicker {
    private final ArrayList<WordTuple> wordTupleList;
    public TestWordPicker() {
        wordTupleList = new ArrayList<>();
        wordTupleList.add(new WordTuple("Civil", "Incognito"));
        wordTupleList.add(new WordTuple("Superman", "Batman"));
        wordTupleList.add(new WordTuple("Douche", "Bain"));
        wordTupleList.add(new WordTuple("Coca", "Orangina"));
    }

    @Override
    public WordTuple getWordTuple() {
        Random rand = new Random();
        return wordTupleList.get(rand.nextInt(wordTupleList.size()));
    }
}
