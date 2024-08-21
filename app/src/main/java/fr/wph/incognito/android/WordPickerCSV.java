package fr.wph.incognito.android;

import android.content.res.AssetManager;

import fr.wph.incognito.core.api.WordPicker;
import fr.wph.incognito.core.api.WordTuple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class WordPickerCSV implements WordPicker {
    private static final String CSV_SEPARATOR = ";";
    private AssetManager assetManager;

    WordPickerCSV(AssetManager assetManager) {
        this.assetManager = assetManager;
    }

    /**
     * Split a csv row using the separator static attribute.
     * @param rowContent a {@code String} representing a csv row.
     * @return {@code String[]} representing the splitted row.
     */
    private List<String> splitCsvRow(String rowContent) {
        //add "NULL (0x0) character at the end to manage empty column
        rowContent += "\0";
        String[] rowSplittedContent = rowContent.split(CSV_SEPARATOR);
        //delete null character
        rowSplittedContent[rowSplittedContent.length - 1] = rowSplittedContent[rowSplittedContent.length - 1].replace("\0", "");
        List<String> result = new ArrayList<String>();
        Collections.addAll(result, rowSplittedContent);
        return result;
    }


    @Override
    public WordTuple getWordTuple() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(assetManager.open("csv/words.csv")))) {
            List<List<String>> allRows = new ArrayList<List<String>>();
            String line = reader.readLine();
            while (line != null) {
                allRows.add(new ArrayList<String>(splitCsvRow(line)));
                line=reader.readLine();
            }
            Random r = new Random();
            List<String> wordRow = allRows.get(r.nextInt(allRows.size()));
            if (r.nextDouble() >= 0.5) {
                return new WordTuple(wordRow.get(0), wordRow.get(1));
            } else {
                return new WordTuple(wordRow.get(1), wordRow.get(0));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
