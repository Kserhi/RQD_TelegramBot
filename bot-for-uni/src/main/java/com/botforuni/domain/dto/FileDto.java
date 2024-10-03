package com.botforuni.domain.dto;

import com.botforuni.domain.StatementInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {

    private String fileName;

    private String fileType;

    private byte[] data;

    private Long statementId;

}
