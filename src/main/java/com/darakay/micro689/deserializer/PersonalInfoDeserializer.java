package com.darakay.micro689.deserializer;

import com.darakay.micro689.dto.PersonalInfoDTO;
import com.darakay.micro689.exception.InvalidFindMatchesRequestFormatException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class PersonalInfoDeserializer extends StdDeserializer<PersonalInfoDTO> {

    private final static DateFormat dateTimeFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private final static Set<String> setOfFieldNames = ImmutableSet.of("surname", "firstName", "secondName", "birthDate");

    public PersonalInfoDeserializer(){
        this(null);
    }

    protected PersonalInfoDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public PersonalInfoDTO deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        checkFields(node.fieldNames());
        String surname = node.get("surname").asText();
        String firstName = node.get("firstName").asText();
        String lastName = node.get("secondName").asText();
        String birthDateRaw = node.get("birthDate").asText();
        Date birthDate = getValidBirthDate(birthDateRaw);
        return new PersonalInfoDTO(surname, firstName, lastName , birthDate);
    }

    private void checkFields(Iterator<String> fieldNames){
        Iterable<String> iterable = () -> fieldNames;
        Set<String> setOfReceivedNames = StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toSet());
        if(!setOfFieldNames.equals(setOfReceivedNames))
            throw InvalidFindMatchesRequestFormatException.invalidFormat();
    }

    private Date getValidBirthDate(String date){
        String[] parts = date.split("-");
        if(parts.length == 3){
            int m = Integer.parseInt(parts[1]);
            int d = Integer.parseInt(parts[2]);
            if(m > 12 || d > 31)
                throw InvalidFindMatchesRequestFormatException.invalidDate();
            try{
                return new Date(dateTimeFormatter.parse(date).getTime());
            } catch (ParseException e) {
                throw InvalidFindMatchesRequestFormatException.invalidDate();
            }
        } else {
            return new Date(Long.parseLong(date));
        }
    }
}
