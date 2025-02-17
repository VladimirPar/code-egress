package com.bt.code.egress.process;

import com.bt.code.egress.read.LineLocation;
import com.bt.code.egress.write.FileCompleted;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class TextFileReplacer {
    private final LineReplacer lineReplacer;
    private final TextMatched.Listener textMatchedListener;

    public FileCompleted replace(FileLocation file, BufferedReader bufferedReader) throws IOException {
        log.info("Process file as plain text: {}", file);

        String line;
        int lineNum = 0;
        List<String> originalLines = new ArrayList<>();
        List<String> replacedLines = new ArrayList<>();
        while (((line = bufferedReader.readLine()) != null)) {
            String replace = lineReplacer.replace(line, new LineLocation(file.toReportedPath(), ++lineNum), textMatchedListener);
            originalLines.add(line);
            replacedLines.add(replace);
        }
        return new FileCompleted(file, originalLines, replacedLines);
    }

}
