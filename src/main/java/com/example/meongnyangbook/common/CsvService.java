package com.example.meongnyangbook.common;

import com.example.meongnyangbook.shop.attachment.AttachmentItemUrl;
import com.example.meongnyangbook.shop.attachment.AttachmentItemUrlRepository;
import com.example.meongnyangbook.shop.item.Item;
import com.example.meongnyangbook.shop.item.ItemCategoryEnum;
import com.example.meongnyangbook.shop.item.ItemRepository;
import com.example.meongnyangbook.shop.item.search.ElasticItemSearchRepository;
import com.example.meongnyangbook.shop.item.search.ItemDocument;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CsvService {

  private final ElasticItemSearchRepository elasticItemSearchRepository;
  private final AttachmentItemUrlRepository attachmentItemUrlRepository;


  private final ItemRepository itemRepository;

  public List<String[]> readCsv(String fileName) {
    int n = 20;
    while (n-- > 0) {

      List<String[]> records = new ArrayList<>();

      try {
        String filePath = Paths.get("/Users/hoon/Desktop", fileName).toString();
        Charset eucKrCharset = Charset.forName("UTF-8");
        InputStreamReader reader = new InputStreamReader(new FileInputStream(filePath),
            eucKrCharset);

        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

        for (CSVRecord csvRecord : csvParser) {
          String[] record = new String[8];

          record[1] = csvRecord.get(1);
          record[2] = csvRecord.get(2);
          record[3] = csvRecord.get(3);
          record[4] = csvRecord.get(4);
          record[5] = csvRecord.get(5);
          record[6] = csvRecord.get(6);
          record[7] = csvRecord.get(7);

          Item item = new Item();
          item.setName(record[2]);
          item.setContent(record[3]);
          item.setPrice(Long.parseLong(record[6]));
          item.setQuantity(Long.parseLong(record[4]));
          switch (record[5]) {
            case "SUPPLY":
              item.setCategory(ItemCategoryEnum.SUPPLY);
              break;
            case "DOGFOOD":
              item.setCategory(ItemCategoryEnum.DOGFOOD);
              break;
            case "CATFOOD":
              item.setCategory(ItemCategoryEnum.CATFOOD);
              break;
            case "SNACK":
              item.setCategory(ItemCategoryEnum.SNACK);
              break;
            default:
              item.setCategory(ItemCategoryEnum.SUPPLY);
          }

          Item savedBoard = itemRepository.save(item);
          attachmentItemUrlRepository.save(new AttachmentItemUrl(record[7], savedBoard));
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

          ItemDocument itemDocument = ItemDocument.builder()
              .itemId(savedBoard.getId())
              .name(savedBoard.getName())
              .price(savedBoard.getPrice())
              .quantity(savedBoard.getQuantity())
              .attachmentItemUrl(record[7])
              .createdAt(savedBoard.getCreatedAt())
              .category(savedBoard.getCategory())
              .build();

          elasticItemSearchRepository.save(itemDocument);
        }


      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    return new ArrayList<>();
  }
}
