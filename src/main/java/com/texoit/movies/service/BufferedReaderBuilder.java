package com.texoit.movies.service;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class BufferedReaderBuilder {

    @Getter
    private BufferedReader bufferedReader = null;

    private List<String> lines;

    BufferedReaderBuilder path(final String path) {
        try {
            this.bufferedReader = this.getBufferReaderWithoutFirstLine(path);
            return this;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    BufferedReaderBuilder build() {
        this.lines = this.bufferedReader.lines().collect(Collectors.toList());
        return this;
    }

    List<String> lines() {
        return this.lines;
    }

    /**
     * @return BufferedReader
     * @throws IOException {}
     */
    private BufferedReader getBufferReaderWithoutFirstLine(final String path) throws IOException {
        final var br = new BufferedReader(new InputStreamReader(Optional.ofNullable(getClass().getClassLoader().getResourceAsStream(path)).orElseThrow()));
        // Pula primeira linha
        br.readLine();
        return br;
    }
}
