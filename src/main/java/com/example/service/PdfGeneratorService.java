package com.example.service;

import com.example.core.CountryStateCapitals;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
@Slf4j
public class PdfGeneratorService {
    public void generatePdf(final String fileName, final CountryStateCapitals countryStateCapitals) throws IOException, DocumentException, URISyntaxException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(fileName));

        document.open();
        document.addTitle("List of States in " + countryStateCapitals.getCountry());
        document.addAuthor("shindeamar");
        document.addCreationDate();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16, BaseColor.BLACK);
        Paragraph paragraph = new Paragraph("List of States in " + countryStateCapitals.getCountry(), font);
        document.add(paragraph);

        Path path = Paths.get(ClassLoader.getSystemResource("world_map.png").toURI());
        Image img = Image.getInstance(path.toAbsolutePath().toString());
        document.add(img);

        PdfPTable table = new PdfPTable(3);
        addTableHeader(table);
        addCustomRows(table, countryStateCapitals);
        document.add(table);

        Phrase phrase = new Phrase();
        phrase.add("More information about this can be found ");
        Chunk chunk = new Chunk("here!", font);
        chunk.setUnderline(BaseColor.BLUE, 0.1F, 0.0F, 0.0F, 0.0F, 0);
        chunk.setAnchor(countryStateCapitals.getMoreInformation());
        phrase.add(chunk);

        document.add(phrase);

        document.close();
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("State", "Capital City", "Largest City")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setHorizontalAlignment(Element.ALIGN_CENTER);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addCustomRows(PdfPTable table, CountryStateCapitals countryStateCapitals) {
        if(!CollectionUtils.isEmpty(countryStateCapitals.getStateCapitals())) {
            countryStateCapitals.getStateCapitals().forEach(stateCapitals -> {
                PdfPCell column1 = new PdfPCell(new Phrase(stateCapitals.getState()));
                column1.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(column1);

                PdfPCell column2 = new PdfPCell(new Phrase(stateCapitals.getCapital()));
                column2.setHorizontalAlignment(Element.ALIGN_CENTER);
                table.addCell(column2);

                PdfPCell column3 = new PdfPCell(new Phrase(stateCapitals.getLargestCity()));
                column3.setVerticalAlignment(Element.ALIGN_CENTER);
                table.addCell(column3);
            });
        }
    }

    public void encryptPdf(String fileName) throws IOException, DocumentException {
        PdfReader pdfReader = new PdfReader(fileName);
        PdfStamper pdfStamper
                = new PdfStamper(pdfReader, new FileOutputStream("encrypted" + fileName));

        pdfStamper.setEncryption(
                "userpass".getBytes(),
                "".getBytes(),
                PdfWriter.ALLOW_PRINTING | PdfWriter.ALLOW_COPY,
                PdfWriter.ENCRYPTION_AES_256
        );

        pdfStamper.close();
    }
}
