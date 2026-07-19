package vn.unigap.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.unigap.api.entity.Province;
import vn.unigap.api.repository.ProvinceRepository;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Custom deserializer for converting JSON data into a Set of Province objects.
 * This deserializer reads a string of province IDs from the JSON data, looks up each province in the database,
 * and adds the corresponding Province object to a Set.
 * If no provinces are found for the given IDs, an exception is thrown.
 */
@Component
public class ProvincesDeserializer extends JsonDeserializer<Set<Province>> {

    @Autowired
    private ProvinceRepository provinceRepository;

    @Override
    public Set<Province> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String provincesString = jsonParser.getValueAsString();
        String[] provinceIds = provincesString.split("-");

        Set<Province> provinces = new HashSet<>();
        for (String provinceId : provinceIds) {
            if (!provinceId.isEmpty()) {
                Province province = provinceRepository.findById((int) Long.parseLong(provinceId))
                        .orElseThrow(() -> new RuntimeException("Province not found with id " + provinceId));
                provinces.add(province);
            }
        }

        if (provinces.isEmpty()) {
            throw new RuntimeException("No provinces found for ids " + provincesString);
        }

        return provinces;
    }
}