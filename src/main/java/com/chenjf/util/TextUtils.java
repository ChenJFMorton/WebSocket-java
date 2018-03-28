package com.chenjf.util;

import com.hankcs.algorithm.AhoCorasickDoubleArrayTrie;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by chenjf on 2017-09-11.
 */
public class TextUtils {


    /**
     * 字符串中查询某几个词是否出现以及出现的位子
     * https://github.com/hankcs/AhoCorasickDoubleArrayTrie
     * @param keyArray
     * @param text
     */
    public static void ahoCorasickDoubleArrayTrie(String[] keyArray, String text) {
        TreeMap<String, String> map = new TreeMap<>();
        for (String key : keyArray) {
            map.put(key.toLowerCase(), key.toLowerCase());
        }

        // Build an AhoCorasickDoubleArrayTrie
        AhoCorasickDoubleArrayTrie<String> acdat = new AhoCorasickDoubleArrayTrie<>();
        acdat.build(map);
        List<AhoCorasickDoubleArrayTrie<String>.Hit<String>> wordList = acdat.parseText(text.toLowerCase());

//        String textResult = text;
//        acdat.parseText(text.toLowerCase(), new AhoCorasickDoubleArrayTrie.IHit<String>() {
//            @Override
//            public void hit(int begin, int end, String value) {
//                System.out.printf("[%d:%d]=%s\n", begin, end, value);
//                wordList.add(value);
//            }
//        });

        List<String> sensitiveWordList = new ArrayList<>();
        for (AhoCorasickDoubleArrayTrie<String>.Hit<String> word:wordList) {
            sensitiveWordList.add(text.substring(word.begin, word.end));

        }

        for (String sensitiveWord:sensitiveWordList) {
            text = text.replaceAll(sensitiveWord, "<a>" + sensitiveWord + "</a>");
        }
        System.out.println(text);
    }

    public static void main(String[] args) throws Exception {
        String text = "dbakdbakd  a vAgina cock ada dadadada  aaaaaa";
        ahoCorasickDoubleArrayTrie(new String[]{"Cock", "vagina"}, text);
    }
}
