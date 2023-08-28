package com.example.meongnyangbook.post.attachment.dto;

import com.example.meongnyangbook.post.attachment.entity.AttachmentUrl;
import lombok.Getter;

@Getter
public class AttachmentUrlResponseDto {
    private Long id;
    private String fileName;

    public AttachmentUrlResponseDto(AttachmentUrl url) {
        this.id = url.getId();
        this.fileName = url.getFileName();
    }
}
