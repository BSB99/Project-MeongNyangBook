package com.example.meongnyangbook.shop.inquiry;

import com.example.meongnyangbook.shop.inquiry.Inquiry;
import com.example.meongnyangbook.shop.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {
    List<Inquiry> findAllByItem(Item item);
}
