package com.jun.service.domain.utils;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class BloomFilterUtils {
  public static BloomFilter<Integer> getIntegerFrom(String data, int expectedInsertions, double fpp)
      throws IOException {
    BloomFilter<Integer> filter;
    if (data == null || data == "") {
      filter = BloomFilter.create(Funnels.integerFunnel(), expectedInsertions, fpp);
    } else {
      byte[] bits = Base64.getDecoder().decode(data);
      InputStream storageInput = new ByteArrayInputStream(bits);
      filter = BloomFilter.readFrom(storageInput, Funnels.integerFunnel());
    }
    return filter;
  }
}
