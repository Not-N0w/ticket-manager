package com.labs.ticketManager.service.impl;

import com.labs.ticketManager.model.core.*;
import com.labs.ticketManager.service.FileService;
import com.labs.ticketManager.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final TicketService ticketService;


    private boolean hasCsvFormat(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && contentType.equals("text/csv");
    }

    @Override
    public void importData(MultipartFile file, String authHeader) {
        if (!hasCsvFormat(file)) {
            throw new IllegalArgumentException("Invalid file format. Only CSV is supported.");
        }
        processAndSaveData(file, authHeader);
    }

    private void processAndSaveData(MultipartFile file, String authHeader) {
        try (CSVParser csvParser = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .parse(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {

            List<Ticket> tickets = new ArrayList<>();

            for (CSVRecord record : csvParser) {
                Ticket ticket = new Ticket();

                ticket.setName(record.get("name"));
                ticket.setPrice(Integer.parseInt(record.get("price")));
                ticket.setRefundable(Boolean.parseBoolean(record.get("refundable")));
                ticket.setTicketType(TicketType.valueOf(record.get("ticketType")));

                Coordinates coordinates = new Coordinates();
                coordinates.setX(Integer.parseInt(record.get("coordinates.x")));
                coordinates.setY(Float.parseFloat(record.get("coordinates.y")));
                ticket.setCoordinates(coordinates);

                Person person = new Person();
                person.setBirthday(LocalDate.parse(record.get("person.birthday")));
                person.setWeight(Double.parseDouble(record.get("person.weight")));
                person.setPassportId(record.get("person.passportId"));

                Location location = new Location();
                location.setX(Float.parseFloat(record.get("person.location.x")));
                location.setY(Float.parseFloat(record.get("person.location.y")));
                location.setZ(Long.parseLong(record.get("person.location.z")));
                person.setLocation(location);

                ticket.setPerson(person);

                tickets.add(ticket);
            }

            for(Ticket ticket : tickets) {
                ticketService.create(ticket, authHeader);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to parse CSV file: " + e.getMessage(), e);
        }
    }

    @Override
    public ByteArrayInputStream load() {
        List<Ticket> tickets = ticketService.getAll();
        return ticketsToCsv(tickets);
    }

    private ByteArrayInputStream ticketsToCsv(List<Ticket> tickets) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             OutputStreamWriter writer = new OutputStreamWriter(out, StandardCharsets.UTF_8);
             CSVPrinter csvPrinter = new CSVPrinter(writer,
                     CSVFormat.DEFAULT.withHeader(
                             "name", "price", "refundable", "ticketType",
                             "coordinates.x", "coordinates.y",
                             "person.birthday", "person.weight", "person.passportId",
                             "person.location.x", "person.location.y", "person.location.z"
                     ))) {

            for (Ticket ticket : tickets) {
                Coordinates coordinates = ticket.getCoordinates();
                Person person = ticket.getPerson();
                Location location = person != null ? person.getLocation() : null;

                csvPrinter.printRecord(
                        ticket.getName(),
                        ticket.getPrice(),
                        ticket.getRefundable(),
                        ticket.getTicketType(),

                        coordinates != null ? coordinates.getX() : null,
                        coordinates != null ? coordinates.getY() : null,

                        person != null ? person.getBirthday() : null,
                        person != null ? person.getWeight() : null,
                        person != null ? person.getPassportId() : null,

                        location != null ? location.getX() : null,
                        location != null ? location.getY() : null,
                        location != null ? location.getZ() : null
                );
            }

            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Failed to export data to CSV", e);
        }
    }

}
